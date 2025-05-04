package actions;

import java.util.ArrayList;

import actions.ds.bst.BinarySearchTree;
import actions.ds.stack.Stack;
import ui.Droplet;

public class Arsenal {

    private static Stack rainDrops;
    private static Stack dropletStack;
    private static BinarySearchTree dropletTree;

    private static Stack fireShots;

    private static int enemyScore;
    private static int playerScore;

    static {
        rainDrops = new Stack();
        dropletStack = new Stack();
        dropletTree = new BinarySearchTree();

        fireShots = new Stack();

        enemyScore = 0;
        playerScore = 0;

        // Populating the rainDrops stack with random droplets
        for (int i=0 ; i<10 ; i++) {
            rainDrops.push(new Droplet((int) (Math.random() * 100)));
        }

        // Populating the fireShots stack with random fireballs
        for (int i=0 ; i<80 ; i++) {
            fireShots.push(new Droplet((int) (Math.random() * 100)));
        }
    }

    // Action methods
    public static void collectDroplet(Droplet droplet) {
        if (droplet != null) {
            dropletStack.push(droplet);
        }
    }
    public static void StackToBST () {
        while (!dropletStack.isEmpty()) {
            dropletTree.add(dropletStack.pop().getValue());
        }
        // dropletTree.traverse();
    }

    // Getters
    public static Stack getRainDrops() {
        return rainDrops;
    }
    public static Stack getCollectedDroplets() {
        return dropletStack;
    }
    public static Stack getFireShots() {
        return fireShots;
    }

    // Setters
    public static void enemyScoreIncrease() {
        enemyScore++;
    }
    public static void playerScoreIncrease() {
        playerScore++;
    }
    public static void enemyScoreIncrease(int score) {
        enemyScore += score;
    }
    public static void playerScoreIncrease(int score) {
        playerScore += score;
    }
}
