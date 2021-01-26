package org.cesi.jsimforest;

import java.sql.*;
import java.text.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Simulation implements CRUDInterface {

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

    public Configuration getConfig() { return config; }

    public void saveSimulation(String name) {
        String nameFormat = "'" + name + "'";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String insertTime = "'" + format.format(new java.util.Date()) + "'" ;
        String req = MessageFormat.format("INSERT INTO simulation (steps, name, insert_time) VALUES ({0}, {1}, {2})", this.getStep(), nameFormat, insertTime);
        create(req);
    }

    public void saveSimulation(String name, int idGrid, int idConfig) {
        String nameFormat = "'" + name + "'";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String insertTime = "'" + format.format(new java.util.Date()) + "'" ;
        String req = MessageFormat.format("INSERT INTO simulation (steps, name, insert_time, ID_Configuration, ID_Grid) VALUES ({0}, {1}, {2}, {3} ,{4})", this.getStep(), nameFormat, insertTime, idConfig, idGrid);
        create(req);
    }

    public ResultSet readAllSimulation() {
        String req = "SELECT * FROM simulation";
        return read(req);
    }

    public ResultSet readOneSimulation(int id) {
        String req = MessageFormat.format("SELECT steps, name, insert_time FROM simulation WHERE ID = {0}", id);
        return read(req);
    }

    public void deleteOneSimulation(String name, Date insertTime) {
        String req = MessageFormat.format("DELETE FROM simulation WHERE name LIKE  '{0}' and insertTime = {1}", name, insertTime);
        delete(req);
    }

    public void updateOneSimulation(int id, String name) {
        long millis=System.currentTimeMillis();
        java.sql.Date insertTime = new java.sql.Date(millis);
        String req = MessageFormat.format("UPDATE FROM simulation SET steps = {0}, name = {1}, insert_time = {2} WHERE ID = {3}", this.getStep(), name, insertTime, id);
    }

    public void saveEntireSimulation(String name) throws SQLException {
        this.getGrid().saveGrid();
        int nextIdGrid = this.getGrid().getMaxIdGrid();
        for(int i=0;i<this.getGrid().getMatrix().length;i++){
            for(int j=0;j<this.getGrid().getMatrix()[i].length;j++) {
                this.getGrid().getMatrix()[i][j].saveCell(nextIdGrid);
            }
        }
        this.getConfig().saveConfiguration();
        int nextIdConfig = this.getConfig().getMaxIdConfiguration();
        this.saveSimulation(name, nextIdGrid, nextIdConfig);
    }

    public void deleteEntireSimulation(String name, Date insertTime) {

    }

}
