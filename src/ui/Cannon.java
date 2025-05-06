package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import utilz.Constants.GameConstants;
import static utilz.Constants.CannonConstants.*;

public class Cannon extends JLabel {
    private double x, y;

    private ImageIcon icon, enemyCannonIcon, playerCannonIcon;

    private boolean isEnemyCannon;

    public Cannon (boolean isEnemyCannon) {
        this.isEnemyCannon = isEnemyCannon;

        x = GameConstants.GAMEWIDTH / 2 - CANNON_WIDTH / 2;
        y = isEnemyCannon ? 0 : GameConstants.GAMEHEIGHT - GameConstants.SOUTHPANEL_HEIGHT - CANNON_HEIGHT;

        // Set the cannon icons
        enemyCannonIcon = new ImageIcon("res/enemyCannon.png");
        enemyCannonIcon = new ImageIcon(enemyCannonIcon.getImage().getScaledInstance(CANNON_WIDTH, CANNON_HEIGHT, java.awt.Image.SCALE_SMOOTH)); // resize icon

        playerCannonIcon = new ImageIcon("res/playerCannon.png");
        playerCannonIcon = new ImageIcon(playerCannonIcon.getImage().getScaledInstance(CANNON_WIDTH, CANNON_HEIGHT, java.awt.Image.SCALE_SMOOTH)); // resize icon

        icon = isEnemyCannon ? enemyCannonIcon : playerCannonIcon;

        setBounds((int) x, (int) y, CANNON_WIDTH, CANNON_HEIGHT); // Set the bounds of the bucket
    }

    public void setXpos (double x) {
        this.x = x;
        setBounds((int) x, (int) y, CANNON_WIDTH, CANNON_HEIGHT); // Update the bounds of the cannon
    }

    // Drawing method
    public void draw (Graphics g) {
        g.drawImage(icon.getImage(), (int) x, (int) y, null);
    }

    // Getters
    public double getXpos () { return x; }
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
