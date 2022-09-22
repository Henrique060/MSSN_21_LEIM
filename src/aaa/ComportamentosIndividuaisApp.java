package aaa;

import processing.sound.SoundFile;
import physics.Body;
import tools.SubPlot;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import setup.IProcessingApp;

import java.util.ArrayList;
import java.util.List;

public class ComportamentosIndividuaisApp implements IProcessingApp {

    SoundFile file;

    private PImage backgroundImg;
    private Boid b1, b2;
    private final double[] window = {-10, 10, -10, 10};
    private final float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private Body target;
    private List<Body> allTrackingBodies;
    private int index = 0;
    private float seekForce;
    private int point = 0;

    private float massb1 = 1f;



    @Override
    public void setup(PApplet p) {

        file = new SoundFile(p, "aaa/mario.mp3");
        file.play();
        file.amp(1);
        backgroundImg = p.loadImage("aaa/sky.jpg");
        plt = new SubPlot(window, viewport, p.width, p.height);

        b1 = new Boid(new PVector(), massb1, 0.5f, p.color(255, 150, 0), p, plt);

        b1.addBehavior(new Seek(seekForce));     // behavior[0]
        //b1.addBehavior(new Arrive(1f));   // behavior[1]
        //b1.addBehavior(new Separate(1f)); // behavior[2]
        b1.addBehavior(new Flee(1f));     // behavior[3]
        b1.addBehavior(new Wander(1f));   // behavior[4]

        target = new Body(new PVector(), new PVector(), 1f, 0.2f, p.color(255, 0, 150));
        allTrackingBodies = new ArrayList<>();
        allTrackingBodies.add(target);
        Eye b1Eye = new Eye(b1, allTrackingBodies);
        b1.setEye(b1Eye);

        b2 = new Boid(new PVector(), 1, 0.5f, p.color(150, 255, 0), p, plt);
        b2.addBehavior(new Wander(seekForce));
        b2.addBehavior(new Flee(1f));
        Eye b2Eye = new Eye(b2, allTrackingBodies);
        b2.setEye(b2Eye);

        // Instruções
        System.out.println("PRESS KEYS TO CHANGE BEHAVIOR");
        System.out.println("0 - Seek");
        System.out.println("1 - Flee");
        System.out.println("2 - Wander");
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.image(backgroundImg, 0, 0, 800, 600);

        b1.applyBehavior(index,dt);
        b2.applyBehaviors(dt);

        b1.display(p, plt);
        b2.display(p, plt);


        target.display(p, plt);
    }

    @Override
    public void mousePressed(PApplet p) {

        double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
        target.setPos(new PVector((float) ww[0], (float) ww[1]));
    }

    @Override
    public void keyPressed(PApplet p) {
        if(p.key == '3'){
            b1.dna.maxSpeed += 2f;
        }
        if (p.key == '4' && b1.dna.maxSpeed > 0.3) {
            b1.dna.maxSpeed -= 2f;
        }

    }

    @Override
    public void mouseDragged(PApplet p) {

    }

    @Override
    public void mouseReleased(PApplet p) {

    }
}
