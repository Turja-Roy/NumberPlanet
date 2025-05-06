package panels;

import java.awt.BorderLayout;
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
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;

import main.GameFrame;
import panels.GamePanel;
import ui.Droplet;
import ui.Fireball;
import ui.ScoreCard;
import ui.Cannon;
import utilz.Constants.CannonConstants;
import utilz.Constants.FireballConstants;
import utilz.Constants.GameConstants;
import static utilz.Constants.FireballConstants;

public class Round2Panel extends JPanel implements ActionListener {

    private GameFrame gameFrame;
    private Timer timer;
    private boolean gameEnded = false;
    
    private Stack<Fireball> enemyFireShots; // Enemy's arsenal
    private BinarySearchTree<Droplet> playerDropletsBST; // Player's arsenal

    private Fireball burstFireball, splashFireball;
    private int burstCounter, splashCounter;

    private Fireball enemyFireball, playerDroplet;

    private Image bgImage;
    private ScoreCard enemyScore, playerScore;

    private Cannon enemyCannon, playerCannon;

    public Round2Panel (GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        bgImage = new ImageIcon("res/bgImage.jpg").getImage();

        enemyFireShots = Arsenal.getEnemyFireShots();

        burstFireball = null;
        splashFireball = null;
        burstCounter = 0;
        splashCounter = 0;

        enemyFireball = null;
        playerDroplet = null;

        enemyCannon = new Cannon(true);
        playerCannon = new Cannon(false);

        enemyScore = new ScoreCard(true);
        playerScore = new ScoreCard(false);

        this.setPreferredSize(new Dimension(GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT));
        this.setLayout(null);
        this.setVisible(true);

        add(enemyScore);
        add(playerScore);

        timer = new Timer(5, this);
        timer.start();
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (gameEnded) gameOver();

        updateEnemyFireballs();
        updatePlayerDroplets();

        if (((GamePanel)getParent()).getSouthPanel() != null) {
            ((GamePanel)getParent()).getSouthPanel().requestFocus();
        }

        repaint();
    }

    // Updater methods
    private void updateEnemyFireballs () {

        if (enemyFireShots.isEmpty() && enemyFireball == null) {
            // System.out.println("Here");
            gameOver();
            return;
        }

        if (!enemyFireShots.isEmpty() && enemyFireball == null) {
            enemyFireball = enemyFireShots.pop();
            return;
        }
        if (enemyFireball == null) return;

        enemyFireball.fall();

        if (enemyFireball.hasSplashed()) {
            splashFireball = enemyFireball;
            splashCounter = 0;
            enemyFireball = null;
        }
        if (enemyFireball.hasBurst()) {
            burstFireball = enemyFireball;
            burstCounter = 0;
            Arsenal.enemyScoreIncrease(enemyFireball.getValue());
            enemyFireball = null;
        }
    }
    private void updatePlayerDroplets () {
        if (playerDroplet == null) return;
        playerDroplet.shoot();

        if (enemyFireball == null) {
            Arsenal.playerScoreIncrease(playerDroplet.getValue());
            playerDroplet = null;
            return;
        }
        if (enemyFireball != null && playerDroplet.intersect(enemyFireball)) {
            if (playerDroplet.getValue() >= enemyFireball.getValue()) {
                splashFireball = playerDroplet.copy();
                splashCounter = 0;
                Arsenal.playerScoreIncrease(playerDroplet.getValue() - enemyFireball.getValue());
            }
            else {
                burstFireball = enemyFireball.copy();
                burstCounter = 0;
                Arsenal.enemyScoreIncrease(enemyFireball.getValue() - playerDroplet.getValue());
            }
            playerDroplet = null;
            enemyFireball = null;
        }
    }

    // Action methods
    public void shootDroplet (int value) {
        playerDroplet = new Fireball(value, false); // isFalling = false
    }

    private void gameOver() {
        gameEnded = true;
        timer.stop();

        String gameOverText1 = "";
        if (Arsenal.getEnemyScore() > Arsenal.getPlayerScore()) {
            gameOverText1 += "You lose!";
        } else if (Arsenal.getEnemyScore() < Arsenal.getPlayerScore()) {
            gameOverText1 += "You win!";
        } else {
            gameOverText1 += "It's a tie!";
        }
        String gameOverText2 = "Enemy Score: " + Arsenal.getEnemyScore() + "  -  " + "Player Score: " + Arsenal.getPlayerScore();

        this.remove(enemyScore);
        this.remove(playerScore);

        TransitionPanel transitionPanel = new TransitionPanel(gameOverText1, gameOverText2);
        this.setLayout(new OverlayLayout(this)); // Change layout temporarily
        this.add(transitionPanel);
        transitionPanel.setAlignmentX(0.5f);
        transitionPanel.setAlignmentY(0.5f);
        transitionPanel.revalidate();
        repaint();

        Timer delayTimer = new Timer(10000, evt -> {
            System.exit(0); // End the game
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    @Override
    public void paint (Graphics g) {
        g.drawImage(bgImage, 0, 0, GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT, null);

        enemyCannon.draw(g);
        playerCannon.draw(g);

        if (enemyFireball != null) enemyFireball.draw(g);
        if (playerDroplet != null) playerDroplet.draw(g);

        if (burstFireball != null && burstCounter <= 100) burstFireball.draw(g);
        if (splashFireball != null && splashCounter <= 100) splashFireball.draw(g);

        burstCounter++;
        splashCounter++;

        super.paintChildren(g);
    }
}
