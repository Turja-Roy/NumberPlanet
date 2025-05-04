package actions.ds.bst;

public class BinarySearchTree {

    private Node root;

    public BinarySearchTree() {
        root = null;
    }

    // add using recursion
    public void add(int value) {
        Node newNode = new Node(value);

        Node find = search(value);
        if (find != null) {
            find.setCount(find.getCount() + 1);
            return;
        }

        if (root == null) {
            root = newNode;
        } else {
            addHelper(root, newNode);
        }
    }
    private void addHelper(Node current, Node newNode) {
        if (newNode.getValue() < current.getValue()) {
            if (current.getLeft() == null) {
                current.setLeft(newNode);
                newNode.setParent(current);
            } else {
                addHelper(current.getLeft(), newNode);
            }
        } else if (newNode.getValue() > current.getValue()) {
            if (current.getRight() == null) {
                current.setRight(newNode);
                newNode.setParent(current);
            } else {
                addHelper(current.getRight(), newNode);
            }
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
            if (nodeToRemove.getCount() > 1)
                nodeToRemove.setCount(nodeToRemove.getCount() - 1);
            else
                removeNode(nodeToRemove);
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
}
