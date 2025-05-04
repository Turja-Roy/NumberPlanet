package actions.ds.stack;

import ui.Droplet;

public class Stack {
    private Node top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    public void push(Droplet data) {
        Node newNode = new Node(data);
        newNode.setNext(top);
        top = newNode;
        size++;
    }

    public Droplet pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        Droplet data = top.getData();
        top = top.getNext();
        size--;

        return data;
    }
    
    public Droplet peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        return top.getData();
    }

    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }

    public void clear() {
        top = null;
        size = 0;
    }
}
