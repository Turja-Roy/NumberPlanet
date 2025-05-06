package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import actions.Arsenal;
import actions.ds.bst.BinarySearchTree;
import actions.ds.ll.DLL;
import panels.GamePanel;
import ui.Droplet;
import ui.Fireball;
import ui.SouthPanelLabel;
import utilz.Constants.GameConstants;

public class SouthPanel extends JPanel implements KeyListener {

    private DLL<Fireball> playerDroplets;
    private BinarySearchTree<SouthPanelLabel> labelTree;
    private StringBuilder numberInput;

    public SouthPanel() {
        playerDroplets = Arsenal.getPlayerDroplets();
        numberInput = new StringBuilder();
        initLabelTree();

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(GameConstants.GAMEWIDTH, GameConstants.SOUTHPANEL_HEIGHT));
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
    }

    public void initLabelTree() {
        removeAll();
        labelTree = new BinarySearchTree<>();
        
        actions.ds.ll.Node<Fireball> curr = playerDroplets.getHead();
        while (curr != null) {
            SouthPanelLabel label = new SouthPanelLabel(curr.getData().getValue());

            labelTree.add(label);
            add(label);

            curr = curr.getNext();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {  }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Character.isDigit(e.getKeyChar())) {
            numberInput.append(e.getKeyChar());
            System.out.println(e.getKeyChar());
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER && numberInput.length() > 0) {
            System.out.println("Entered number: " + numberInput.toString());
            int value = Integer.parseInt(numberInput.toString());
            labelTree.search(new SouthPanelLabel(value)).getData().setActive();
            repaint();
            numberInput.setLength(0);
        }
        else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && numberInput.length() > 0) {
            numberInput.deleteCharAt(numberInput.length() - 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {  }

    @Override
    public boolean isFocusable() { return true; }

    // Ensure focus is maintained
    @Override
    public void requestFocus() {
        super.requestFocus();
        requestFocusInWindow();
    }
}
