package panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import actions.Arsenal;
import actions.ds.stack.Stack;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import main.GameFrame;
import ui.Fireball;
import ui.Cannon;
import utilz.Constants.GameConstants;
import static utilz.Constants.FireballConstants;

public class Round2Panel extends JPanel implements ActionListener {

    private GameFrame gameFrame;
    private Timer timer;
    
    private Stack firesShots;
    private ArrayList<Fireball> activeShots;
    private int lastShotHeight;
    private int burstCounter;

    private Image bgImage;

    private Cannon cannon;

    public Round2Panel (GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        bgImage = new ImageIcon("res/bgImage.jpg").getImage();

        firesShots = Arsenal.getFireShots();
        activeShots = new ArrayList<>();
        lastShotHeight = 0;
        burstCounter = 0;

        this.setPreferredSize(new Dimension(GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT));
        this.setLayout(null);
        this.setVisible(true);

        timer = new Timer(5, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (firesShots.isEmpty() && activeShots.isEmpty()) {
            Arsenal.StackToBST();
            return;
        }

        if (!firesShots.isEmpty() && lastShotHeight >= GameConstants.GAMEHEIGHT / 3) {
            // activeShots.add(firesShots.pop());
            // lastShotHeight = 0;
        }

        Fireball burstFireball = null;
        try {
        for (Fireball fireball : activeShots) {
            fireball.fall();

            // Check for collection or out of bounds
            if (!fireball.hasBurst() && cannon.getBounds().intersects(fireball.getBounds())) {
                // cannon.setShowGlow(true);
                // Arsenal.collectDroplet(fireball);
                // activeShots.remove(fireball);
                // ((GamePanel)getParent()).addCollectedDroplet(fireball.getValue());
            }
            else if (fireball.hasBurst()) {
                burstFireball = fireball;
            }
        }
        } catch (ConcurrentModificationException ex) {
            System.out.println("Concurrent modification exception caught: " + ex.getMessage());
        }

        if (burstCounter > 200) {
            activeShots.remove(burstFireball);
            burstCounter = 0;
        }

        lastShotHeight += FireballConstants.FIREBALL_SPEED;
        burstCounter++;
        repaint();
    }

    @Override
    public void paint (Graphics g) {
        super.paint(g);

        g.drawImage(bgImage, 0, 0, GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT, null);

        for (Fireball fireball : activeShots) {
            fireball.draw(g);
        }

        cannon.draw(g);
    }

    public void handleButtonClick (int value) {
        // Handle button click event
        // You can add your logic here to handle the button click
        System.out.println("Button clicked!");
    }
}
