package panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import actions.Arsenal;
import actions.ds.bst.BinarySearchTree;
import actions.ds.stack.Stack;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import main.GameFrame;
import ui.Fireball;
import ui.Cannon;
import utilz.Constants.CannonConstants;
import utilz.Constants.FireballConstants;
import utilz.Constants.GameConstants;
import static utilz.Constants.FireballConstants;

public class Round2Panel extends JPanel implements ActionListener {

    private GameFrame gameFrame;
    private Timer timer;
    
    private Stack<Fireball> enemyFireShots;
    private BinarySearchTree playerDropletsBST;
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
        // Move all active enemy shots downward
        for (Fireball fireball : activeShots) {
            fireball.fall();
        }

        // Spawn new fireballs periodically
        if (lastShotHeight >= GameConstants.GAMEHEIGHT / 3) {
            if (!enemyFireShots.isEmpty()) {
                activeShots.add(enemyFireShots.pop());
                lastShotHeight = 0;
            }
        }
        lastShotHeight += FireballConstants.FIREBALL_SPEED;
    }
    private void updatePlayerDroplets () {
        // Move all player shots upward
        Iterator<Fireball> iter = playerDroplets.iterator();
        while (iter.hasNext()) {
            Fireball droplet = iter.next();
            droplet.shoot();
            if (droplet.getYpos() < 0) { // Remove when off-screen
                iter.remove();
            }
        }
    }
    private void checkCollisions () {
        // Check player shots against enemy shots
        for (Fireball playerShot : new ArrayList<>(playerFireballs)) {
            for (Fireball enemyShot : new ArrayList<>(activeShots)) {
                if (playerShot.intersects(enemyShot)) {
                    // Show splash effect
                    enemyShot.splash((int)enemyShot.getXpos(), (int)enemyShot.getYpos());

                    // Remove both projectiles
                    playerFireballs.remove(playerShot);
                    activeShots.remove(enemyShot);

                    // Add score
                    Arsenal.playerScoreIncrease(10);
                    break;
                }
            }
        }

        // Check for enemy shots reaching bottom
        for (Fireball enemyShot : new ArrayList<>(activeShots)) {
            if (enemyShot.hasBurst()) {
                activeShots.remove(enemyShot);
                Arsenal.enemyScoreIncrease(5);
            }
        }
    }

    private Fireball findTargetFireball(int buttonValue) {
        Fireball largestSmaller = null;
        Fireball closest = null;
        int minDiff = Integer.MAX_VALUE;
        
        for (Fireball fb : activeShots) { // Using your existing activeShots list
            int diff = Math.abs(fb.getValue() - buttonValue);
            
            if (fb.getValue() < buttonValue) {
                if (largestSmaller == null || fb.getValue() > largestSmaller.getValue()) {
                    largestSmaller = fb;
                }
            }
            
            if (diff < minDiff) {
                minDiff = diff;
                closest = fb;
            }
        }
        
        return largestSmaller != null ? largestSmaller : closest;
    }
    private void positionCannon(Fireball target) {
        if (target != null) {
            cannon.setX(target.getXpos() - (CannonConstants.CANNON_WIDTH - FireballConstants.FIREBALL_WIDTH)/2);
        }
    }
    private void shootFireball(int value) {
        Fireball shot = new Fireball(value, "shoot");
        shot.setX(cannon.getXpos() + (CannonConstants.CANNON_WIDTH - FireballConstants.FIREBALL_WIDTH)/2);
        shot.setY(GameConstants.GAMEHEIGHT - CannonConstants.CANNON_HEIGHT);
        playerDroplets.add(shot);
    }

    public void handleButtonClick (int value) {
        // Handle button click event
        // You can add your logic here to handle the button click
        System.out.println("Button clicked!");
    }
}
