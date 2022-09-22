package aaa;

import physics.Body;
import processing.core.PVector;

public class Patrol extends Behavior{

    public Patrol(float weight) {
        super(weight);
    }

    @Override
    public PVector getDesiredVelocity(Boid me){
        Body bodyTarget = me.eye.target;
        return PVector.sub(bodyTarget.getPos(), me.getPos()); //velocidade desejada
    }
}
