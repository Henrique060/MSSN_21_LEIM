package aaa;

import processing.core.PVector;

public interface IBehavior {

    PVector getDesiredVelocity(Boid me);
    void setWeight(float weight);
    float getWeight();

}
