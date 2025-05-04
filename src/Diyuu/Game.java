public class Game {
    //BinarySearchTree bst = new BinarySearchTree();
    static int[] enemy = new int[100];
    static Ball[] dropped = new Ball[100];
    //static int caughtCount = 0;
    //Slider slider = new Slider();
    static int score = 0;

    public static void main(String[] args) {
        for(int i = 0; i < 100; i++){
            boolean[] isDropped = new boolean[100];
            int toDrop = (int)(Math.random()*100);
            while(isDropped[toDrop]){
                toDrop = (int)(Math.random()*100);
            }
            isDropped[toDrop] = true;

            boolean[] isEnemy = new boolean[100];
            int enemyNum = (int)(Math.random()*100);
            while(isEnemy[enemyNum]){
                enemyNum = (int)(Math.random()*100);
            }
            isEnemy[enemyNum] = true;
            enemy[i] = enemyNum;

            Ball ball = new Ball(toDrop);
            dropped[i] = ball;
            ball.draw();
            ball.fall();
            //Delay for a little bit 
            //Delay time such that all the balls are dropped before
            //the first dropped ball reaches the bottom half of the screen
        }
        start();
        /*
         * Let the half height of the box = 600/2 = 300
         * The speed of the balls = 10
         * The delay between each ball = t_delay
         * Then runtime = 100 * t_delay + 300/10
         * Run the following loop for runtime
         */
        Boolean runtime = true; // Placeholder for actual runtime condition
        while(runtime){
            for(Ball b : dropped){
                if(b.getY() > 0 && b.getY() < Slider.height &&
                 b.getX() > Slider.getX()-Slider.width/2 && b.getX() < Slider.x + Slider.width/2){
                    BinarySearchTree.add(b.getNum());
                }
            }
        }
        
        for(int i = 0; i < 100; i++){
            int attackNum = enemy[i];
            int defendNum = 0; // Placeholder for actual defend number taken through mouse click    
            defend(attackNum, defendNum);
        }
        
    }
    public static void start(){
        //Shows the start screen
        // Reads "Get ready to collect your numbers!"
    }
    public void interval(){
        // Shows the interval screen
        // Reads "Get ready for the enemy attack!"
        // In a new screen, show all the caught numbers using a grid, using traversal
        // Player can click on this numbers using the mouse
    }
    public static void defend(int attackNum, int defendNum){
        if(BinarySearchTree.search(defendNum) != null){
            BinarySearchTree.remove(defendNum);
            if(defendNum >= defendNum){
                score++;
                //Remove the number from the grid
            }
            else{
                //show a cross sign
            }
        } else {
            //Show a message that the number is not in the caught array
        }
    }
    public static void end(){
        //Shows the end screen
        // Reads "Game Over! Your score is: " + score
    }
}
