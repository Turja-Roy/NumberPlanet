package actions.ds.ll;

public class Node {
    int value;
    Node next;
    Node prev;
    int count;
    public Node(int value) {
        this.value = value;
        this.prev = null;
        this.next = null;
        this.count = 0;
    }
    public int getValue() {return value;}
    public void setValue(int value) {this.value = value;}
    public Node getPrev() {return prev;}
    public void setPrev(Node prev) {this.prev = prev;}
    public Node getNext() {return next;}
    public void setNext(Node next) {this.next = next;}
}
