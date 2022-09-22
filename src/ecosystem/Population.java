package ecosystem;

import aaa.*;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Animal> allAnimals;
    private double[] window;
    private boolean mutate = true;

    private List<Body> allTrackingBodies = new ArrayList<Body>();

    public Population(PApplet parent, SubPlot plt, Terrain terrain) {
        window = plt.getWindow();
        allAnimals = new ArrayList<Animal>();

        List<Body> obstacles = terrain.getObstacles();

        for (int i = 0; i < WorldConstants.INI_PREY_POPULATION; i++) {
            PVector pos = new PVector(parent.random((float)window[0],(float)window[1]),
                                        parent.random((float)window[2],(float)window[3]));
            int color = parent.color(
                    WorldConstants.PREY_COLOR[0],
                    WorldConstants.PREY_COLOR[1],
                    WorldConstants.PREY_COLOR[2]);
            int color2 = parent.color(
                    WorldConstants.PREDATOR_COLOR[0],
                    WorldConstants.PREDATOR_COLOR[1],
                    WorldConstants.PREDATOR_COLOR[2]);
            Animal a = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE, color, parent, plt);
            a.addBehavior(new Wander(1));
            a.addBehavior(new AvoidObstacle(1000));
            Eye eye = new Eye(a, obstacles);
            a.setEye(eye);
            allAnimals.add(a);


            Animal b = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE, color2, parent, plt);
            b.addBehavior(new Wander(1));
            b.addBehavior(new AvoidObstacle(1000));
            Eye eye3 = new Eye(b, obstacles);

            b.setEye(eye3);
            allAnimals.add(b);
        }
    }

    public void update(float dt, Terrain terrain) {
        move(terrain, dt);
        eat(terrain);
        energy_consumption(dt, terrain);
        reproduce(mutate);
        die();
    }

    public void move(Terrain terrain, float dt) {
        for (Animal a : allAnimals)
            a.applyBehaviors(dt);
    }

    private void eat(Terrain terrain) {
        for (Animal a : allAnimals)
            a.eat(terrain);
    }

    public void energy_consumption(float dt, Terrain terrain) {
        for (Animal a : allAnimals)
            a.energy_consumption(dt, terrain);
    }

    public void die() {
        for (int i = allAnimals.size() - 1; i >= 0; i--) {
            Animal a = allAnimals.get(i);
            if (a.die()) {
                allAnimals.remove(a);
            }
        }
    }

    public void reproduce(boolean mutate) {
        for (int i = allAnimals.size() - 1; i >= 0; i--) {
            Animal a = allAnimals.get(i);
            Animal child = a.reproduce(mutate);
            if (child != null)
                allAnimals.add(child);
        }
    }

    public void display(PApplet p, SubPlot plt) {
        for (Animal a : allAnimals)
            a.display(p, plt);
    }

    public int getNumAnimals() {
        return allAnimals.size();
    }


    public float getMeanMaxSpeed() {
        float sum = 0;
        for (Animal a : allAnimals)
            sum += a.getDNA().maxSpeed;
        return sum / allAnimals.size();
    }

    public float getStdMaxSpeed() {
        float mean = getMeanMaxSpeed();
        float sum = 0;
        for (Animal a : allAnimals)
            sum += Math.pow(a.getDNA().maxSpeed - mean, 2);
        return (float) Math.sqrt(sum / allAnimals.size());
    }

    public float[] getMeanWeights() {
        float[] sums = new float[2];
        for (Animal a : allAnimals) {
            sums[0] += a.getBehaviors().get(0).getWeight();
            //sums[1] += a.getBehaviors().get(1).getWeight();
        }
        sums[0] /= allAnimals.size();
        //sums[1] /= allAnimals.size();
        return sums;
    }
}
