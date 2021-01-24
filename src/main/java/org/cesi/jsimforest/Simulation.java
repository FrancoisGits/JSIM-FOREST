package org.cesi.jsimforest;

import java.util.ArrayList;
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
            this.processOneStep();
        }
    }

    /**
     * Method to process one step during the process of the simulation
     *
     */
    public void processOneStep() {
        this.step += 1;

        for(int i=0;i<this.getGrid().getRow();i++){
            for(int j=0;j<this.getGrid().getColumn();j++) {
                this.getGrid().getNeighborsOfOneCell(i,j);

            }
        }



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
