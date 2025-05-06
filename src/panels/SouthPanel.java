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
import actions.ds.HashTable;
import actions.ds.bst.BinarySearchTree;
import actions.ds.ll.DLL;
import main.ImplementedDS;
import panels.GamePanel;
import ui.Droplet;
import ui.SouthPanelLabel;
import utilz.Constants;
import utilz.Constants.GameConstants;

public class SouthPanel extends JPanel implements KeyListener {

    private HashTable dropletTable;
    private HashTable labelTable;
    private BinarySearchTree<Droplet> dropletTree;
    private BinarySearchTree<SouthPanelLabel> labelTree;
    private StringBuilder numberInput;

    private JLabel inputFeedback; // For debugging

    public SouthPanel() {
        dropletTable = Arsenal.getDropletTable();
        dropletTree = Arsenal.getDropletTree();

        numberInput = new StringBuilder();

        if (Constants.IMPLEMENTED_DS == ImplementedDS.BINARYSEARCHTREE) {
            removeAll();
            labelTree = new BinarySearchTree<>();
            initLabelTree(dropletTree.getRoot());
            addLabelsFromTree(labelTree.getRoot());
        }

        if (Constants.IMPLEMENTED_DS == ImplementedDS.HASHTABLE) {
            removeAll();
            labelTable = new HashTable();
            initLabelTable();
            addLabelsFromTable();
        }

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

    @Override
    public void keyTyped(KeyEvent e) {  }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Character.isDigit(e.getKeyChar())) {
            numberInput.append(e.getKeyChar());
            updateInputFeedback();
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER && numberInput.length() > 0) {
            if (Constants.IMPLEMENTED_DS == ImplementedDS.BINARYSEARCHTREE)
            handleEnterPressForBST();
            else if (Constants.IMPLEMENTED_DS == ImplementedDS.HASHTABLE)
            handleEnterPressForHashTable();
        }
        else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && numberInput.length() > 0) {
            numberInput.deleteCharAt(numberInput.length() - 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {  }

    @Override
    public boolean isFocusable() { return true; }

    @Override
    public void requestFocus() {
        super.requestFocus();
        requestFocusInWindow();
    }

    private void updateInputFeedback() {
        inputFeedback.setForeground(Color.YELLOW);
        inputFeedback.setText("Input: " + numberInput.toString());
    }


    /*                            */
    /*                            */
    /* Binary Search Tree Methods */
    /*                            */
    /*                            */

    private void initLabelTree(actions.ds.bst.Node<Droplet> node) {
        if (node == null) return;

        initLabelTree(node.getLeft());
        for (int i=0 ; i<node.getCount() ; i++)
            labelTree.add(new SouthPanelLabel(node.getData().getValue()));
        initLabelTree(node.getRight());
    }
    private void addLabelsFromTree (actions.ds.bst.Node<SouthPanelLabel> node) {
        if (node == null) return;

        addLabelsFromTree(node.getLeft());
        add(node.getData());
        addLabelsFromTree(node.getRight());
    }

    private void handleEnterPressForBST() {
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


    /*                            */
    /*                            */
    /*     Hash Table Methods     */
    /*                            */
    /*                            */

    private void initLabelTable() {
        for (int i = 0; i < HashTable.TABLE_SIZE; i++) {
            DLL<Integer> list = dropletTable.getList(i);
            actions.ds.ll.Node<Integer> current = list.getHead();

            while (current != null) {
                int value = current.getData();
                // Only insert if not already in labelTable
                if (!labelTable.search(value)) {
                    labelTable.insert(value);
                }
                current = current.getNext();
            }
        }
    }
    private void addLabelsFromTable() {
        for (int i = 0; i < HashTable.TABLE_SIZE; i++) {
            DLL<Integer> list = labelTable.getList(i);
            actions.ds.ll.Node<Integer> current = list.getHead();

            while (current != null) {
                int value = current.getData();
                // Only add if this is the first occurrence in labelTable
                if (isFirstOccurrence(value, i, current)) {
                    add(new SouthPanelLabel(value));
                }
                current = current.getNext();
            }
        }
    }
    private boolean isFirstOccurrence(int value, int currentIndex, actions.ds.ll.Node<Integer> curr) {
        for (int i = 0; i < currentIndex; i++) {
            DLL<Integer> list = labelTable.getList(i);
            actions.ds.ll.Node<Integer> node = list.getHead();
            while (node != null) {
                if (node.getData() == value) {
                    return false; // Found an earlier occurrence
                }
                node = node.getNext();
            }
        }
        // Check previous nodes in current bucket
        actions.ds.ll.Node<Integer> node = labelTable.getList(currentIndex).getHead();
        while (node != curr) {
            if (node.getData() == value) {
                return false;
            }
            node = node.getNext();
        }
        return true;
    }

    private void handleEnterPressForHashTable() {
        if (numberInput.length() == 0) return;

        try {
        int value = Integer.parseInt(numberInput.toString());

        if (labelTable.search(value)) {
            SouthPanelLabel foundLabel = findLabelInPanel(value);

            if (foundLabel != null) {
                int remainingOccurrences = countAllOccurrences(value);

                if (remainingOccurrences > 1) {
                    foundLabel.setActive();
                ((GamePanel)getParent()).getRound2Panel().shootDroplet(value);
                    removeOneOccurrence(value);
                } else {
                    foundLabel.setInactive();
                ((GamePanel)getParent()).getRound2Panel().shootDroplet(value);
                    removeOneOccurrence(value);
                    labelTable.remove(value); // Remove from labelTable when last occurrence is used
                }
                repaint();
            }
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

    private int countAllOccurrences(int value) {
        int count = 0;
        int index = dropletTable.hash(value);
        DLL<Integer> list = dropletTable.getList(index);
        actions.ds.ll.Node<Integer> current = list.getHead();

        while (current != null) {
            if (current.getData() == value) {
                count++;
            }
            current = current.getNext();
        }
        return count;
    }
    private void removeOneOccurrence(int value) {
        int index = dropletTable.hash(value);
        DLL<Integer> list = dropletTable.getList(index);
        list.remove(value); // Remove one occurrence
    }

    private SouthPanelLabel findLabelInPanel(int value) {
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof SouthPanelLabel) {
                SouthPanelLabel label = (SouthPanelLabel) getComponent(i);
                if (label.getValue() == value) {
                    return label;
                }
            }
        }
        return null;
    }
}
