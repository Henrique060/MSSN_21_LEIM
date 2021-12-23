package physics;

import processing.core.PApplet;
import processing.core.PVector;

public class RigidBody {

    public enum ControlType{
        POSITION,
        VELOCITY,
        FORCE
    }

    private PVector pos;
    private PVector vel;
    private PVector acc;
    private float mass;

    public RigidBody(float mass){
        pos = new PVector();
        vel = new PVector();
        acc = new PVector();
        this.mass = mass;
    }

    public void setPos(PVector pos){
        this.pos = pos;
    }

    public void setVel(PVector vel){
        this.vel = vel;
    }

    public void applyForce(PVector force){
        this.acc = PVector.div(force, mass);
    }


    /**
     * passa o ultimo tempo que ocorreu desde a ultima frame; atualiza vel e acc
     *
     *
     * s(t+dt) = s(t) + dt * vel
     * NOTA: o vetor posicao no instante t + dt sera igual à soma entre a sua posicao na frame anterior
     * e a multiplicacao entre o intervalo de tempo
     * com a velocidade
     *
     *
     * vel(t+dt) = vel(t) + dt * acc
     */

    public void move(float dt, ControlType ct){
        switch(ct){
            case POSITION:
                break;
            case VELOCITY:
                pos.add(PVector.mult(vel, dt));
                break;
            case FORCE:
                pos.add(PVector.mult(vel, dt));
                vel.add(PVector.mult(acc, dt));
                break;
        }
    }


    public void display(PApplet p){
        p.circle(pos.x, pos.y, 30);
    }
}
