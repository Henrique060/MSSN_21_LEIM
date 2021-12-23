import processing.core.PApplet;

public class ZOOG implements iZOOG{
    @Override
    public void settings(PApplet parent) {
        parent.size(600,600);
    }

    @Override
    public void setup(PApplet parent) {
        parent.fill(150);
    }

    @Override
    public void draw(PApplet parent, float ft) {
        parent.ellipse(100,70,60,60);
    }

    public static void main(String[] args) {
        PApplet.main(ZOOG.class);
    }
}

