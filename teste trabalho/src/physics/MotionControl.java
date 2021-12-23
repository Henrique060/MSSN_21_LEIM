package physics;

import physics.RigidBody.ControlType;
import processing.core.PVector;

public class MotionControl {

    private RigidBody rb;
    private ControlType ct;
    private PVector vector;

    public MotionControl(ControlType ct, RigidBody rb){
        this.ct = ct;
        this.rb = rb;
        vector = new PVector();
    }

    public void setVector(PVector vector){
        this.vector = vector;
        switch(ct){
            case POSITION:
                rb.setPos(vector);
                break;
            case VELOCITY:
                rb.setVel(vector);
                break;
            case FORCE:
                rb.applyForce(vector);
                break;
        }
    }

}
