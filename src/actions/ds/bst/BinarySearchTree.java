package actions.ds.bst;

import actions.ds.ll.DLL;
import ui.Droplet;
import ui.Fireball;

public class BinarySearchTree<T extends Comparable<T>> {

    private Node<T> root;
    private int sizeExcludingDups; // This size excludes duplicate elements
    private int sizeIncludingDups; // This size includes duplicate elements

    public BinarySearchTree() {
        root = null;
        sizeIncludingDups = 0;
        sizeExcludingDups = 0;
    }

    // add using recursion
    public void add(T data) {
        Node<T> newNode = new Node<>(data);

        if (root == null) {
            root = newNode;
        } else {
            addHelper(root, newNode);
        }
        sizeIncludingDups++;
    }
    private void addHelper(Node<T> current, Node<T> newNode) {
        int compare = newNode.getData().compareTo(current.getData());

        if (compare < 0) {
            if (current.getLeft() == null) {
                current.setLeft(newNode);
                newNode.setParent(current);
            } else {
                addHelper(current.getLeft(), newNode);
            }
            sizeExcludingDups++;
        } else if (compare > 0) {
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
    public Node<T> search(T data) { return searchHelper(root, data);
    }
    private Node<T> searchHelper(Node<T> current, T data) {
        if (current == null || current.getData() == data) {
            return current;
        }
        if (data.compareTo(current.getData()) < 0) {
            return searchHelper(current.getLeft(), data);
        } else {
            return searchHelper(current.getRight(), data);
        }
    }

    // remove using search and findMin
    public void remove(T data) {
        Node<T> nodeToRemove = search(data);
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
    private void removeNode(Node<T> nodeToRemove) {
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
            Node<T> child;
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
            Node<T> minNode = findMin(nodeToRemove.getRight());
            nodeToRemove.setData(minNode.getData());
            removeNode(minNode);
        }
    }
    // finds the minimum data in the right subtree
    private Node<T> findMin(Node<T> current) {
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    // Inorder traversal
    public void traverse () {
        inOrderTraversal(root);
    }
    public void inOrderTraversal (Node<T> node) {
        if (node != null) {
            inOrderTraversal(node.getLeft());
            for (int i=0 ; i<node.getCount() ; i++)
                System.out.print(node.getData() + " ");
            inOrderTraversal(node.getRight());
        }
    }
    public void traverse (Node<Droplet> node, DLL<Fireball> dropletList) {
        if (node != null) {
            traverse(node.getLeft(), dropletList);
            for (int i=0 ; i<node.getCount() ; i++)
                dropletList.add(new Fireball(node.getData().getValue(), false));
            traverse(node.getRight(), dropletList);
        }
    }

    // Getters
    public Node<T> getRoot() { return root; }
    public int getSizeExcludingDups() { return sizeExcludingDups; }
    public int getSizeIncludingDups() { return sizeIncludingDups; }
}
