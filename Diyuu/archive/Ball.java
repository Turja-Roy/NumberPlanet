public class Ball{
    double x = (int) Math.random() * 800;
    double y = 0;
    int yvel = 10;
    int num;
    //Timer timer;

    public Ball(int n){
        num = n;
        //timer = new Timer(1000, this);
        //timer.start();
    }
    //public void caught(){}
    public void fall(){
        y += yvel;
        if (y > 600) {
            return;
        }
    }
    public double getY(){ return y;} 
    public double getX(){ return x;}
    public int getNum(){ return num;}
    public void draw(){};
}
