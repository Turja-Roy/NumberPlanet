package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import actions.Arsenal;
import actions.ds.bst.BinarySearchTree;
import actions.ds.ll.DLL;
import panels.GamePanel;
import ui.Fireball;
import ui.SouthPanelLabel;

import utilz.Constants.GameConstants;

public class SouthPanel extends JPanel implements KeyListener {

    private DLL<Fireball> playerDropletsDLL;
    private BinarySearchTree<SouthPanelLabel> labelTree;
    private StringBuilder numberInput;

    private JLabel inputFeedback; // For debugging

    public SouthPanel() {
        playerDropletsDLL = Arsenal.getPlayerDropletsDLL();
        numberInput = new StringBuilder();

        initLabelTree();
        addLabelsFromTree(labelTree.getRoot());

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(GameConstants.GAMEWIDTH, GameConstants.SOUTHPANEL_HEIGHT));
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Inputfeedback for debugging
        inputFeedback = new JLabel();
        inputFeedback.setForeground(Color.YELLOW);
        inputFeedback.setFont(new Font("Arial", Font.BOLD, 16));
        add(inputFeedback);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
    }

    private void initLabelTree() {
        removeAll();
        labelTree = new BinarySearchTree<>();
        
        actions.ds.ll.Node<Fireball> curr = playerDropletsDLL.getHead();
        while (curr != null) {
            SouthPanelLabel label = new SouthPanelLabel(curr.getData().getValue());

            labelTree.add(label);

            curr = curr.getNext();
        }
    }

    private void addLabelsFromTree (actions.ds.bst.Node<SouthPanelLabel> node) {
        if (node == null) return;

        addLabelsFromTree(node.getLeft());
        add(node.getData());
        addLabelsFromTree(node.getRight());
    }

    @Override
    public void keyTyped(KeyEvent e) {  }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Character.isDigit(e.getKeyChar())) {
            numberInput.append(e.getKeyChar());
            updateInputFeedback();
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER && numberInput.length() > 0) {
            handleEnterPress();
        }
        else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && numberInput.length() > 0) {
            numberInput.deleteCharAt(numberInput.length() - 1);
        }
    }

    private void handleEnterPress() {
        if (numberInput.length() == 0) return;

        try {
            int value = Integer.parseInt(numberInput.toString());
            actions.ds.bst.Node<SouthPanelLabel> found = labelTree.search(new SouthPanelLabel(value));

            if (found != null) {
                if (found.getCount() > 1) {
                    found.getData().setActive();
                    ((GamePanel)getParent()).getRound2Panel().shootDroplet(found.getData().getValue());
                    labelTree.remove(found.getData());
                } else if (found.getCount() == 1) {
                    found.getData().setInactive();
                    ((GamePanel)getParent()).getRound2Panel().shootDroplet(found.getData().getValue());
                    found.setCount(0);
                } else {
                    inputFeedback.setForeground(Color.RED);
                    inputFeedback.setText("Input: " + value);
                }
                repaint();
            } else {
                inputFeedback.setForeground(Color.RED);
                inputFeedback.setText("Input: " + value);
            }
        } catch (NumberFormatException ex) {
            System.err.println("Invalid number format");
            inputFeedback.setForeground(Color.RED);
            inputFeedback.setText("Invalid");
        }

        numberInput.setLength(0);
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

    private void updateInputFeedback() {
        inputFeedback.setForeground(Color.YELLOW);
        inputFeedback.setText("Input: " + numberInput.toString());
    }
}
