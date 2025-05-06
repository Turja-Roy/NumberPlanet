package actions.ds.ll;

public class DLL<T extends Comparable<T>> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public DLL() {
        head = null;
        tail = null;
        size = 0;
    }
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    }
    public Node<T> find(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.getData().compareTo(data) == 0) {
                return current;
            }
            current = current.getNext();
        }
        return null; // Not found
    }
    public void remove(T data) {
        Node<T> current = find(data);
        if (current != null) {
            if (current.getPrev() != null) {
                current.getPrev().setNext(current.getNext());
            } else {
                head = current.getNext(); // Update head if removing the first node
            }
            if (current.getNext() != null) {
                current.getNext().setPrev(current.getPrev());
            } else {
                tail = current.getPrev(); // Update tail if removing the last node
            }
            size--;
        }
    }

    public void traverse() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
        System.out.println();
    }

    // Getters
    public Node<T> getHead() { return head; }
    public Node<T> getTail() { return tail; }
    public int getSize() { return size; }
}
