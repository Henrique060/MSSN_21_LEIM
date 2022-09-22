
package automata;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TestCellularAutomata implements IProcessingApp {

    private int nrows = 15;
    private int ncols = 20;
    private int nStates = 4;
    private int radiusNeigh = 2;
    private CellularAutomata ca;
    private SubPlot plt;
    private boolean startGame = false;
    private double[] window = {0,10,0,10};
    private float[] viewport = {0.3f, 0.3f, 0.5f, 0.6f};

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        ca = new CellularAutomata(p, plt, nrows, ncols, nStates, radiusNeigh);
        ca.initRandom();
        ca.display(p);
    }

    @Override
    public void draw(PApplet p, float dt) {
        ca.display(p);
    }


/*@Override
    public void mousePressed(PApplet p) {
        Cell cell = ca.pixel2Cell(p.mouseX, p.mouseY);
        if (cell.getState() == 1) {
            cell.setState(0);
        } else {
            cell.setState(1);
        }
    }*/


   /* @Override
    public void mousePressed(PApplet p) {
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                Cell cell = ca.cellCoords(i,j);
                if (ca.countNeighbors(cell) > 3 || ca.countNeighbors(cell) < 2) {
                    cell.setState(0);
                } else {
                    cell.setState(1);
                }
            }
        }

    }*/

    @Override
    public void mousePressed(PApplet p) {
        Cell cell = ca.pixel2Cell(p.mouseX, p.mouseY);

        Cell[] neigh = cell.getNeighbors();
        for(int i = 0; i < neigh.length; i++){
            neigh[i].setState(nStates-1);
        }

    }

    @Override
    public void keyPressed(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }

    @Override
    public void mouseReleased(PApplet p) {

    }


}

