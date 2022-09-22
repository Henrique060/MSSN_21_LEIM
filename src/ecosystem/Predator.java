package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.List;

public class Predator extends Animal{

    private PApplet p;
    private SubPlot plt;

    public Predator(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
        super(pos, mass, radius, color, p, plt);
        this.p = p;
        this.plt = plt;
        energy = WorldConstants.INI_PREY_ENERGY;
    }

    public Predator(Predator predator, Boolean mutate, PApplet p, SubPlot plt){
        super(predator, mutate, p, plt);
        this.p = p;
        this.plt = plt;
    }

    @Override
    public Animal reproduce(boolean mutate) {
        Animal child = null;
        if(energy > WorldConstants.PREDATOR_ENERGY_TO_REPRODUCE) {
            energy -= WorldConstants.INI_PREDATOR_ENERGY;
            child = new Predator(this, mutate, p, plt);
            if (mutate) child.mutateBehaviors();
        }
        return child;
    }

    @Override
    public void eat(Terrain terrain) {
        Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
        if(patch.getState() == WorldConstants.PatchType.FOOD.ordinal()) {
            energy += WorldConstants.ENERGY_FROM_PLANT;
            patch.setFertile();
        }
    }


    public List<Animal> eat(List<Animal> animals){
        PVector mePos = this.getPos().copy();

        for(Animal animal : animals){
            if(animal instanceof Prey){
                PVector preyPos = animal.getPos().copy();
                float distance = PVector.dist(mePos, preyPos);
                if(distance < this.radius*4){
                    energy += WorldConstants.ENERGY_FROM_PREY;
                    animals.remove(animal);
                    return animals;
                }
            }
        }
        return animals;
    }


}
