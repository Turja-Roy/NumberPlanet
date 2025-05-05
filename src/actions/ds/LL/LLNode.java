public class LLNode {
    int value;
    LLNode next;
    LLNode prev;
    int count;
    public LLNode(int value) {
        this.value = value;
        this.prev = null;
        this.next = null;
        this.count = 0;
    }
    public int getValue() {return value;}
    public void setValue(int value) {this.value = value;}
    public LLNode getPrev() {return prev;}
    public void setPrev(LLNode prev) {this.prev = prev;}
    public LLNode getNext() {return next;}
    public void setNext(LLNode next) {this.next = next;}
}
