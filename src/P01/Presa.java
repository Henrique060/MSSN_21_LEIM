package P01;

import aaa.Behavior;
import aaa.Boid;
import ecosystem.*;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Presa extends Boid implements IAnimal {
    private float energy;

    protected Presa(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
        super(pos, mass, radius, color, p, plt);
    }

    @Override
    public Animal reproduce(boolean mutate) {
        return null;
    }

    @Override
    public void eat(Terrain terrain) {

    }

    @Override
    public void energy_consumption(float dt, Terrain terrain) {
        energy -= dt;
        energy -= mass*Math.pow(vel.mag(),2) * dt;
        Patch patch = (Patch)terrain.world2Cell(pos.x,pos.y);
        if(patch.getState() == WorldConstants.PatchType.OBSTACLE.ordinal()){
            energy -= 50*dt;
        }
    }

    @Override
    public boolean die() {
        return false;
    }
}
