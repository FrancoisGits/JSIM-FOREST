package org.cesi.jsimforest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Simulation {

    private int numberSteps;
    private int nextGridRowNbr;
    private int nextGridColumnNbr;
    private Grid grid;
    private int step;

    /**
     * Simulation Constructor
     *
     * @param numberSteps How many steps the simulation is going to process
     * @param nextGridRowNbr Number of row in the next grid built by the simulation
     * @param nextGridColumnNbr Number of column in the next grid built by the simulation
     */
    public Simulation(int numberSteps, int nextGridRowNbr, int nextGridColumnNbr) {
        this.grid = new Grid(nextGridRowNbr, nextGridColumnNbr);
        this.numberSteps = numberSteps;
        this.step = 0;
    }

    /**
     * Method to process the simulation until it reach the maximum steps
     *
     */
    public void process() {
        while(step <= this.numberSteps) {
            System.out.println("Matrix : ");
            System.out.println(Arrays.deepToString(this.getGrid().getMatrix()));
            System.out.println("Step : " + this.getStep());
            System.out.println("Liste Cells : ");
            for(int i = 0;i<this.getGrid().getMatrix().length;i++) {
                for(int j=0;j<this.getGrid().getMatrix()[0].length;j++) {
                    System.out.println(this.getGrid().getMatrix()[i][j].infoCell());
                }
            }
            int x = 1;
            int y = 2;
            System.out.println("Cell target : ");
            System.out.println(this.getGrid().getMatrix()[x][y].infoCell());
            System.out.println("Voisines de Cell target : ");
            System.out.println(this.getGrid().getNeighborsOfOneCell(x,y));
            System.out.println("Cell had : " + this.getGrid().getNeighborsOfOneCell(x,y).size() + " neighbors");
            System.out.println("voisines states : " + this.getGrid().getStateOfNeighborsCell(this.getGrid().getNeighborsOfOneCell(x,y)));
            this.processOneStep();
        }
    }

    /**
     * Method to process one step during the process of the simulation
     *
     */
    public void processOneStep() {
        ArrayList<Cell> evolveInYoungTree = new ArrayList<>();
        ArrayList<Cell> evolveInBush = new ArrayList<>();
        ArrayList<Cell> evolveInTree = new ArrayList<>();

        for(int i=0;i<getGrid().getMatrix().length;i++){
            for(int j=0;j<getGrid().getMatrix().length;j++) {

                // the cell age is up by one
                this.getGrid().getMatrix()[i][j].setAge(getGrid().getMatrix()[i][j].getAge() + 1);

                // the cell try to evolve
                State newState = getGrid().getMatrix()[i][j].isEvolving(getGrid().getNeighborsStatesCount(getGrid().getStateOfNeighborsCell(getGrid().getNeighborsOfOneCell(i,j))));
                if(newState != getGrid().getMatrix()[i][j].getState()) {
                    switch(newState) {
                        case youngTree:
                            evolveInYoungTree.add(getGrid().getMatrix()[i][j]);
                            break;
                        case bush:
                            evolveInBush.add(getGrid().getMatrix()[i][j]);
                            break;
                        case tree:
                            evolveInTree.add(getGrid().getMatrix()[i][j]);
                            break;
                    }
                }
            }
        }
        for(Cell cell: evolveInYoungTree) {
            cell.setState(State.youngTree);
            cell.setAge(0);
        }

        for(Cell cell: evolveInBush) {
            cell.setState(State.bush);
            cell.setAge(0);
        }
        for(Cell cell: evolveInTree) {
            cell.setState(State.tree);
            cell.setAge(0);
        }
        this.step += 1;
    }

    /**
     * Method to check if a number is sup or equal to 2 - throw exception if not
     *
     * @param nbr integer
     * @return boolean
     */
    public boolean checkNbr(int nbr) {
        if(nbr <= 2) {
            return true;
        } else {
            throw new IllegalArgumentException("Nbr must be superior or equal to 2");
        }
    }

    public int getStep() { return step; }

    public void setStep(int step) {
        if(step >= 1) {
            this.numberSteps = step;
        }
    }

    public Grid getGrid() { return grid; }

    public int getNumberSteps() { return numberSteps; }

    public void setNumberSteps(int numberSteps) {
        if(numberSteps > 0) {
            this.numberSteps = numberSteps;
        } else {
            throw new IllegalArgumentException("Number of step must be superior to 0");
        }
    }

    public void setNextGridRowNbr(int nextGridRowNbr) {
        if(checkNbr(nextGridRowNbr)) {
            this.nextGridRowNbr = nextGridRowNbr;
        }
    }

    public void setNextGridColumnNbr(int nextGridColumnNbr) {
        if(checkNbr(nextGridColumnNbr)) {
            this.nextGridColumnNbr = nextGridColumnNbr;
        }
    }
}
