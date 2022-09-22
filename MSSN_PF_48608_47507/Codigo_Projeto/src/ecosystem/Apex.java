package ecosystem;

import java.util.ArrayList;
import java.util.List;

import aaa.Eye;
import physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

public class Apex extends Animal{

    private final PApplet p;
    private final SubPlot plt;
    private PImage img;
    public Apex(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt, PImage img) {
        super(pos, mass, radius, color, p, plt, img);
        this.p = p;
        this.plt = plt;
        energy = WorldConstants.INI_APEX_ENERGY;
    }

    public Apex(Apex apex, Boolean mutate, PApplet p, SubPlot plt, PImage img){
        super(apex, mutate, p, plt,"Apex", img);
        this.p = p;
        this.plt = plt;
        this.img = img;
        energy = WorldConstants.INI_APEX_ENERGY;
    }

    @Override
    public Animal reproduce(boolean mutate) {
        Animal child = null;
        if(energy > WorldConstants.APEX_ENERGY_TO_REPRODUCE) {
            energy -= WorldConstants.INI_APEX_ENERGY;
            child = new Apex(this, mutate, p, plt, img);
            if (mutate) child.mutateBehaviors();
        }
        return child;
    }

    @Override
    public void eat(Terrain terrain) {
        // TODO Auto-generated method stub

    }

    /**
     *
     * novo método criado
     * dá override ao método 'eat' de Animal, superclasse
     * recebe uma lista pela qual vai ser responsável por remover o animal
     * em que toca, a uma certa distância
     */

    @Override
    public List<Animal> eat (List<Animal> animals){
        PVector mePos = this.getPos().copy();

        for(Animal animal : animals){
            if(animal instanceof Predator){
                PVector preyPos = animal.getPos().copy();
                float distance = PVector.dist(mePos, preyPos);
                if(distance < this.radius*5){
                    energy += WorldConstants.APEX_ENERGY_FROM_PREDATOR;
                    animals.remove(animal);
                    return animals;
                }
            }
            if(animal instanceof Prey){
                PVector preyPos = animal.getPos().copy();
                float distance = PVector.dist(mePos, preyPos);
                if(distance < this.radius*5){
                    energy += WorldConstants.APEX_ENERGY_FROM_PREY;
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

            //esta distancia é entre o predador e a presa
            dist = PVector.dist(this.getPos(), this.target.getPos());
            if (dist > this.getDNA().visionSafeDistance) {
                for (Animal animal : allPrey) {
                    float novaDistancia = PVector.dist(this.getPos(), animal.getPos());
                    if (novaDistancia < dist && this.target != animal && animal instanceof Predator) {
                        this.target = animal;
                    }
                }
                List<Body> tempTrackingBodies = new ArrayList<>();

                //adiciona o alvo + obstaculos à lista
                tempTrackingBodies.add(this.target);
                tempTrackingBodies.addAll(obstacles);

                //novo olho criado com a lista
                Eye eye = new Eye(this, tempTrackingBodies);
                this.setEye(eye);
            }
        }
    }

}
