package ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import utilz.Constants.CannonConstants;
import static utilz.Constants.GameConstants;
import static utilz.Constants.FireballConstants.*;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Fireball extends JLabel implements Comparable<Fireball> {
    private double x, y, yvel;
    private int value;

    private boolean isFalling, isShooting, hasBurst, hasSplashed;

    ImageIcon icon, fallIcon, shootIcon, burstIcon, splashIcon;

    public Fireball (int value, boolean isFalling) {
        this.isFalling = isFalling;

        x = GameConstants.GAMEWIDTH / 2 - FIREBALL_WIDTH / 2;
        y = isFalling ? CannonConstants.CANNON_HEIGHT : GameConstants.GAMEHEIGHT - GameConstants.SOUTHPANEL_HEIGHT - CannonConstants.CANNON_HEIGHT;

        yvel = FIREBALL_SPEED;
        this.value = value;
        hasBurst = false;
        hasSplashed = false;

        // Set the icon for the fireball
        fallIcon = new ImageIcon("res/fireball.png");
        fallIcon = new ImageIcon(fallIcon.getImage().getScaledInstance(FIREBALL_WIDTH, FIREBALL_HEIGHT, java.awt.Image.SCALE_SMOOTH)); // resize icon
        // Set the icon for the droplet
        shootIcon = new ImageIcon("res/shootingDroplet.png");
        shootIcon = new ImageIcon(shootIcon.getImage().getScaledInstance(FIREBALL_WIDTH, FIREBALL_HEIGHT, java.awt.Image.SCALE_SMOOTH)); // resize icon
        // Set the icon for the burst
        burstIcon = new ImageIcon("res/burst.png");
        burstIcon = new ImageIcon(burstIcon.getImage().getScaledInstance(FIREBALL_WIDTH, FIREBALL_HEIGHT, Image.SCALE_SMOOTH)); // resize icon
        // Set the icon for the splash
        splashIcon = new ImageIcon("res/splash.png");
        splashIcon = new ImageIcon(splashIcon.getImage().getScaledInstance(FIREBALL_WIDTH, FIREBALL_HEIGHT, Image.SCALE_SMOOTH)); // resize icon

        icon = isFalling ? fallIcon : shootIcon;

        setBounds((int) x, (int) y, FIREBALL_WIDTH, FIREBALL_HEIGHT); // Set the bounds of the droplet
    }

    // Action methods
    public void fall () {
        if (y >= GameConstants.GAMEHEIGHT - CannonConstants.CANNON_HEIGHT - GameConstants.SOUTHPANEL_HEIGHT) {
            icon = burstIcon;
            hasBurst = true; // Mark as dropped
        }
        else {
            y += yvel; // Move down
        }
    }
    public void shoot () {
        y -= yvel; // Move up
    }

    // Drawing method
    public void draw (Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        if (hasBurst) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2d.drawImage(burstIcon.getImage(), (int)x, (int)y, FIREBALL_WIDTH, FIREBALL_HEIGHT, null);
        }
        else if (hasSplashed) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2d.drawImage(splashIcon.getImage(), (int)x, (int)y, FIREBALL_WIDTH, FIREBALL_HEIGHT, null);
        }
        else {
            // Set the composite for opacity
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            // Draw the fireball with opacity
            g2d.drawImage(icon.getImage(), (int)x, (int)y, FIREBALL_WIDTH, FIREBALL_HEIGHT, null);

            // Reset composite for text
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            // Draw the text (now fully opaque)
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));

            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(value + "");
            int textX = (int)x + (FIREBALL_WIDTH - textWidth) / 2;
            int textY = (int)y + (FIREBALL_HEIGHT / 2) + fm.getAscent() / 2;

            g2d.drawString(value + "", textX, textY);
        }

        g2d.dispose();
    }

    // Action methods
    public boolean intersect (Fireball other) {
        return this.getBounds().intersects(other.getBounds());
    }

    // Getters
    public double getXpos () { return (double) x; }
    public double getYpos () { return (double) y; }
    public boolean hasBurst () { return hasBurst; }
    public boolean hasSplashed () { return hasSplashed; }
    public int getValue () { return value; }
    public ImageIcon getNormalIcon () {
        icon = new ImageIcon("res/fireball.png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(FIREBALL_WIDTH, FIREBALL_HEIGHT, java.awt.Image.SCALE_SMOOTH)); // resize icon
        return icon;
    }
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, FIREBALL_WIDTH, FIREBALL_HEIGHT);
    }

    // Setters
    public void setXpos (double x) { this.x = x; }
    public void setYpos (double y) { this.y = y; }
    public void setValue (int value) { this.value = value; }
    public void setBurst (boolean hasBurst) { this.hasBurst = hasBurst; }
    public void setSplash (boolean hasSplashed) { this.hasSplashed = hasSplashed; }

    public Fireball copy () {
        Fireball copy = this;

        if (isFalling) copy.setBurst(true);
        else copy.setSplash(true);

        return copy;
    }

    @Override
    public int compareTo (Fireball other) {
        return Integer.compare(this.value, other.value);
    }
}
