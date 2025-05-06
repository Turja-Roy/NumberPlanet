package panels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import main.GameFrame;
import ui.IntroButton;

import static utilz.Constants.GameConstants;

public class IntroPanel extends JPanel {

    private GameFrame gameFrame;
    private IntroButton startButton;

    public IntroPanel (GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        startButton = new IntroButton(gameFrame);

        this.setBackground(Color.BLACK);
        this.setSize(GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        this.add(startButton, BorderLayout.CENTER);
    }
}
