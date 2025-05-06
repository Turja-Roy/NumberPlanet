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
import panels.SouthPanel;
import panels.WestPanel;
import ui.Droplet;

import utilz.Constants.DropletConstants;
import static utilz.Constants.GameConstants;

public class GamePanel extends JPanel {

    private GameFrame gameFrame;

    private WestPanel westPanel;
    private SouthPanel southPanel;
    private Round1Panel round1Panel;
    private Round2Panel round2Panel;

    public GamePanel (GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        this.setBackground(Color.BLACK);
        this.setSize(GameConstants.GAMEWIDTH+50, GameConstants.GAMEHEIGHT);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        this.addRound1();
    }

    private void addRound1 () {
        westPanel = new WestPanel();
        this.add(westPanel, BorderLayout.WEST);

        round1Panel = new Round1Panel(gameFrame);
        this.add(round1Panel, BorderLayout.CENTER);
    }

    public void switchToRound2 () {
        this.remove(westPanel);
        this.remove(round1Panel);

        southPanel = new SouthPanel();
        round2Panel = new Round2Panel(gameFrame);

        this.add(round2Panel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        this.revalidate();
        this.repaint();
    }

    // Getters
    public WestPanel getWestPanel () { return westPanel; }
    public SouthPanel getSouthPanel () { return southPanel; }
    public Round1Panel getRound1Panel () { return round1Panel; }
    public Round2Panel getRound2Panel () { return round2Panel; }
}
