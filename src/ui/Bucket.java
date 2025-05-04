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
import static utilz.Constants.BucketConstants.*;

public class Bucket extends JLabel {
    private double x, y;

    private ImageIcon icon, bucketIcon, glowBucketIcon;

    private int glowCounter;
    private boolean showGlow = false;

    public Bucket () {
        x = 0;
        y = GameConstants.GAMEHEIGHT - BUCKET_HEIGHT;
        glowCounter = 0;

        // Set the icon for the bucket
        bucketIcon = new ImageIcon("res/bucket.png");
        bucketIcon = new ImageIcon(bucketIcon.getImage().getScaledInstance(BUCKET_WIDTH, BUCKET_HEIGHT, java.awt.Image.SCALE_SMOOTH)); // resize icon

        // Set the icon for the glowing bucket
        glowBucketIcon = new ImageIcon("res/glowBucket.png");
        glowBucketIcon = new ImageIcon(glowBucketIcon.getImage().getScaledInstance(BUCKET_WIDTH, BUCKET_HEIGHT, java.awt.Image.SCALE_SMOOTH)); // resize icon

        icon = bucketIcon; // Default to normal bucket icon

        setBounds((int) x, (int) y, BUCKET_WIDTH, BUCKET_HEIGHT); // Set the bounds of the bucket
    }

    // Action methods
    public void moveBucket (int mouseX) {
        x = mouseX - getWidth()/2; // Center the bucket under the mouse
        if (x < 0) x = 0; // Prevent moving out of bounds
        if (x > GameConstants.GAMEWIDTH - getWidth()) x = GameConstants.GAMEWIDTH - getWidth(); // Prevent moving out of bounds

        setBounds((int) x, (int) y, getWidth(), getHeight()); // Update the bounds of the bucket
    }
    public void setShowGlow (boolean showGlow) {
        this.showGlow = showGlow;
    }

    // Drawing method
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        if (!showGlow) {
            icon = bucketIcon;
        } else {
            icon = glowBucketIcon;
            glow();
        }

        g2d.drawImage(icon.getImage(), (int) x, (int) y, null);
        g2d.dispose();
    }
    public void glow () {
        if (glowCounter < 100) {
            glowCounter++; // Increment the glow counter
        } else {
            showGlow = false; // Stop showing the glow
            glowCounter = 0; // Reset the glow counter
        }
    }

    // Getters
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
