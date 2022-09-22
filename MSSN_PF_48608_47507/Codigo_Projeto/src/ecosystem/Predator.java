package ecosystem;

import aaa.Eye;
import physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Predator extends Animal{

    private final PApplet p;
    private final SubPlot plt;
    private PImage img;

    public Predator(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt, PImage img) {
        super(pos, mass, radius, color, p, plt, img);
        this.p = p;
        this.plt = plt;
        energy = WorldConstants.INI_PREDATOR_ENERGY;
    }

    public Predator(Predator predator, Boolean mutate, PApplet p, SubPlot plt, PImage img){
        super(predator, mutate, p, plt, "Predator", img);
        this.p = p;
        this.plt = plt;
        this.img = img;
        energy = WorldConstants.INI_PREDATOR_ENERGY;
    }

    @Override
    public Animal reproduce(boolean mutate) {
        Animal child = null;
        if(energy > WorldConstants.PREDATOR_ENERGY_TO_REPRODUCE) {
            energy -= WorldConstants.INI_PREDATOR_ENERGY;
            child = new Predator(this, mutate, p, plt, img);
            if (mutate) child.mutateBehaviors();
        }
        return child;
    }


    @Override
    public void eat(Terrain terrain) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Animal> eat (List<Animal> animals){
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

    /**
     *
     * método que permite o predador Apex dar target a um alvo que encontre
     * próximo de si, dentro da distância estabelecida no olho em DNA;
     * caso a distância até ao proximo predador seja demasiado grande,
     * procuramos um outro animal
     *
     * @param p
     * @param obstacles
     * @param allPrey lista de presas
     *
     */

    public void updateTargetPrey(PApplet p, List<Body> obstacles, List<Animal> allPrey) {
        float dist = 0;
        if (!obstacles.contains(this.target) && this.target != null) {

            //distancia entre pred e presa
            dist = PVector.dist(this.getPos(), this.target.getPos());
            if (dist > this.getDNA().visionSafeDistance) {
                for (Animal animal : allPrey) {
                    float novaDistancia = PVector.dist(this.getPos(), animal.getPos());
                    if (novaDistancia < dist && this.target != animal && animal instanceof Prey) {
                        this.target = animal;
                    }
                }
                List<Body> tempTrackingBodies = new ArrayList<>();

                //adiciona alvo+obstaculos a lista
                tempTrackingBodies.add(this.target);
                tempTrackingBodies.addAll(obstacles);

                //novo olho com lista
                Eye eye = new Eye(this, tempTrackingBodies);
                this.setEye(eye);
            }
        }
    }


}
