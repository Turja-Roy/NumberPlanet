package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import actions.Arsenal;
import actions.ds.stack.Stack;
import main.GameFrame;
import main.Main;
import panels.Round1Panel;
import ui.Droplet;
import utilz.Constants.DropletConstants;

import static utilz.Constants.GameConstants;

public class GamePanel extends JPanel {

    private GameFrame gameFrame;
    private JPanel westPanel;

    private Round1Panel round1Panel;

    private ArrayList<Integer> collectedDroplets;
    private int totalCollectedDroplets;

    public GamePanel (GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        this.setBackground(Color.BLACK);
        this.setSize(GameConstants.GAMEWIDTH+50, GameConstants.GAMEHEIGHT);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        westPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCollectedDroplets(g);
            }
        };
        westPanel.setBackground(Color.BLACK);
        westPanel.setPreferredSize(new Dimension(50, GameConstants.GAMEHEIGHT));
        collectedDroplets = new ArrayList<>();

        round1Panel = new Round1Panel(gameFrame);

        this.add(westPanel, BorderLayout.WEST);
        this.add(round1Panel, BorderLayout.CENTER);
    }

    private void drawCollectedDroplets(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        int yPos = GameConstants.GAMEHEIGHT - 50; // Start drawing from bottom
        g2d.rotate(Math.toRadians(270), 32, yPos);

        // Collected droplets list into string
        String text="Collection Stack:    ";
        for (int i=collectedDroplets.size()-1 ; i>=0 ; i--)
            text += collectedDroplets.get(i) + ",    ";

        g2d.drawString(text, 32, yPos);

        // for (int i=0 ; i<collectedDroplets.size() ; i++) {
        //     Droplet d = new Droplet(collectedDroplets.get(i));
        //
        //     g2d.setColor(Color.darkGray);
        //     g2d.fillOval(10, yPos, DropletConstants.DROPLET_WIDTH, DropletConstants.DROPLET_HEIGHT);
        //
        //     g2d.setColor(Color.WHITE);
        //     g2d.setFont(new Font("Arial", Font.BOLD, 20));
        //
        //     g2d.drawString(d.getValue() + "", 15, yPos+20);
        //     yPos -= 100;
        // }
    }

    public void addCollectedDroplet(int value, int totalCollectedDroplets) {
        this.totalCollectedDroplets = totalCollectedDroplets;
        collectedDroplets.add(value);
        if (collectedDroplets.size() > 11) {
            collectedDroplets.remove(0); // Remove the oldest droplet if more than 10
        }
        westPanel.repaint();
    }
}
