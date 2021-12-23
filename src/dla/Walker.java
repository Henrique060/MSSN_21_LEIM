package dla;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Walker {

    public enum State{
        STOPPED,
        WANDER
    }
    private PVector pos;
    private State state;
    private int colour;
    public static int radius = 4;
    public static int num_wanders = 0;
    public static int num_stopped = 0;

    public Walker(PApplet p){
        //pos = new PVector(p.random(p.width), p.random((p.height)));
        pos = new PVector(p.width/2, p.height/2);
        PVector r = PVector.random2D();
        r.mult(p.width/2);
        pos.add(r);
        setState(p, State.WANDER);
    }

    public Walker(PApplet p, PVector pos){
        this.pos = pos;
        setState(p, State.STOPPED);
    }

    public State getState(){
        return state;
    }

    public void setState(PApplet p, State state){
        this.state = state;
        if (state == State.STOPPED){
            if (num_stopped < 200) {
                colour = p.color(0,255,169);
            } else if (num_stopped > 200 && num_stopped <= 350) {
                colour = p.color(0,255,128);
            } else if (num_stopped > 350 && num_stopped <= 500){
                colour = p.color(0, 255, 60);
            } else if (num_stopped > 500 && num_stopped <= 650){
                colour = p.color(137, 255,0);
            } else if (num_stopped > 650 && num_stopped <= 900){
                colour = p.color(222,255,0);
            } else if (num_stopped > 900 && num_stopped <= 1200){
                colour = p.color(255,171,0);
            }
            else {
                colour = p.color(255, 68, 0);
            }
            num_stopped++;
        }
        else{
            colour = p.color(50);
            num_wanders++;
        }
    }

    public void updateState(PApplet p, List<Walker> walkers){
        if (state == State.STOPPED){
            return;
        }

        for(Walker w : walkers){
                if (w.state == State.STOPPED){
                    float dist = PVector.dist(pos, w.pos);
                    if (dist < 2 * radius){
                        setState(p, State.STOPPED);
                        num_wanders--;
                        break;
                    }
                }
            }
        }


    public void wander(PApplet p){
        PVector step = PVector.random2D();
        pos.add(step);
        pos.lerp(new PVector(p.width/2, p.height/2), 0.0002f);
        pos.x = PApplet.constrain(pos.x, 0, p.width);
        pos.y = PApplet.constrain(pos.y, 0, p.height);
    }

    public void display(PApplet p){
        p.fill(colour);
        p.circle(pos.x, pos.y, 2 * radius);
    }

}
