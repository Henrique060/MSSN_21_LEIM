package ecosystem;

import physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

import java.util.List;

public class Prey extends Animal{

    private final PApplet parent;
    private final SubPlot plt;
    private final PImage img;

    public Prey(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt, PImage img) {
        super(pos, mass, radius, color, parent, plt, img);
        this.parent = parent;
        this.plt = plt;
        this.img = img;
        energy = WorldConstants.INI_PREY_ENERGY;
    }

    public Prey(Prey prey, boolean mutate, PApplet parent, SubPlot plt, PImage img){
        super(prey, mutate, parent, plt, "Prey", img);
        this.parent = parent;
        this.plt = plt;
        this.img = img;
        energy = WorldConstants.INI_PREY_ENERGY;
    }

    @Override
    public void eat(Terrain terrain){
        Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
        if(patch.getState() == WorldConstants.PatchType.FOOD.ordinal()){
            energy += WorldConstants.ENERGY_FROM_PLANT;
            patch.setFertile();
        }
    }


    /**
     *
     * descreve o que acontece dentro de água
     *
     * @param terrain tipo de terreno
     */
    @Override
    public void inWater(Terrain terrain){
        Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
        if(patch.getState() == WorldConstants.PatchType.WATER.ordinal()){
            energy +=.1f;
            patch.setWater();

        }
    }

    /**
     *
     * método responsável pela reprodução e mutação dos filhos
     *
     * @param mutate
     * @return filho evoluido
     */
    @Override
    public Animal reproduce(boolean mutate){
        Animal child = null;
        if(energy > WorldConstants.PREY_ENERGY_TO_REPRODUCE){
            energy -= WorldConstants.INI_PREY_ENERGY;
            child = new Prey(this, mutate, parent, plt, img);
            if (mutate) child.mutateBehaviors();
        }
        return child;
    }

    @Override
    public void updateTargetPrey(PApplet p, List<Body> obstacles, List<Animal> allPrey) {
        // TODO Auto-generated method stub

    }
}
