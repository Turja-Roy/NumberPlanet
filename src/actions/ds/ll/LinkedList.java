package actions.ds.ll;

public class LinkedList {
    Node head;
    Node tail;
    int size;
    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    public void add(int value) {
        Node newNode = new Node(value);
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
    public Node find(int value) {
        Node current = head;
        while (current != null) {
            if (current.getValue() == value) {
                return current;
            }
            current = current.getNext();
        }
        return null; // Not found
    }
    public void remove(int value) {
        Node current = find(value);
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
    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.getValue() + " ");
            current = current.getNext();
        }
        System.out.println();
    }
    public int getSize() {
        return size;
    }
}
