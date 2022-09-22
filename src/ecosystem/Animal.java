package ecosystem;

import aaa.Behavior;
import aaa.Boid;
import aaa.DNA;
import aaa.Eye;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Animal extends Boid implements IAnimal{
    protected float energy;

    protected Animal(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt){
        super(pos, mass, radius, color, p, plt);
    }

    protected Animal(Animal a,boolean mutate, PApplet p, SubPlot plt){
        super(a.pos, a.mass, a.radius, a.color, p, plt);
        for(Behavior b: a.behaviors){
            this.addBehavior(b);
        }
        if(a.eye != null){
            eye = new Eye(this, a.eye);
        }
        dna = new DNA(a.dna, mutate);
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
        energy -= dt; //basic metabolism
        energy -= mass*Math.pow(vel.mag(), 2)*dt;
        Patch patch = (Patch)terrain.world2Cell(pos.x,pos.y);
        if(patch.getState() == WorldConstants.PatchType.OBSTACLE.ordinal()){
            energy -= 50*dt;
        }
    }

    @Override
    public boolean die(){
        return (energy < 0);
    }
}
