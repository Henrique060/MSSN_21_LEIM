package aaa;

import physics.Body;
import processing.core.PVector;

public class Flee extends Behavior{

    public Flee(float weight) {
        super(weight);
    }

    public PVector getDesiredVelocity(Boid me){
        Body bodyTarget = me.eye.target;
        PVector vd = PVector.sub(bodyTarget.getPos(), me.getPos()); //velocidade desejada
        return vd.mult(-1);
    }

}
