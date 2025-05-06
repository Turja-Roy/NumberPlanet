package actions.ds.ll;

public class Node<T extends Comparable<T>> {

    private T data;
    private int count;
    private Node<T> next;
    private Node<T> prev;

    public Node(T data) {
        this.data = data;
        this.prev = null;
        this.next = null;
        this.count = 0;
    }

    public T getData() {return data;}
    public void setData(T data) {this.data = data;}

    public Node<T> getPrev() {return prev;}
    public void setPrev(Node<T> prev) {this.prev = prev;}

    public Node<T> getNext() {return next;}
    public void setNext(Node<T> next) {this.next = next;}
}
