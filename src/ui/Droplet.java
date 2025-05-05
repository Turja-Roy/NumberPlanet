package ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import utilz.Constants.BucketConstants;

import static utilz.Constants.GameConstants;
import static utilz.Constants.DropletConstants.*;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Droplet extends JLabel implements Comparable<Droplet> {
    private double x, y, yvel;
    private int value;

    private boolean hasSplashed;

    ImageIcon icon, splashIcon;

    public Droplet (int value) {
        x = (double) Math.random() * (GameConstants.GAMEWIDTH - 50); // Random x position
        y = 0f;
        yvel = DROPLET_SPEED;
        this.value = value;
        hasSplashed = false;

        // Set the icon for the droplet
        icon = new ImageIcon("res/droplet.png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(DROPLET_WIDTH, DROPLET_HEIGHT, java.awt.Image.SCALE_SMOOTH)); // resize icon

        // Set the icon for the splash
        splashIcon = new ImageIcon("res/splash.png");
        splashIcon = new ImageIcon(splashIcon.getImage().getScaledInstance(DROPLET_WIDTH, DROPLET_HEIGHT, Image.SCALE_SMOOTH)); // resize icon
        icon = splashIcon;

        setBounds((int) x, (int) y, DROPLET_WIDTH, DROPLET_HEIGHT); // Set the bounds of the droplet
    }

    // Action methods
    public void fall () {
        if (y >= GameConstants.GAMEHEIGHT - BucketConstants.BUCKET_HEIGHT - 5) {
            icon = splashIcon;
            hasSplashed = true; // Mark as dropped
        }
        else {
            y += yvel; // Move down
        }
    }

    // Drawing method
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        if (hasSplashed) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2d.drawImage(icon.getImage(), (int)x, (int)y, DROPLET_WIDTH, DROPLET_HEIGHT, null);
        }
        else {
            // Set the composite for opacity
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            // Draw the droplet with opacity
            g2d.drawImage(icon.getImage(), (int)x, (int)y, DROPLET_WIDTH, DROPLET_HEIGHT, null);

            // Reset composite for text
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            // Draw the text (now fully opaque)
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));

            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(value + "");
            int textX = (int)x + (DROPLET_WIDTH - textWidth) / 2;
            int textY = (int)y + (DROPLET_HEIGHT / 2) + fm.getAscent() / 2;

            g2d.drawString(value + "", textX, textY);
        }

        g2d.dispose();
    }

    // Getters
    public double getXpos () { return (double) x; }
    public double getYpos () { return (double) y; }
    public boolean hasSplashed () { return hasSplashed; }
    public int getValue () { return value; }
    public ImageIcon getNormalIcon () {
        icon = new ImageIcon("res/droplet.png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(DROPLET_WIDTH, DROPLET_HEIGHT, java.awt.Image.SCALE_SMOOTH)); // resize icon
        return icon;
    }
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, DROPLET_WIDTH, DROPLET_HEIGHT);
    }

    @Override
    public int compareTo(Droplet other) {
        return Integer.compare(this.value, other.value);
    }
}
