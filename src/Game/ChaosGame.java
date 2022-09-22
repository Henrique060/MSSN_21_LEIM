package Game;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class ChaosGame implements IProcessingApp{

    PVector[] v;
    PVector it;
    @Override
    public void setup(PApplet p) {
        //p.size(600, 600);
        p.background(0);
        v = new PVector[3];
        v[0] = new PVector(p.width/2, 0);
        v[1] = new PVector(0, p.height-1);
        v[2] = new PVector(p.width-1, p.height-1);
        it = new PVector(p.width / 2 + p.random(-100, 100), p.height/2 + p.random(-100, 100));
    }



    @Override
    public void mousePressed(PApplet p) {

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


    @Override
    public void draw(PApplet p, float dt) {
        p.stroke(255);
        p.point(it.x, it.y);
        int n = (int)(p.random(0, 3));
        it.x = p.lerp(it.x, v[n].x, 0.5f);
        it.y = p.lerp(it.y, v[n].y, 0.5f);
    }
}
