package automata;

import processing.core.PApplet;
import processing.core.PVector;
import tools.CustomRandomGenerator;
import tools.SubPlot;

import java.util.Scanner;

public class CellularAutomata {
    //atributos
    protected int nrows;
    protected int ncols;
    protected int nStates;
    private final int radiusNeigh;
    protected Cell[][] cells;
    private int[] colors;
    protected float cellWidth, cellHeight; //pixels
    protected float xmin, ymin;
    private final SubPlot plt;

    public CellularAutomata(PApplet p, SubPlot plt, int nrows, int ncols, int nStates, int radiusNeigh) {
        this.nrows = nrows;
        this.ncols = ncols;
        this.nStates = nStates;
        this.radiusNeigh = radiusNeigh;
        cells = new Cell[nrows][ncols];
        colors = new int[nStates];
        float[] bb = plt.getBoundingBox();
        xmin = bb[0];
        ymin = bb[1];
        cellWidth = bb[2] / ncols;
        cellHeight = bb[3] / nrows;
        this.plt = plt;
        createCells();
        setStateColors(p);
    }


    public void setStateColors(PApplet p) {
        for(int i = 0; i < nStates; i++){
            colors[i] = p.color(p.random(255), p.random(255), p.random(255));
        }
    }

    public void setStateColors(int[] colors){
        this.colors = colors;
    }

    public int[] getStateColors() {
        return colors;
    }

    protected void createCells() {
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                cells[i][j] = new Cell(this, i, j);
            }
        }
        setMooreNeighbors();
    }

    public void initRandom() {
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    cells[i][j].setState(0);
                }
            }
    }

    public void initRandomCustom(double[] pmf){
        CustomRandomGenerator crg = new CustomRandomGenerator(pmf);
        for (int i = 0; i < nrows; i++){
            for (int j = 0; j < ncols; j++) {
                cells[i][j].setState(crg.getRandomClass());
            }
        }
    }

    public PVector getCenterCell(int row, int col){
        float x = (col + 0.5f) * cellWidth;
        float y = (row + 0.5f) * cellHeight;
        double[] w = plt.getWorldCoord(x,y);
        return new PVector((float)w[0], (float)w[1]);
    }

    public Cell world2Cell(double x, double y){
        float[] xy = plt.getPixelCoord(x,y);
        return pixel2Cell(xy[0], xy[1]);
    }

    //return da posicao em pixeis de onde estamos a clicar
    public Cell pixel2Cell(float x, float y) {
        int row = (int)((y - ymin)/ cellHeight);
        int col = (int)((x - xmin)/ cellWidth);
        if (row >= nrows) {
            row = nrows - 1;
        }
        if (col >= ncols) {
            col = ncols - 1;
        }
        if(row < 0) row = 0;
        if(col < 0) col = 0;
        return cells[row][col];
    }

    //definir a vizinhança segundo o método de Moore
    protected void setMooreNeighbors() {
        int NN = (int) Math.pow(2 * radiusNeigh + 1, 2); //Number of Neighbors
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                Cell[] neigh = new Cell[NN];
                int n = 0;
                for (int ii = -radiusNeigh; ii <= radiusNeigh; ii++) {
                    int row = (i + ii + nrows) % nrows;
                    for (int jj = -radiusNeigh; jj <= radiusNeigh; jj++) {
                        int col = (j + jj + ncols) % ncols;
                        neigh[n++] = cells[row][col];
                    }
                }
                cells[i][j].setNeighbors(neigh);
            }
        }
    }


    public int[][] activeCells(PApplet p){
        int [][]nActive = new int[nrows][ncols];
        for (int row = 0; row < nrows; ++row){
            for (int col = 0; col < ncols; col++){
                Cell[] neighbors = cells[row][col].getNeighbors();
                int count = 0;

                for (Cell neighbor : neighbors) {
                    count += (neighbor.getState() == 1) ? 1 : 0;
                }
                nActive[row][col] = (cells[row][col].getState() != 1) ? (count) : (count - 1);
            }
        }
        return nActive;
    }

    public void regras(PApplet p) {

        int[][] livingCells = activeCells(p);
        for (int row = 0; row < nrows; ++row){
            for (int col = 0; col < ncols; col++){
                if (livingCells[row][col] == 3 || (cells[row][col].getState() >= 1 && livingCells[row][col] == 2)) {
                    cells[row][col].setState(1);
                }
                else {
                    cells[row][col].setState(0);
                }
            }
        }
    }

    public void display(PApplet p) {
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                cells[i][j].display(p);
            }
        }

    }

    public float getCellWidth(){
        return cellWidth;
    }

    public float getCellHeight(){
        return cellHeight;
    }


}
