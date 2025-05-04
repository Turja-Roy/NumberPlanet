package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.GameFrame;
import main.Main;
import panels.Round1Panel;
import ui.TestButton;

import static utilz.Constants.GameConstants;

public class GamePanel extends JPanel {

    private GameFrame gameFrame;
    private JPanel northPanel, southPanel, eastPanel, westPanel;

    private Round1Panel round1Panel;

    public GamePanel (GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        this.setBackground(Color.BLACK);
        this.setSize(GameConstants.GAMEWIDTH+50, GameConstants.GAMEHEIGHT);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        westPanel = new JPanel();
        westPanel.setBackground(Color.BLACK);
        westPanel.setPreferredSize(new Dimension(50, GameConstants.GAMEHEIGHT));

        round1Panel = new Round1Panel(gameFrame);

        this.add(westPanel, BorderLayout.WEST);
        this.add(round1Panel, BorderLayout.CENTER);
    }
}
