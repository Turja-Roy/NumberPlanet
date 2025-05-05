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
    
    private Stack enemyFireShots;
    private int[] playerDroplets;
    private ArrayList<Fireball> activeShots;
    private int lastShotHeight;
    private int burstCounter;

    private Image bgImage;

    private Cannon cannon;

    public Round2Panel (GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        bgImage = new ImageIcon("res/bgImage.jpg").getImage();

        enemyFireShots = Arsenal.getEnemyFireShots();
        playerDroplets = Arsenal.getPlayerDroplets();
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
    public void actionPerformed (ActionEvent e) {
        updateEnemyFireballs();
        updatePlayerDroplets();
        checkCollisions();
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

    // Action methods
    private void updateEnemyFireballs () {

    }
    private void updatePlayerDroplets () {

    }
    private void checkCollisions () {

    }

    public void handleButtonClick (int value) {
        // Handle button click event
        // You can add your logic here to handle the button click
        System.out.println("Button clicked!");
    }
}
