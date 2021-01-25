package org.cesi.jsimforest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Simulation {

    private Configuration config;
    private Grid grid;
    private int step;

    /**
     * Simulation Constructor
     *
     * @param config - the config that the simulation gonna use to run
     */
    public Simulation(Configuration config) {
        this.config = config;
        this.grid = new Grid(config.getRowNumber(), config.getColumnNumber());
        this.step = 0;
    }

    /**
     * Method to process the simulation until it reach the maximum steps
     *
     */
    public void process() {
        while(step <= config.getStepsNumber()) {
            System.out.println("Matrix : ");
            System.out.println(Arrays.deepToString(this.getGrid().getMatrix()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));            System.out.println("Step : " + this.getStep());
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

                // the cell try to evolve to a new state and is stored in arrayList corresponding to her future new state
                // avoid to change the state during the matrix analyse
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
        // every cells in the differents arrayslist get their states change to the corresponding state.
        // the cell's age is reboot to 0 if the her state changed.
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

    public int getStep() { return step; }

    public Grid getGrid() { return grid; }

}
