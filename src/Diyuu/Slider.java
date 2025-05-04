public class Slider {
    static double x;
    static double y;
    static double width;
    static double height;
    static double speed;
    public Slider(){}
    public static double getX(){ return x;}
    public void move(){
        // keyboard input to move left and right
        String key = "left"; // Placeholder for actual key input
        if(key.equals("left")){
            x -= speed;
        } else if(key.equals("right")){
            x += speed;
        }
    }
}
