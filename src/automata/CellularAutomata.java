package automata;

import processing.core.PApplet;

import java.util.Scanner;

public class CellularAutomata {

    //atributos
    private int nrows;
    private int ncols;
    private int nStates;
    private int radiusNeigh;
    private Cell[][] cells;
    private int[] colors;
    private int cellWidth, cellHeight;

    public CellularAutomata(PApplet p, int nrows, int ncols, int nStates, int radiusNeigh) {
        this.nrows = nrows;
        this.ncols = ncols;
        this.nStates = nStates;
        this.radiusNeigh = radiusNeigh;
        cells = new Cell[nrows][ncols];
        colors = new int[nStates];
        cellWidth = p.width / ncols;
        cellHeight = p.height / nrows;
        createCells();
        setStateColors(p);
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    private void setStateColors(PApplet p) {
        colors[0] = p.color(255);
        colors[1] = p.color(50,50,50);
    }

    public int[] getStateColors() {
        return colors;
    }

    private void createCells() {
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                cells[i][j] = new Cell(this, i, j);
            }
        }
        setMooreNeighbors();
    }

    public void initRandom() {
        Scanner input = new Scanner(System.in);
        System.out.println("Opção 1: Início random");
        System.out.println("Opção 2: Início personalizado");
        System.out.print("Codificacao: ");
        int number = input.nextInt();
        if(number == 1) {
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    cells[i][j].setState((int) (2* Math.random()));
                }
            }
        }
        if(number == 2) {
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    cells[i][j].setState(0);
                }
            }
        }
        System.out.println("Prima qualquer tecla para iniciar");
    }

    //return da posicao em pixeis de onde estamos a clicar
    public Cell pixel2Cell(int x, int y) {
        int row = y / cellHeight;
        int col = x / cellWidth;
        if (row >= nrows) {
            row = nrows - 1;
        }
        if (col >= ncols) {
            col = ncols - 1;
        }

        return cells[row][col];
    }

    //definir a vizinhança segundo o método de Moore
    private void setMooreNeighbors() {
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

    /*public void jogoVida(PApplet p) {
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                Cell cell = cellCoords(i,j);
                if (countNeighbors(cell) > 3) {
                    cell.setState(0);
                }
                if (countNeighbors(cell) < 2) {
                    cell.setState(0);
                } else {
                    cell.setState(1);
                }
                cells[i][j].display(p);
            }
        }
    }*/

}
