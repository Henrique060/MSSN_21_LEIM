package physics;

import tools.SubPlot;
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PImage;

public class Body extends Mover{

    protected int color;
    protected float radius;

    public Body(PVector pos, PVector vel, float mass, float radius, int color) {
        super(pos, vel, mass,radius);
        this.color = color;
        this.radius = radius;
    }

    public Body(PVector pos){
        super(pos, new PVector(), 0f,0);
    }

    public void display(PApplet p, SubPlot plt){
        p.pushStyle();

        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        float[] r = plt.getVectorCoord(radius, radius);

        p.noStroke();
        p.fill(color);
        p.circle(pp[0], pp[1], r[0]);

        p.popStyle();
    }

    public void display(PApplet p, SubPlot plt, PImage imagem) {
        // TODO Auto-generated method stub
        p.pushMatrix();
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        p.translate(pp[0], pp[1]);
        p.image(imagem, 0, 0);
        p.popMatrix();
    }

}
