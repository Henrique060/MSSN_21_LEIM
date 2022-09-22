package ecosystem;

import aaa.AvoidObstacle;
import aaa.Eye;
import aaa.Pursuit;
import aaa.Wander;
import physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private final List<Animal> allApex;
    private final List<Body> obstacles;
    private final boolean mutate = true;
    private final PImage imgPrey;
    private final PImage imgPredator;
    private final PImage imgApex;
    private final float targetUpdate = 5;
    private final float[] viewport = {0, 0, 1, 1};
    private List<Animal> allAnimals;
    private List<Animal> allPredator;
    private float timer;

    /**
     *
     * este construtor atribui os comportamentos a cada espécie
     * atribui também as espécies que servem de alimento aos predadores
     * define também o olho para que possam saber se fogem do perigo ou vão ao encontro de alimento
     */
    public Population(PApplet parent, SubPlot plt, Terrain terrain) {
        double[] window = plt.getWindow();
        plt = new SubPlot(window, viewport, parent.width, parent.height);


        timer = 0;
        allAnimals = new ArrayList<>();
        allPredator = new ArrayList<>();
        allApex = new ArrayList<>();

        imgPrey = parent.loadImage("imgs/rabbit.png");
        imgPrey.resize(20, 20);

        imgPredator = parent.loadImage("imgs/Wolf.png");
        imgPredator.resize(60, 50);

        imgApex = parent.loadImage("imgs/man.png");
        imgApex.resize(40, 40);

        obstacles = terrain.getObstacles();


        for (int i = 0; i < WorldConstants.INI_PREY_POPULATION; i++) {
            PVector pos = new PVector(parent.random((float) window[0], (float) window[1]),
                    parent.random((float) window[2], (float) window[3]));

            Animal a = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE, 0, parent, plt, imgPrey);
            a.addBehavior(new Wander(1));
            a.addBehavior(new AvoidObstacle(10));

            allAnimals.add(a);
        }

        for (int i = 0; i < WorldConstants.INI_PREDATOR_POPULATION; i++) {
            PVector pos = new PVector(parent.random((float) window[0], (float) window[1]),
                    parent.random((float) window[2], (float) window[3]));

            Animal a = new Predator(pos, WorldConstants.PREDATOR_MASS, WorldConstants.PREDATOR_SIZE, 0, parent, plt, imgPredator);
            a.addBehavior(new Wander(1));
            a.addBehavior(new Pursuit(1));
            a.addBehavior(new AvoidObstacle(5));


            List<Body> allTrackingBodies = new ArrayList<>();

            Prey target = (Prey) allAnimals.get((int) parent.random(0, allAnimals.size()));

            allTrackingBodies.add(target);
            a.target = target;
            allTrackingBodies.addAll(obstacles);

            Eye eye = new Eye(a, allTrackingBodies);
            a.setEye(eye);

            allPredator.add(a);
        }

        for (int i = 0; i < WorldConstants.INI_APEX_POPULATION; i++) {
            PVector pos = new PVector(parent.random((float) window[0], (float) window[1]),
                    parent.random((float) window[2], (float) window[3]));

            Animal a = new Apex(pos, WorldConstants.APEX_MASS, WorldConstants.APEX_SIZE, 0, parent, plt, imgApex);

            a.addBehavior(new Wander(1));
            a.addBehavior(new Pursuit(1));
            a.addBehavior(new AvoidObstacle(5));


            List<Body> allTrackingBodies = new ArrayList<>();
            allTrackingBodies.addAll(allPredator);
            allTrackingBodies.addAll(allAnimals);

            a.target = allTrackingBodies.get((int) parent.random(0, allTrackingBodies.size()));

            Eye eye = new Eye(a, allTrackingBodies);
            a.setEye(eye);

            allApex.add(a);
        }


        for (Animal animal : allAnimals) {
            List<Body> allTrackingBodies = new ArrayList<>();
            allTrackingBodies.addAll(obstacles);
            allTrackingBodies.addAll(allPredator);
            allTrackingBodies.addAll(allApex);
            Eye eye = new Eye(animal, allTrackingBodies);
            animal.setEye(eye);
        }

    }

    public void update(PApplet p, float dt, Terrain terrain) {
        timer += dt;
        die();
        move(dt);
        eat(terrain);
        swim(terrain);
        energy_consumption(dt, terrain);
        reproduce(mutate);
        timer = updateTargets(p, timer);
    }

    //serve para que os alvos mudem durante as iterações do programa
    private float updateTargets(PApplet p, float timer) {
        List<Body> predObstacles = new ArrayList<>();
        predObstacles.addAll(obstacles);
        predObstacles.addAll(allApex);

        if (timer > targetUpdate) {
            for (Animal animal : allPredator)
                animal.updateTargetPrey(p, predObstacles, allAnimals);

            List<Animal> allAnimalsAsPrey = new ArrayList<>();
            allAnimalsAsPrey.addAll(allAnimals);
            allAnimalsAsPrey.addAll(allPredator);
            for (Animal animal : allApex)
                animal.updateTargetPrey(p, obstacles, allAnimalsAsPrey);

            timer = 0;
        }

        return timer;
    }

    public void move(float dt) {
        for (Animal a : allAnimals)
            a.applyBehaviors(dt);

        for (Animal a : allPredator)
            a.applyBehaviors(dt);

        for (Animal a : allApex)
            a.applyBehaviors(dt);
    }

    //comportamento dentro de água
    private void swim(Terrain terrain) {
        List<Animal> animais = new ArrayList<>();

        for (Animal a : allAnimals) {
            a.inWater(terrain);
            animais.add(a);
        }

    }

    private void eat(Terrain terrain) {
        List<Animal> animais = new ArrayList<>();

        for (Animal a : allAnimals) {
            a.eat(terrain);
            animais.add(a);
        }

        for (Animal a : allPredator) {
            animais = a.eat(animais);
            animais.add(a);
        }

        for (Animal a : allApex) {
            animais = a.eat(animais);
        }

        allPredator = new ArrayList<>();
        allAnimals = new ArrayList<>();

        for (Animal a : animais) {
            if (a instanceof Prey) {
                allAnimals.add(a);

            }

            if (a instanceof Predator) {
                allPredator.add(a);
            }
        }
    }


    public void energy_consumption(float dt, Terrain terrain) {
        for (Animal a : allAnimals)
            a.energy_consumption(dt, terrain);

        for (Animal a : allPredator)
            a.energy_consumption(dt, terrain);

        for (Animal a : allApex)
            a.energy_consumption(dt, terrain);
    }

    public void die() {
        for (int i = allAnimals.size() - 1; i >= 0; i--) {
            Animal a = allAnimals.get(i);
            if (a.die())
                allAnimals.remove(a);
        }

        for (int i = allPredator.size() - 1; i >= 0; i--) {
            Animal a = allPredator.get(i);
            if (a.die()) {
                allPredator.remove(a);
            }
        }

        for (int i = allApex.size() - 1; i >= 0; i--) {
            Animal a = allApex.get(i);
            if (a.die()) {
                allApex.remove(a);
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

        for (int i = allPredator.size() - 1; i >= 0; i--) {
            Animal a = allPredator.get(i);
            Animal child = a.reproduce(mutate);
            if (child != null)
                allPredator.add(child);
        }

        for (int i = allApex.size() - 1; i >= 0; i--) {
            Animal a = allApex.get(i);
            Animal child = a.reproduce(mutate);
            if (child != null)
                allApex.add(child);
        }

        List<Body> preyObstacles = new ArrayList<>();
        preyObstacles.addAll(obstacles);
        preyObstacles.addAll(allPredator);
        preyObstacles.addAll(allApex);

        for (Animal a : allAnimals) {
            Eye e = new Eye(a, preyObstacles);
            a.setEye(e);
        }

        List<Body> predObstacles = new ArrayList<>();
        predObstacles.addAll(obstacles);
        predObstacles.addAll(allPredator);

        for (Animal a : allPredator) {
            Eye e = new Eye(a, predObstacles);
            a.setEye(e);
        }
    }

    public void display(PApplet p, SubPlot plt) {
        for (Animal a : allAnimals)
            a.display(p, plt, imgPrey);

        for (Animal a : allPredator)
            a.display(p, plt, imgPredator);

        for (Animal a : allApex)
            a.display(p, plt, imgApex);
    }

    public int getNumAnimals() {
        return allAnimals.size();
    }

    public int getNumPredators() {
        return allPredator.size();
    }

    public int getNumApex() {
        return allApex.size();
    }


    public float getMeanMaxSpeed(List<Animal> animals) {
        float sum = 0;
        for (Animal a : animals)
            sum += a.getDNA().maxSpeed;
        return sum / animals.size();
    }

    public float getStdMaxSpeed(List<Animal> animals) {
        float mean = getMeanMaxSpeed(animals);
        float sum = 0;
        for (Animal a : animals)
            sum += Math.pow(a.getDNA().maxSpeed - mean, 2);
        return (float) Math.sqrt(sum / animals.size());
    }

    public float[] getMeanWeights(List<Animal> animals) {
        float[] sums = new float[2];
        for (Animal a : animals) {
            sums[0] += a.getBehaviors().get(0).getWeight();
        }
        sums[0] /= allAnimals.size();
        return sums;
    }

    public List<Animal> getAllPredator() {
        return allPredator;
    }

    public List<Animal> getAllAnimals() {
        return allAnimals;
    }

    public List<Animal> getAllApex() {
        return allApex;
    }

}
