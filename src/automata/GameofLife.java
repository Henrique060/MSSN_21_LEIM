package automata;

import processing.core.PApplet;
import setup.IProcessingApp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameofLife implements IProcessingApp {

    private int nrows = 120;
    private int ncols = 160;
    private int nStates = 2;
    private int radiusNeigh = 1;
    private CellularAutomata ca;
    private boolean start;

    @Override
    public void setup(PApplet p) {
        ca = new CellularAutomata(p, nrows, ncols, nStates, radiusNeigh);
        this.start = false;
        ca.initRandom();
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.frameRate(10);
        ca.display(p);
        if (this.start){
            ca.regras(p);
        }

    }

    @Override
    public void mousePressed(PApplet p) {
        Cell cell = ca.pixel2Cell(p.mouseX, p.mouseY);
        if (cell.getState() == 1) {
            cell.setState(0);
        } else {
            cell.setState(1);
        }
    }
    @Override
    public void keyPressed(PApplet p) {
        this.start = !this.start;
        if (!this.start){
            System.out.println("PAUSED");
        }
        else{
            System.out.println("IN PROGRESS");
        }
    }


}
