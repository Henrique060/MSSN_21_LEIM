package aaa;

import physics.Body;
import tools.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

import java.util.ArrayList;
import java.util.List;

public class BoidApp implements IProcessingApp {

    private Boid b;
    private double[] window = {-10, 10, -10, 10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private Body target;
    private List<Body> allTrackingBodies;
    private int index = 0;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        b = new Boid(new PVector(), 1, 0.5f, p.color(0), p, plt);
        b.addBehavior(new Seek(1f));
        b.addBehavior(new Flee(1f));
        b.addBehavior(new Wander(1f));
        target = new Body(new PVector(), new PVector(), 1f, 0.3f, p.color(255, 0, 0));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(target);
        Eye eye = new Eye(b, allTrackingBodies);
        b.setEye(eye);

        System.out.println("PRESS KEYS TO CHANGE BEHAVIOR");
        System.out.println("0 - Seek");
        System.out.println("1 - Flee");
        System.out.println("2 - Wander");
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(255);

        b.applyBehavior(index, dt);

        b.display(p, plt);
    }

    @Override
    public void mousePressed(PApplet p) {
        double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
        target.setPos(new PVector((float) ww[0], (float) ww[1]));
    }

    @Override
    public void keyPressed(PApplet p) {
        if(p.key == '0'){
            index = 0;
        }
        if(p.key == '1'){
            index = 1;
        }
        if(p.key == '2'){
            index = 2;
        }
    }

    @Override
    public void mouseDragged(PApplet p) {

    }

    @Override
    public void mouseReleased(PApplet p) {

    }
}
