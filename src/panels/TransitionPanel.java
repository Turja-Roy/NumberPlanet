package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

import static utilz.Constants.GameConstants;

public class TransitionPanel extends JPanel {
    private String message;
    private String message2;
    
    public TransitionPanel(String message, String message2) {
        this.message = message;
        this.message2 = message2;
        this.setOpaque(false); // Make sure this is set
        this.setBounds(0, 0, GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT);
        this.setLayout(null); // Use null layout for precise positioning
    }
    public TransitionPanel(String message) {
        this.message = message;
        this.setOpaque(false); // Make sure this is set
        this.setBounds(0, 0, GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT);
        this.setLayout(null); // Use null layout for precise positioning
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        // Don't call super to avoid clearing the background
        g.setColor(new Color(0, 0, 0, 180)); // Semi-transparent black (lower alpha)
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        int textWidth = g.getFontMetrics().stringWidth(message);
        g.drawString(message, (getWidth() - textWidth)/2, getHeight()/2);
        if (message2 != null) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            textWidth = g.getFontMetrics().stringWidth(message2);
            g.drawString(message2, (getWidth() - textWidth)/2, getHeight()/2 + 50);
        }
    }
}
