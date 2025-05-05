package actions.ds.bst;

import java.util.ArrayList;

public class BinarySearchTree {

    private Node root;
    private int sizeExcludingDups; // This size excludes duplicate elements
    private int sizeIncludingDups; // This size includes duplicate elements

    public BinarySearchTree() {
        root = null;
        sizeIncludingDups = 0;
        sizeExcludingDups = 0;
    }

    // add using recursion
    public void add(int value) {
        Node newNode = new Node(value);

        if (root == null) {
            root = newNode;
        } else {
            addHelper(root, newNode);
        }
        sizeIncludingDups++;
    }
    private void addHelper(Node current, Node newNode) {
        if (newNode.getValue() < current.getValue()) {
            if (current.getLeft() == null) {
                current.setLeft(newNode);
                newNode.setParent(current);
            } else {
                addHelper(current.getLeft(), newNode);
            }
            sizeExcludingDups++;
        } else if (newNode.getValue() > current.getValue()) {
            if (current.getRight() == null) {
                current.setRight(newNode);
                newNode.setParent(current);
            } else {
                addHelper(current.getRight(), newNode);
            }
            sizeExcludingDups++;
        } else {
            current.setCount(current.getCount() + 1);
        }
    }

    // search using recursion
    public Node search(int value) {
        return searchHelper(root, value);
    }
    private Node searchHelper(Node current, int value) {
        if (current == null || current.getValue() == value) {
            return current;
        }
        if (value < current.getValue()) {
            return searchHelper(current.getLeft(), value);
        } else {
            return searchHelper(current.getRight(), value);
        }
    }

    // remove using search and findMin
    public void remove(int value) {
        Node nodeToRemove = search(value);
        if (nodeToRemove != null) {
            if (nodeToRemove.getCount() > 1) {
                nodeToRemove.setCount(nodeToRemove.getCount() - 1);
            }
            else {
                removeNode(nodeToRemove);
                sizeExcludingDups--;
            }
            sizeIncludingDups--;
        }
    }
    private void removeNode(Node nodeToRemove) {
        //node is a leaf
        if(nodeToRemove.getLeft() == null && nodeToRemove.getRight() == null) {
            if (nodeToRemove.getParent() != null) {
                if (nodeToRemove.getParent().getLeft() == nodeToRemove) {
                    nodeToRemove.getParent().setLeft(null);
                } else {
                    nodeToRemove.getParent().setRight(null);
                }
            } else {
                root = null;
            }
        }
        // node has one child
        else if (nodeToRemove.getLeft() == null || nodeToRemove.getRight() == null) {
            Node child;
            if(nodeToRemove.getLeft() != null){
                child = nodeToRemove.getLeft();
            } else{
                child = nodeToRemove.getRight();
            }    
            if (nodeToRemove.getParent() != null) {
                if (nodeToRemove.getParent().getLeft() == nodeToRemove) {
                    nodeToRemove.getParent().setLeft(child);
                } else {
                    nodeToRemove.getParent().setRight(child);
                }
            } else {
                root = child;
            }
            child.setParent(nodeToRemove.getParent());
        }
        // node has two children
        else {
            Node minNode = findMin(nodeToRemove.getRight());
            nodeToRemove.setValue(minNode.getValue());
            removeNode(minNode);
        }
    }
    // finds the minimum value in the right subtree
    private Node findMin(Node current) {
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    // Inorder traversal
    public void traverse () {
        inOrderTraversal(root);
    }
    public void inOrderTraversal (Node node) {
        if (node != null) {
            inOrderTraversal(node.getLeft());
            for (int i=0 ; i<node.getCount() ; i++)
                System.out.print(node.getValue() + " ");
            inOrderTraversal(node.getRight());
        }
    }
    public void traverse (Node node, ArrayList<Integer> dropletList) {
        if (node != null) {
            traverse(node.getLeft(), dropletList);
            for (int i=0 ; i<node.getCount() ; i++)
                dropletList.add(node.getValue());
            traverse(node.getRight(), dropletList);
        }
    }

    // Getters
    public Node getRoot() { return root; }
    public int getSizeExcludingDups() { return sizeExcludingDups; }
    public int getSizeIncludingDups() { return sizeIncludingDups; }
}
