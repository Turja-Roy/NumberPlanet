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
        icon = new ImageIcon("res/bucket.png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(CANNON_WIDTH, CANNON_HEIGHT, java.awt.Image.SCALE_SMOOTH)); // resize icon

        setBounds((int) x, (int) y, CANNON_WIDTH, CANNON_HEIGHT); // Set the bounds of the bucket
    }

    // Action methods
    public void moveBucket (int mouseX) {
        // x = mouseX - getWidth()/2; // Center the bucket under the mouse
        // if (x < 0) x = 0; // Prevent moving out of bounds
        // if (x > GameConstants.GAMEWIDTH - getWidth()) x = GameConstants.GAMEWIDTH - getWidth(); // Prevent moving out of bounds
        //
        // setBounds((int) x, (int) y, getWidth(), getHeight()); // Update the bounds of the bucket
    }

    // Drawing method
    public void draw (Graphics g) {
        g.drawImage(icon.getImage(), (int) x, (int) y, null);
    }

    // Getters
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
