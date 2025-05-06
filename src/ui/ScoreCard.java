package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import actions.Arsenal;
import utilz.Constants.GameConstants;

public class ScoreCard extends JPanel {
    public boolean isEnemy;

    public ScoreCard (boolean isEnemy) {
        this.isEnemy = isEnemy;

        int x = isEnemy ? GameConstants.GAMEWIDTH / 5 : GameConstants.GAMEWIDTH * 3 / 5;
        int y = GameConstants.GAMEHEIGHT / 3;

        setOpaque(false);
        setBounds(x, y, 200, 200);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String message = isEnemy ? "Enemy: " + Arsenal.getEnemyScore() : "Player: " + Arsenal.getPlayerScore();

        int textWidth = g.getFontMetrics().stringWidth(message);
        g.drawString(message, (getWidth() - textWidth)/2, getHeight()/2);
    }
}
