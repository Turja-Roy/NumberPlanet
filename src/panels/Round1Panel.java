package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
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
import javax.swing.JButton;
import javax.swing.JPanel;

import main.GameFrame;
import main.Main;
import ui.Bucket;
import ui.Droplet;
import utilz.Constants.DropletConstants;

import static utilz.Constants.GameConstants;

public class Round1Panel extends JPanel implements ActionListener {

    private GameFrame gameFrame;
    private Timer timer;

    private Arsenal arsenal;
    private Stack rainDrops;
    private ArrayList<Droplet> activeDroplets;
    private int lastDropHeight;
    private int splashCounter;

    private Image bgImage;

    private Bucket bucket;

    public Round1Panel (GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        bgImage = new ImageIcon("res/bgImage.jpg").getImage();

        arsenal = new Arsenal();
        rainDrops = arsenal.getRainDrops();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (rainDrops.isEmpty() && activeDroplets.isEmpty()) {
            // BST Operation
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
                arsenal.collectDroplet(droplet);
                activeDroplets.remove(droplet);
                ((GamePanel)getParent()).addCollectedDroplet(droplet.getValue(), arsenal.getCollectedDroplets().size());
            }
            else if (droplet.hasSplashed()) {
                splashedDroplet = droplet;
            }
        }
        } catch (ConcurrentModificationException ex) {
            System.out.println("Concurrent modification exception caught: " + ex.getMessage());
        }

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
        super.paint(g);

        g.drawImage(bgImage, 0, 0, GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT, null);

        for (Droplet droplet : activeDroplets) {
            droplet.draw(g);
        }

        bucket.draw(g);
    }

    // Getters
    public Arsenal getArsenal() {
        return arsenal;
    }
}
