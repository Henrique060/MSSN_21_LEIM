package aaa;

import physics.Body;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Pursuit extends Behavior{
    protected List<Body> alltrackingbodies = new ArrayList<Body>();
    public Pursuit(float weight) {
        super(weight);
    }

    @Override
    public PVector getDesiredVelocity(Boid me) {
        Body bodyTarget = me.eye.target;
        PVector d = bodyTarget.getVel().mult(me.dna.deltaTPursuit);
        PVector target = PVector.add(bodyTarget.getPos(), d);
        return PVector.sub(target, me.getPos());
    }

}
