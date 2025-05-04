package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.GameFrame;
import utilz.Constants.GameConstants;

public class IntroButton extends JButton {
    public IntroButton(GameFrame gameFrame) {
        setText("Get ready for the battle!!!");
        setFont(new Font("Comic Sans", Font.BOLD, 50));

        setBackground(Color.BLACK);
        setForeground(Color.WHITE);

        setFocusable(false);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                gameFrame.switchToGamePanel();
            }
        });
    }
}
