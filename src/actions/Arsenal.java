package actions;

import java.util.ArrayList;

import actions.ds.bst.BinarySearchTree;
import actions.ds.stack.Stack;
import ui.Droplet;

public class Arsenal {
    private Stack rainDrops;
    private Stack dropletStack;

    private BinarySearchTree dropletTree;

    public Arsenal() {
        rainDrops = new Stack();
        dropletStack = new Stack();
        dropletTree = new BinarySearchTree();

        // Populating the rainDrops stack with random droplets
        for (int i=0 ; i<100 ; i++) {
            rainDrops.push(new Droplet((int) (Math.random() * 100)));
        }
    }

    // Action methods
    public void collectDroplet(Droplet droplet) {
        if (droplet != null) {
            dropletStack.push(droplet);
        }
    }
    public void StackToBST () {
        while (!dropletStack.isEmpty()) {
            dropletTree.add(dropletStack.pop().getValue());
        }
        // dropletTree.traverse();
    }

    // Getters
    public Stack getRainDrops() {
        return rainDrops;
    }
    public Stack getCollectedDroplets() {
        return dropletStack;
    }
}
