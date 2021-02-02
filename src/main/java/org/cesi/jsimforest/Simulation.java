package org.cesi.jsimforest;

import javafx.application.Platform;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Simulation implements CRUDInterface {

    private int step;
    private Grid grid;
    private Configuration config;

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
     */
    public void process() throws InterruptedException, IOException {
        int interval =  (int) (1000 / this.getConfig().getStepsPerSecond());
        new Thread(() -> {
        while (step < config.getStepsNumber() && ClientController.instanceAlive) {
//            System.out.println("Matrix : ");
//            System.out.println(Arrays.deepToString(getGrid().getMatrix()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//            System.out.println("Step : " + this.getStep());
//            System.out.println("Liste Cells : ");
//            for (int i = 0; i < getGrid().getMatrix().length; i++) {
//                for (int j = 0; j < getGrid().getMatrix()[0].length; j++) {
//                    System.out.println(getGrid().getMatrix()[i][j].infoCell());
//                }
//            }
//            int x = 1;
//            int y = 2;
//            System.out.println("Cell target : ");
//            System.out.println(getGrid().getMatrix()[x][y].infoCell());
//            System.out.println("Voisines de Cell target : ");
//            System.out.println(getGrid().getNeighborsOfOneCell(x, y));
//            System.out.println("Cell had : " + getGrid().getNeighborsOfOneCell(x, y).size() + " neighbors");
//            System.out.println("voisines states : " + getGrid().getStateOfNeighborsCell(getGrid().getNeighborsOfOneCell(x, y)));
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                try {
                    processOneStep();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        }).start();
    }

    /**
     * Method to process one step during the process of the simulation
     */
    public void processOneStep() throws IOException {
        if (step < config.getStepsNumber()) {
            ArrayList<Cell> evolveInYoungTree = new ArrayList<>();
            ArrayList<Cell> evolveInBush = new ArrayList<>();
            ArrayList<Cell> evolveInTree = new ArrayList<>();

            for (int i = 0; i < getGrid().getMatrix().length; i++) {
                for (int j = 0; j < getGrid().getMatrix().length; j++) {

                    // the cell age is up by one
                    getGrid().getMatrix()[i][j].setAge(getGrid().getMatrix()[i][j].getAge() + 1);

                    // the cell try to evolve to a new state and is stored in arrayList corresponding to her future new state
                    // avoid to change the state during the matrix analyse
                    State newState = getGrid().getMatrix()[i][j].isEvolving(getGrid().getNeighborsStatesCount(getGrid().getStateOfNeighborsCell(getGrid().getNeighborsOfOneCell(i, j))));
                    if (newState != getGrid().getMatrix()[i][j].getState()) {
                        switch (newState) {
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
            for (Cell cell : evolveInYoungTree) {
                cell.setState(State.youngTree);
                cell.setAge(0);
            }

            for (Cell cell : evolveInBush) {
                cell.setState(State.bush);
                cell.setAge(0);
            }
            for (Cell cell : evolveInTree) {
                cell.setState(State.tree);
                cell.setAge(0);
            }
            System.out.println(this.getGrid().getCellsDensity());
            Client.updateGrid(this.getConfig().getRowNumber(), this.getConfig().getColumnNumber());
            Client.updateStep();
            Client.updateDensity();
            step += 1;
        }
        if (step == config.getStepsNumber()){
            ClientController.popUpFinSim();
        }
    }

    public int getStep() {
        return step;
    }

    public  Grid getGrid() {
        return grid;
    }

    public Configuration getConfig() {
        return config;
    }

    /**
     * saveSimulation - Method to store Simuation Data in DB - useful for testing
     *
     * @param name - name of the simulation to store it in DB
     */
    public void saveSimulation(String name) throws IOException, InterruptedException {
        String nameFormat = "'" + name + "'";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String insertTime = "'" + format.format(new java.util.Date()) + "'";
        String req = MessageFormat.format("INSERT INTO simulation (steps, name, insert_time) VALUES ({0}, {1}, {2})", this.getStep(), nameFormat, insertTime);
        create(req);
    }

    /**
     * saveSimulation - Overload
     * Method to store simulation data in DB, giving her a name and the grid/configuration she's linked to
     *
     * @param name     - name given to the simulation
     * @param idGrid   - grid id that is linked to the simulation
     * @param idConfig - config id that is linked to the simulation
     */
    public void saveSimulation(String name, int idGrid, int idConfig) {
        String nameFormat = "'" + name + "'";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String insertTime = "'" + format.format(new java.util.Date()) + "'";
        String req = MessageFormat.format("INSERT INTO simulation (steps, name, insert_time, ID_Configuration, ID_Grid) VALUES ({0}, {1}, {2}, {3} ,{4})", this.getStep(), nameFormat, insertTime, idConfig, idGrid);
        create(req);
        System.out.println("Oui");
    }

    public ArrayList<String> readAllSimulation() throws SQLException {
        String req = "SELECT name, insert_time FROM simulation";
        ArrayList<String> simulationsArrayList = new ArrayList<>();
        ResultSet rs  = read(req);
        ResultSetMetaData rsmd  = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            String str = "";
            for (int i = 1; i <= columnsNumber; i++) {
                String columnValue = rs.getString(i);
                str += columnValue;
                str += ":";
            }
            str = str.substring(0, str.indexOf('.'));
            simulationsArrayList.add(str);
        }
        return simulationsArrayList;
    }

    public Map<String, Integer> readOneSimulation(String simulationName, String simulationInsertTime) {
        simulationName = "'" + simulationName + "'";
        simulationInsertTime = "'" + simulationInsertTime + "'";
        Map<String, Integer> simulationInfos = new HashMap<String, Integer>();
        String req = MessageFormat.format("SELECT ID, steps, ID_Configuration, ID_Grid FROM simulation WHERE name LIKE {0} AND insert_Time = {1}", simulationName, simulationInsertTime);
        try{
            ResultSet rs = read(req);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            int idSim = 0;
            int steps = 0;
            int idConfig = 0;
            int idGrid = 0;
            if (rs.next()) {
                idSim = rs.getInt("ID");
                steps = rs.getInt("steps");
                idConfig = rs.getInt("ID_Configuration");
                idGrid = rs.getInt("ID_Grid");
            }
            simulationInfos.put("ID",idSim);
            simulationInfos.put("steps", steps);
            simulationInfos.put("ID_Configuration", idConfig);
            simulationInfos.put("ID_Grid", idGrid);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return simulationInfos;
    }



    public void deleteOneSimulation(String name, Date insertTime) {
        String req = MessageFormat.format("DELETE FROM simulation WHERE name LIKE  '{0}' and insertTime = {1}", name, insertTime);
        delete(req);
    }

    public void updateOneSimulation(int id, String name) {
        long millis = System.currentTimeMillis();
        java.sql.Date insertTime = new java.sql.Date(millis);
        String req = MessageFormat.format("UPDATE FROM simulation SET steps = {0}, name = {1}, insert_time = {2} WHERE ID = {3}", this.getStep(), name, insertTime, id);
    }

    /**
     * saveEntireSimulation - method to store the entire simulation (config + sim + grid + cells) to store a simulation in DB during the simulation process
     *
     * @param name - name given to the simulation
     * @throws SQLException
     */
    public void saveEntireSimulation(String name) throws SQLException, IOException, InterruptedException {
        try {
            getGrid().saveGrid();
            int nextIdGrid = getGrid().getMaxIdGrid();
            for (int i = 0; i < getGrid().getMatrix().length; i++) {
                for (int j = 0; j < getGrid().getMatrix()[i].length; j++) {
                    getGrid().getMatrix()[i][j].saveCell(nextIdGrid);
                }
            }
            this.getConfig().saveConfiguration();
            int nextIdConfig = this.getConfig().getMaxIdConfiguration();
            this.saveSimulation(name, nextIdGrid, nextIdConfig);
            ClientController.popUpValider();
        } catch (Exception e) {
            e.printStackTrace();
            ClientController.popUpErreur();
        }
    }

    public void deleteEntireSimulation(String name, Date insertTime) {

    }

}
