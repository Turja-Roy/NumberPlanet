public class LinkedList {
    LLNode head;
    LLNode tail;
    int size;
    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    public void add(int value) {
        LLNode newNode = new LLNode(value);
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
    public LLNode find(int value) {
        LLNode current = head;
        while (current != null) {
            if (current.getValue() == value) {
                return current;
            }
            current = current.getNext();
        }
        return null; // Not found
    }
    public void remove(int value) {
        LLNode current = find(value);
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
        LLNode current = head;
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
