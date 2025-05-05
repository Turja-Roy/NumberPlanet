package actions;

import java.util.ArrayList;

import actions.ds.bst.BinarySearchTree;
import actions.ds.stack.Stack;
import ui.Droplet;
import ui.Fireball;

public class Arsenal {

    private static Stack<Droplet> rainDrops;
    private static Stack<Droplet> dropletStack;
    private static BinarySearchTree dropletTree;

    private static Stack<Fireball> enemyFireShots;
    private static int[] playerDroplets;

    private static int enemyScore;
    private static int playerScore;

    static {
        rainDrops = new Stack<>();
        dropletStack = new Stack<>();
        dropletTree = new BinarySearchTree();

        enemyFireShots = new Stack<>();
        playerDroplets = new int[0];

        enemyScore = 0;
        playerScore = 0;

        // Populating the rainDrops stack with random droplets
        for (int i=0 ; i<10 ; i++) {
            rainDrops.push(new Droplet((int) (Math.random() * 100)));
        }

        // Populating the enemyFireShots stack with random fireballs
        for (int i=0 ; i<80 ; i++) {
            enemyFireShots.push(new Fireball((int) (Math.random() * 100), true));
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
    public static void BSTtoArray () {
        playerDroplets = new int[dropletTree.getSizeIncludingDups()];
        ArrayList<Integer> dropletList = new ArrayList<>();
        dropletTree.traverse(dropletTree.getRoot(), dropletList);
        for (int i=0 ; i<playerDroplets.length ; i++) {
            playerDroplets[i] = dropletList.get(i);
        }
    }

    // Getters
    public static Stack<Droplet> getRainDrops() {
        return rainDrops;
    }
    public static Stack<Droplet> getCollectedDroplets() {
        return dropletStack;
    }
    public static Stack<Fireball> getEnemyFireShots() {
        return enemyFireShots;
    }
    public static int[] getPlayerDroplets() {
        return playerDroplets;
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
