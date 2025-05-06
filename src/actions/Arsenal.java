package actions;

import actions.ds.bst.BinarySearchTree;
import actions.ds.ll.DLL;
import actions.ds.stack.Stack;
import ui.Droplet;
import ui.Fireball;

public class Arsenal {

    private static Stack<Droplet> rainDrops;
    private static Stack<Droplet> dropletStack;
    private static BinarySearchTree<Droplet> dropletTree;

    private static Stack<Fireball> enemyFireShots;
    private static DLL<Fireball> playerDropletsDLL;

    private static int enemyScore;
    private static int playerScore;

    static {
        rainDrops = new Stack<>();
        dropletStack = new Stack<>();
        dropletTree = new BinarySearchTree<>();

        enemyFireShots = new Stack<>();
        playerDropletsDLL = new DLL<>();

        enemyScore = 0;
        playerScore = 0;

        // Populating the rainDrops stack with random droplets
        for (int i=0 ; i<100 ; i++) {
            rainDrops.push(new Droplet((int) (Math.random() * 100)));
        }

        // Populating the enemyFireShots stack with random fireballs
        for (int i=0 ; i<100 ; i++) {
            enemyFireShots.push(new Fireball((int) (Math.random() * 70), true));
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
            dropletTree.add(dropletStack.pop());
        }
    }
    public static void BSTtoList () {
        dropletTree.traverse(dropletTree.getRoot(), playerDropletsDLL);
    }

    // Getters
    public static Stack<Droplet> getRainDrops() { return rainDrops; }
    public static Stack<Droplet> getCollectedDroplets() { return dropletStack; }
    public static Stack<Fireball> getEnemyFireShots() { return enemyFireShots; }
    public static BinarySearchTree<Droplet> getDropletTree() { return dropletTree; }
    public static DLL<Fireball> getPlayerDropletsDLL() { return playerDropletsDLL; }
    public static int getEnemyScore() { return enemyScore; }
    public static int getPlayerScore() { return playerScore; }

    // Setters
    public static void enemyScoreIncrease() { enemyScore++; }
    public static void playerScoreIncrease() { playerScore++; }
    public static void enemyScoreIncrease(int score) { enemyScore += score; }
    public static void playerScoreIncrease(int score) { playerScore += score; }
}
