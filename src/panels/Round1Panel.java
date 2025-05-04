package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.GameFrame;
import main.Main;
import ui.Bucket;
import ui.Droplet;

import static utilz.Constants.GameConstants;

public class Round1Panel extends JPanel implements ActionListener {

    private GameFrame gameFrame;
    private Timer timer;

    private Droplet droplet;
    private Bucket bucket;

    private Image bgImage;

    public Round1Panel (GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        bgImage = new ImageIcon("res/bgImage.jpg").getImage();

        bucket = new Bucket();
        droplet = new Droplet(55);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                bucket.moveBucket(e.getX());
                repaint();
            }
        });

        this.setPreferredSize(new Dimension(GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT));
        this.setLayout(null);
        this.setVisible(true);

        timer = new Timer(5, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        droplet.fall();

        if (!droplet.hasDropped() && 
        bucket.getBounds().intersects(droplet.getBounds())) {
            bucket.setShowGlow(true);
        }
        repaint();
    }

    @Override
    public void paint (Graphics g) {
        super.paint(g);

        g.drawImage(bgImage, 0, 0, GameConstants.GAMEWIDTH, GameConstants.GAMEHEIGHT, null);
        droplet.draw(g);
        bucket.draw(g);
    }
}
