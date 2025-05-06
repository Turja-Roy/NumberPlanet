package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import actions.ds.ll.DLL;

import utilz.Constants.GameConstants;

public class WestPanel extends JPanel {

    private DLL<Integer> collectedDroplets;

    public WestPanel () {
        collectedDroplets = new DLL<>();

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(GameConstants.WESTPANEL_WIDTH, GameConstants.GAMEHEIGHT));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCollectedDroplets(g);
    }

    private void drawCollectedDroplets(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));

        int yPos = GameConstants.GAMEHEIGHT - 50; // Start drawing from bottom
        g2d.rotate(Math.toRadians(270), 32, yPos);
        
        String text="Collection Stack:    "; // Collected droplets list into string
        actions.ds.ll.Node<Integer> curr = collectedDroplets.getTail();
        while (curr != null) {
            text += curr.getData() + ",    ";
            curr = curr.getPrev();
        }

        g2d.drawString(text, 32, yPos);
    }

    public void addCollectedDroplet (int value) {
        collectedDroplets.add((Integer) value);
        if (collectedDroplets.getSize() > 11) {
            collectedDroplets.remove(collectedDroplets.getHead().getData()); // Remove the oldest droplet if more than 11
        }
        repaint();
    }
}
