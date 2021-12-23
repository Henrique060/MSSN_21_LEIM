import processing.core.PApplet;

public class firstborn  extends PApplet{

    public void settings(){
        size(300,300);
    }
    public void setup(){
        fill(0, 128, 0);
    }
    public void draw(){
        ellipse(width/2, height/2, 200, 200);
    }

    public static void main(String[] args) {
        PApplet.main(firstborn.class);
    }
}
