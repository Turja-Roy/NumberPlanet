package actions.ds.stack;

import ui.Droplet;

public class Node {
    private Droplet data;
    private Node next;

    public Node (Droplet data) {
        this.data = data;
        this.next = null;
    }

    public Droplet getData () {
        return data;
    }

    public Node getNext () {
        return next;
    }

    public void setNext (Node next) {
        this.next = next;
    }
}
