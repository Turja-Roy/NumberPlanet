package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import actions.Arsenal;
import actions.ds.stack.Stack;
import main.GameFrame;
import main.Main;
import panels.Round1Panel;
import panels.Round2Panel;
import ui.Droplet;

import utilz.Constants.DropletConstants;
import static utilz.Constants.GameConstants;

public class GamePanel extends JPanel {

    private GameFrame gameFrame;

    private JPanel westPanel, southPanel;
    private Round1Panel round1Panel;
    private Round2Panel round2Panel;

    private ArrayList<Integer> collectedDroplets;

    public GamePanel (GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        collectedDroplets = new ArrayList<>();

        this.setBackground(Color.BLACK);
        this.setSize(GameConstants.GAMEWIDTH+50, GameConstants.GAMEHEIGHT);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        this.addRound1();
    }

    public void addRound1 () {
        westPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCollectedDroplets(g);
            }
        };
        westPanel.setBackground(Color.BLACK);
        westPanel.setPreferredSize(new Dimension(50, GameConstants.GAMEHEIGHT));

        this.add(westPanel, BorderLayout.WEST);

        round1Panel = new Round1Panel(gameFrame);
        this.add(round1Panel, BorderLayout.CENTER);
    }

    public void switchToRound2Panel() {
        this.remove(westPanel);
        this.remove(round1Panel);

        // Create south panel for BST buttons
        southPanel = new JPanel();
        southPanel.setBackground(Color.BLACK);
        southPanel.setPreferredSize(new Dimension(GameConstants.GAMEWIDTH, 100));
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        round2Panel = new Round2Panel(gameFrame);

        this.add(round2Panel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        this.revalidate();
        this.repaint();
    }

    private void drawCollectedDroplets(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));

        int yPos = GameConstants.GAMEHEIGHT - 50; // Start drawing from bottom
        g2d.rotate(Math.toRadians(270), 32, yPos);
        
        String text="Collection Stack:    "; // Collected droplets list into string
        for (int i=collectedDroplets.size()-1 ; i>=0 ; i--)
            text += collectedDroplets.get(i) + ",    ";

        g2d.drawString(text, 32, yPos);
    }

    public void addCollectedDroplet (int value) {
        collectedDroplets.add(value);
        if (collectedDroplets.size() > 11) {
            collectedDroplets.remove(0); // Remove the oldest droplet if more than 11
        }
        westPanel.repaint();
    }

    public void createBSTButtons(ArrayList<Integer> bstValues) {
        southPanel.removeAll();
        for (int value : bstValues) {
            JButton button = new JButton(String.valueOf(value));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.addActionListener(e -> {
                round2Panel.handleButtonClick(value);
            });
            southPanel.add(button);
        }
        southPanel.revalidate();
        southPanel.repaint();
    }
}
