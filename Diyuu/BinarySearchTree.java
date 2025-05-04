public class BinarySearchTree {
    public static Node root;
    public BinarySearchTree() {
        root = null;
    }
    public static void add(int value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
        } else {
            addHelper(root, newNode);
        }
    }
    private static void addHelper(Node current, Node newNode) {
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
    public static Node search(int value) {
        return searchHelper(root, value);
    }
    private static Node searchHelper(Node current, int value) {
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
    public static void remove(int value) {
        Node nodeToRemove = search(value);
        if (nodeToRemove != null) {
            removeNode(nodeToRemove);
        }
    }
    private static void removeNode(Node nodeToRemove) {
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
    private static Node findMin(Node current) {
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }
    // Inorder traversal
    public static void traversal() {
        traversalHelper(root);
    }
    private static void traversalHelper(Node current) {
        if (current != null) {
            traversalHelper(current.getLeft());
            traversalHelper(current.getRight());
        }
    }
}
