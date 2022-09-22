package automata;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameofLife implements IProcessingApp {

    private int nrows = 15;
    private int ncols = 20;
    private int nStates = 4;
    private int radiusNeigh = 2;
    private CellularAutomata ca;
    private boolean start;
    private SubPlot plt;
    private double[] window = {0, 10, 0, 10};
    private float[] viewport = {0, 3f, 0.3f, 0.5f, 0.6f};

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        ca = new CellularAutomata(p, plt, nrows, ncols, nStates, radiusNeigh);
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
        /*if (cell.getState() == 1) {
            cell.setState(0);
        } else {
            cell.setState(1);
        }*/
        Cell[] neigh = cell.getNeighbors();
        for(int i = 0; i < neigh.length; i++){
            neigh[i].setState(nStates-1);
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

    @Override
    public void mouseDragged(PApplet p) {

    }

    @Override
    public void mouseReleased(PApplet p) {

    }


}
