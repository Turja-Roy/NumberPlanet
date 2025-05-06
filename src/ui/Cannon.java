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

    private ImageIcon icon;

    private int glowCounter;
    private boolean showGlow = false;

    public Cannon () {
        x = 0;
        y = GameConstants.GAMEHEIGHT - CANNON_HEIGHT;
        glowCounter = 0;

        // Set the icon for the bucket
        icon = new ImageIcon("res/cannon1.png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(CANNON_WIDTH, CANNON_HEIGHT, java.awt.Image.SCALE_SMOOTH)); // resize icon

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
