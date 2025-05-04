package actions.ds.bst;

public class Node {
    private Node left;
    private Node right;
    private Node parent;
    private int value;
    private int count;

    public Node (int Value) {
        this.value = Value;
        this.count = 1;
        left = null;
        right = null;
        parent = null;
    }

    public int getValue(){ return value;}
    public void setValue(Integer value){ this.value = value;}

    public int getCount(){ return count;}
    public void setCount(int count){ this.count = count;}
    
    public Node getLeft(){ return left;}
    public void setLeft(Node left){ this.left = left;}
    
    public Node getRight(){ return right;}
    public void setRight(Node right){ this.right = right;}
    
    public Node getParent(){ return parent;}
    public void setParent(Node parent){ this.parent = parent;}
}
