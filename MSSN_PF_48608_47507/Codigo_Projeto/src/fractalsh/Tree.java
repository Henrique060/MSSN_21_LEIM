package fractalsh;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Tree {

    private final LSystem lsys;
    private final Turtle turtle;
    private final PVector position;

    private float length;
    private float growthRate;

    private final int numberOfSeasonsToGrow;
    private final float scalingFactor;
    private final float intervalBetweenSeasons;
    private float now;
    private float nextSeasonTime;
    private final PApplet p;
    private final int color;

    public Tree(String axiom, Rule[] rules, PVector position, float referenceLength, float angle,
                int niter, float scalingFactor, float intervalBetweenSeasons, PApplet p, int color){
        lsys = new LSystem(axiom, rules);

        length = 0;
        growthRate = referenceLength/intervalBetweenSeasons;
        turtle = new Turtle(0, angle);

        this.position = position;
        numberOfSeasonsToGrow = niter;
        this.scalingFactor = scalingFactor;
        this.intervalBetweenSeasons = intervalBetweenSeasons;
        now = p.millis() / 1000f;
        nextSeasonTime = now + intervalBetweenSeasons;
        this.p = p;
        this.length = length;
        this.color = color;

    }

    public void grow(float dt){
        now += dt;

        if(now < nextSeasonTime){
            length += growthRate * dt;
            turtle.setLength(length);
        }
         else if(lsys.getGeneration() < numberOfSeasonsToGrow){
            lsys.nextGeneration();
            length *= scalingFactor;
            growthRate *= scalingFactor;
            turtle.setLength(length);
            nextSeasonTime = now + intervalBetweenSeasons;
        }
    }

    public void display(PApplet p, SubPlot plt){
        p.pushMatrix();
        turtle.setPose(position, (float)Math.PI/2, plt, p);
        turtle.render(p, lsys, plt);
        p.popMatrix();
    }
}
