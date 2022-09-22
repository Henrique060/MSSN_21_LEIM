package ecosystem;

import aaa.Behavior;
import aaa.Boid;
import aaa.DNA;
import aaa.Eye;
import physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

import java.util.List;

public abstract class Animal extends Boid implements IAnimal{
    protected float energy;
    protected Body target;

    protected Animal(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt, PImage img){
        super(pos, mass, radius, color, p, plt, img);
    }

    protected Animal(Animal a,boolean mutate, PApplet p, SubPlot plt, String str, PImage img){
        super(a.pos, a.mass, a.radius, a.color, p, plt, img);
        for(Behavior b: a.behaviors){
            this.addBehavior(b);
        }
        if(a.eye != null){
            eye = new Eye(this, a.eye);
        }
        dna = new DNA(a.dna, mutate, str);
    }

    @Override
    public Animal reproduce(boolean mutate) {
        return null;
    }



    public List<Animal> eat (List<Animal> animals){
        return null;
    }

    @Override
    public void eat(Terrain terrain){

    }


    @Override
    public void energy_consumption(float dt, Terrain terrain) {
        energy -= dt; //basic metabolism
        energy -= mass*Math.pow(vel.mag(), 2)*dt;
        Patch patch = (Patch)terrain.world2Cell(pos.x,pos.y);
        if(patch.getState() == WorldConstants.PatchType.OBSTACLE.ordinal()){
            energy -= 50*dt;
        }
        //recuperam energia a beber agua
        if(patch.getState() == WorldConstants.PatchType.WATER.ordinal()){
            energy +=0.05;

        }
    }

    @Override
    public boolean die(){
        return (energy < 0);
    }

    @Override
    public void inWater(Terrain terrain){
        Patch patch = (Patch)terrain.world2Cell(pos.x,pos.y);
        if(patch.getState() == WorldConstants.PatchType.WATER.ordinal()){
            PVector newVel = new PVector(0.0f, 0.0f);
            setVel(newVel);
        }
    }
}
