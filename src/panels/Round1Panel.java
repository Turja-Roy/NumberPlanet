package panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.Timer;

import actions.Arsenal;
import actions.ds.stack.Stack;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import main.GameFrame;
import panels.TransitionPanel;
import ui.Bucket;
import ui.Droplet;

import utilz.Constants.DropletConstants;
import utilz.Constants.GameConstants;

public class Round1Panel extends JPanel implements ActionListener {

    private GameFrame gameFrame;
    private Timer timer;

    private Stack<Droplet> rainDrops;
    private ArrayList<Droplet> activeDroplets;
    private int lastDropHeight;
    private int splashCounter;

    private Image bgImage;

    private Bucket bucket;

    public Round1Panel (GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        bgImage = new ImageIcon("res/bgImage.jpg").getImage();

        rainDrops = Arsenal.getRainDrops();
        activeDroplets = new ArrayList<>();
        lastDropHeight = 0;
        splashCounter = 0;

        bucket = new Bucket();

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                bucket.moveBucket(e.getX());
                repaint();
            }
        });

        this.setPreferredSize(new Dimension(GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT));
        this.setLayout(null);
        this.setVisible(true);

        timer = new Timer(5, this);
        timer.start();
    }

    private void transitionToRound2 () {
        // Create transition panel
        TransitionPanel transitionPanel = new TransitionPanel("Round 1 Complete. Prepare for the Attack!!!");
        this.setLayout(new OverlayLayout(this)); // Change layout temporarily
        this.add(transitionPanel);
        transitionPanel.setAlignmentX(0.5f);
        transitionPanel.setAlignmentY(0.5f);
        transitionPanel.revalidate();
        repaint();

        // Delay timer
        Timer delayTimer = new Timer(5000, evt -> {
            this.remove(transitionPanel);
            this.setLayout(null); // Restore original layout
            GamePanel parent = (GamePanel)getParent();
            parent.switchToRound2();
        ((Timer)evt.getSource()).stop();
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (rainDrops.isEmpty() && activeDroplets.isEmpty()) {
            timer.stop();
            Arsenal.StackToBST();
            Arsenal.BSTtoList();
            transitionToRound2();

            return;
        }

        if (!rainDrops.isEmpty() && lastDropHeight >= GameConstants.GAMEHEIGHT / 3) {
            activeDroplets.add(rainDrops.pop());
            lastDropHeight = 0;
        }

        Droplet splashedDroplet = null;
        try {
        for (Droplet droplet : activeDroplets) {
            droplet.fall();

            // Check for collection or out of bounds
            if (!droplet.hasSplashed() && bucket.getBounds().intersects(droplet.getBounds())) {
                bucket.setShowGlow(true);
                Arsenal.collectDroplet(droplet);
                activeDroplets.remove(droplet);
                ((GamePanel)getParent()).getWestPanel().addCollectedDroplet(droplet.getValue());
            }
            else if (droplet.hasSplashed()) {
                splashedDroplet = droplet;
            }
        }
        } catch (ConcurrentModificationException ex) { }

        if (splashCounter > 200) {
            activeDroplets.remove(splashedDroplet);
            splashCounter = 0;
        }

        lastDropHeight += DropletConstants.DROPLET_SPEED;
        splashCounter++;
        repaint();
    }

    @Override
    public void paint (Graphics g) {
        g.drawImage(bgImage, 0, 0, GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT, null);

        for (Droplet droplet : activeDroplets) {
            droplet.draw(g);
        }
        bucket.draw(g);

        super.paintChildren(g);
    }
}
