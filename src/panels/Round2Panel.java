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
import actions.ds.Pair;
import actions.ds.bst.BinarySearchTree;
import actions.ds.ll.DLL;
import actions.ds.stack.Stack;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;

import main.GameFrame;
import panels.GamePanel;
import ui.Droplet;
import ui.Fireball;
import ui.Cannon;
import utilz.Constants.CannonConstants;
import utilz.Constants.FireballConstants;
import utilz.Constants.GameConstants;
import static utilz.Constants.FireballConstants;

public class Round2Panel extends JPanel implements ActionListener {

    private GameFrame gameFrame;
    private Timer timer;
    
    private Stack<Fireball> enemyFireShots; // Enemy's arsenal
    private ArrayList<Fireball> activeEnemyShots; // Active enemy shots

    private BinarySearchTree<Droplet> playerDropletsBST; // Player's arsenal
    private ArrayList<Pair> activePlayerShots; // Active player shots

    private int lastShotHeight;
    private int burstCounter;
    private int splashCounter;

    private Image bgImage;

    private Cannon cannon;

    public Round2Panel (GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        bgImage = new ImageIcon("res/bgImage.jpg").getImage();

        enemyFireShots = Arsenal.getEnemyFireShots();

        activeEnemyShots = new ArrayList<>();
        activePlayerShots = new ArrayList<>();

        lastShotHeight = 0;
        burstCounter = 0;
        splashCounter = 0;

        cannon = new Cannon();

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

        if (((GamePanel)getParent()).getSouthPanel() != null) {
            ((GamePanel)getParent()).getSouthPanel().requestFocus();
        }

        repaint();
    }

    // Updater methods
    private void updateEnemyFireballs () {
        if (enemyFireShots.isEmpty()) {
            gameOver();
        }

        if (!enemyFireShots.isEmpty() && lastShotHeight >= GameConstants.GAMEHEIGHT / 2) {
            activeEnemyShots.add(enemyFireShots.pop());
            lastShotHeight = 0;
        }

        Fireball burstFireball = null;
        Fireball splashFireball = null;
        try{
        for (Fireball fireball : activeEnemyShots) {
            if (fireball.hasSplashed()) {
                splashFireball = fireball;
                continue;
            }

            fireball.fall();
            if (fireball.hasBurst()) {
                burstFireball = fireball;
                Arsenal.enemyScoreIncrease(fireball.getValue());
            }
        }
        } catch (ConcurrentModificationException e) { }

        if (burstCounter > 200) {
            activeEnemyShots.remove(burstFireball);
            burstCounter = 0;
        }
        if (splashCounter > 200) {
            activeEnemyShots.remove(splashFireball);
            splashCounter = 0;
        }

        lastShotHeight += FireballConstants.FIREBALL_SPEED;
        burstCounter++;
        splashCounter++;
    }
    private void updatePlayerDroplets () {
        Iterator<Pair> it = activePlayerShots.iterator();
        while (it.hasNext()) {
            Pair pair = it.next();
            pair.getDroplet().shoot();

            if (pair.getDroplet().intersect(pair.getTarget())) {
                int ind = activeEnemyShots.indexOf(pair.getTarget());
                if (pair.getDroplet().getValue() >= pair.getTarget().getValue()) {
                    if (ind != -1) activeEnemyShots.get(ind).setSplash(true);

                    Arsenal.playerScoreIncrease( pair.getDroplet().getValue() - pair.getTarget().getValue() );
                    it.remove();
                }
                else {
                    if (ind != -1) {
                        activeEnemyShots.get(ind).setBurst(true);
                        activeEnemyShots.get(ind).setValue( activeEnemyShots.get(ind).getValue() - pair.getDroplet().getValue() );
                    }

                    it.remove();
                }
            }
        }
    }

    // Action methods
    public void shootDroplet (int value) {
        Fireball playerDroplet = new Fireball(value, false); // isFalling = false

        // Find the target and position the cannon
        Fireball target = findTargetFireball(value);
        positionCannon(target);

        playerDroplet.setXpos(cannon.getXpos() + (CannonConstants.CANNON_WIDTH - FireballConstants.FIREBALL_WIDTH)/2);
        playerDroplet.setYpos(GameConstants.GAMEHEIGHT - CannonConstants.CANNON_HEIGHT);

        activePlayerShots.add(new Pair(playerDroplet, target));
    }
    private void positionCannon(Fireball target) {
        if (target == null) return;

        cannon.setXpos(target.getXpos() - (CannonConstants.CANNON_WIDTH - FireballConstants.FIREBALL_WIDTH)/2);
    }
    private Fireball findTargetFireball(int buttonValue) {
        Fireball largestSmaller = null;
        Fireball closest = null;
        int minDiff = Integer.MAX_VALUE;
        
        for (Fireball fb : activeEnemyShots) { // Using your existing activeShots list
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
    private void gameOver () {
        // Create GameOver panel
        String gameOverText = "Game Over! ";
        // if (Arsenal.getEnemyScore() > Arsenal.getPlayerScore()) {
        //     gameOverText += "You lose!";
        // } else if (Arsenal.getEnemyScore() < Arsenal.getPlayerScore()) {
        //     gameOverText += "You win!";
        // } else {
        //     gameOverText += "It's a tie!";
        // }
        TransitionPanel transitionPanel = new TransitionPanel(gameOverText);
        this.setLayout(new OverlayLayout(this)); // Change layout temporarily
        this.add(transitionPanel);
        transitionPanel.setAlignmentX(0.5f);
        transitionPanel.setAlignmentY(0.5f);
        transitionPanel.revalidate();
        repaint();

        // Delay timer
        // Timer delayTimer = new Timer(5000, evt -> {
        //     this.remove(transitionPanel);
        //     this.setLayout(null); // Restore original layout
        //     GamePanel parent = (GamePanel)getParent();
        //     parent.switchToRound2();
        // ((Timer)evt.getSource()).stop();
        // });
        // delayTimer.setRepeats(false);
        // delayTimer.start();
    }

    @Override
    public void paint (Graphics g) {
        super.paint(g);

        g.drawImage(bgImage, 0, 0, GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT, null);

        // Draw the active enemy shots
        for (Fireball fireball : activeEnemyShots) {
            fireball.draw(g);
        }

        // Draw the active player shots
        for (Pair pair : activePlayerShots) {
            pair.getDroplet().draw(g);
        }

        cannon.draw(g);
    }
}
