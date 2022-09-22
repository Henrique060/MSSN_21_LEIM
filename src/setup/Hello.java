package setup;

import processing.core.PApplet;

public class Hello implements IProcessingApp {

    private int c; // uma cor

    public void setup(PApplet p) {
        //p.fill(255,0,0);
        c = p.color(255,0,0);
    }

    public void draw(PApplet p, float dt) {
        p.fill(c);
        p.circle(p.mouseX, p.mouseY,50);
        System.out.println(1/dt);
    }

    public void mousePressed(PApplet p){
        c = p.color(0,255,0);
    }

    @Override
    public void keyPressed(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }

    @Override
    public void mouseReleased(PApplet p) {

    }
}
