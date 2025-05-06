package main;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import panels.IntroPanel;
import panels.Round1Panel;
import panels.GamePanel;
import static utilz.Constants.GameConstants;

public class GameFrame extends JFrame {

    // Declaring the panels
    private IntroPanel introPanel;
    private GamePanel gamePanel;
    private Round1Panel round1Panel;

    // Initializing the base frame
    public GameFrame () {
        this.setTitle("Number Planet");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(GameConstants.GAMEWIDTH,GameConstants.GAMEHEIGHT);
        this.setVisible(true);

        this.setLayout(null);
        this.getContentPane().setBackground(Color.BLACK);
    }

    // Methods for adding new panels to the frame
    public void addIntroPanel () {
        this.introPanel = new IntroPanel(this);
        this.add(introPanel);
    }
    public void addGamePanel () {
        this.gamePanel = new GamePanel(this);
        this.add(gamePanel);
    }

    // Method for switching panels
    public void switchToGamePanel () {
        remove(introPanel);
        addGamePanel();
        this.revalidate();
        this.repaint();
    }
}
