package actions;

import java.util.ArrayList;

// import actions.ds.bst.BinarySearchTree;
import actions.ds.stack.Stack;
import ui.Droplet;

public class Arsenal {
    private Stack rainDrops;
    private Stack dropletStack;

    // private BinarySearchTree dropletTree;

    public Arsenal() {
        rainDrops = new Stack();
        dropletStack = new Stack();

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
    // public void StackToBST () {
    //     // Convert the stack dropletStack to a binary search tree (BST)
    //     // This is a placeholder for the actual implementation
    //     dropletTree = new BinarySearchTree();
    //
    //     while (!dropletStack.isEmpty()) {
    //         Droplet droplet = dropletStack.pop();
    //         dropletTree.add(droplet.getValue());
    //     }
    // }

    // Getters
    public Stack getRainDrops() {
        return rainDrops;
    }
    public Stack getCollectedDroplets() {
        return dropletStack;
    }
}
