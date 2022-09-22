package ecosystem;

import physics.Body;
import processing.core.PApplet;

import java.util.List;

public interface IAnimal {
    Animal reproduce(boolean mutate);
    void eat(Terrain terrain);
    void energy_consumption(float dt, Terrain terrain);
    boolean die();
    void updateTargetPrey(PApplet p, List<Body> obstacles, List<Animal> allPrey);
    void inWater(Terrain terrain);
}
