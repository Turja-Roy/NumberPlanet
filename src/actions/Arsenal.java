package actions;

import actions.ds.bst.BinarySearchTree;
import actions.ds.stack.Stack;
import ui.Droplet;
import ui.Fireball;
import utilz.Constants.GameConstants;

public class Arsenal {

    private static Stack<Droplet> rainDrops;
    private static Stack<Droplet> dropletStack;
    private static BinarySearchTree<Droplet> dropletTree;

    private static Stack<Fireball> enemyFireShots;

    private static int enemyScore;
    private static int playerScore;

    static {
        rainDrops = new Stack<>();
        dropletTree = new BinarySearchTree<>();

        enemyFireShots = new Stack<>();

        enemyScore = 0;
        playerScore = 0;

        // Populating the rainDrops stack with random droplets
        for (int i=0 ; i<GameConstants.SHOTS_PER_ROUND ; i++) {
            rainDrops.push(new Droplet((int) (Math.random() * 100)));
        }

        // Populating the enemyFireShots stack with random fireballs
        for (int i=0 ; i<GameConstants.SHOTS_PER_ROUND ; i++) {
            enemyFireShots.push(new Fireball((int) (Math.random() * 70), true));
        }
    }

    // Action methods
    public static void collectDroplet(Droplet droplet) {
        if (droplet != null) {
            dropletTree.add(droplet);
        }
    }

    // Getters
    public static Stack<Droplet> getRainDrops() { return rainDrops; }
    public static Stack<Fireball> getEnemyFireShots() { return enemyFireShots; }
    public static BinarySearchTree<Droplet> getDropletTree() { return dropletTree; }
    public static int getEnemyScore() { return enemyScore; }
    public static int getPlayerScore() { return playerScore; }

    // Setters
    public static void enemyScoreIncrease() { enemyScore++; }
    public static void playerScoreIncrease() { playerScore++; }
    public static void enemyScoreIncrease(int score) { enemyScore += score; }
    public static void playerScoreIncrease(int score) { playerScore += score; }
}
