package physics;

import physics.CelestialBody;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class SolarSystemApp implements IProcessingApp{

    private PImage img;

    private float sunMass = 1.989e30f;

    private float mercuryMass = 3.285e23f;
    private float venusMass = 4.867e24f;
    private float earthMass = 5.97e24f;
    private float marsMass = 6.39e24f;


    private float distMercurySun = 5.3e10f;
    private float distVenusSun = 1.08e11f;
    private float distEarthSun = 1.496e11f;
    private float distMarsSun = 2.3e11f;


    private float mercurySpeed = 4.8e4f;
    private float venusSpeed = 3.5e4f;
    private float earthSpeed = 3e4f;
    private float marsSpeed = 2.6e4f;


    private float[] viewport = {0.2f,0.2f,0.6f,0.6f};
    private double[] window = {-1.6*distEarthSun, 1.6*distEarthSun, -1.6*distEarthSun, 1.6*distEarthSun};

    private SubPlot plt;
    private CelestialBody sun, earth, mercury, venus, mars;

    private List<ParticleSystem> pss;

    private float speedUp = 60 * 60 * 24 * 30;

    @Override
    public void setup(PApplet p) {
        img = p.loadImage("physics/starblack.jpg");
        plt = new SubPlot(window, viewport, p.width, p.height);
        sun = new CelestialBody(new PVector(), new PVector(), sunMass, distEarthSun/10, p.color(255,231,1));
        mercury = new CelestialBody(new PVector(0, distMercurySun), new PVector(mercurySpeed,0), mercuryMass, distEarthSun/30, p.color(138,120,120));
        venus = new CelestialBody(new PVector(0, distVenusSun), new PVector(venusSpeed, 0), venusMass, distVenusSun/20, p.color(225,135,0));
        earth = new CelestialBody(new PVector(0, distEarthSun), new PVector(earthSpeed, 0), earthMass, distEarthSun/20, p.color(1, 255, 231));
        mars = new CelestialBody(new PVector(0, distMarsSun), new PVector(marsSpeed, 0), marsMass, distEarthSun/17, p.color(255,0,0));

        pss = new ArrayList<ParticleSystem>();
    }

    @Override
    public void draw(PApplet p, float dt) {

        float[] pp = plt.getBoundingBox();
        p.fill(0, 16);

        p.image(img,0,0,1920,1080);
        sun.display(p, plt);

        PVector vf = sun.attraction(venus);
        venus.applyForce(vf);
        venus.move(dt*speedUp);
        venus.display(p,plt);

        PVector mf = sun.attraction(mercury);
        mercury.applyForce(mf);
        mercury.move(dt*speedUp/3);
        mercury.display(p,plt);


        PVector f = sun.attraction(earth);
        earth.applyForce(f);
        earth.move(dt*speedUp);
        earth.display(p, plt);

        PVector marsf = sun.attraction(mars);
        mars.applyForce(marsf);
        mars.move(dt*speedUp);
        mars.display(p,plt);

        for(ParticleSystem ps : pss){
            ps.applyForce(new PVector(distEarthSun, -distEarthSun));
        }

        for(ParticleSystem ps : pss){
            ps.move(dt);
            ps.display(p,plt);
        }
    }

    /**
     * ao clicar com o rato geramos cometas de várias cores através do sistema de partículas
     */

    @Override
    public void mousePressed(PApplet p) {
        double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);

        int cor = p.color(p.random(255), p.random(255), p.random(255));
        float vx = p.random(distEarthSun,distEarthSun);
        float vy = p.random(distEarthSun,distEarthSun);
        float lifespan = p.random(1,2);

        ParticleSystem ps = new ParticleSystem(new PVector((float) ww[0], (float) ww[1]), new PVector(), 1f,distEarthSun/30,
                cor, lifespan, new PVector(vx,vy));

        pss.add(ps);
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
