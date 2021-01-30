package org.cesi.jsimforest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;


public class Configuration implements CRUDInterface{

    private int stepsPerSecond;
    private int stepsNumber;
    private int rowNumber;
    private int columnNumber;

    /**
     * Configuration Constructor - Default parameters for testing
     *
     */
    public Configuration() {
        this.stepsPerSecond = 1;
        this.stepsNumber = 20;
        this.rowNumber = 3;
        this.columnNumber = 3;
    }

    /**
     * Configuration Constructor - Overload
     *
     * @param stepsPerSecond - number of steps per second for the simulation
     * @param stepsNumber - number of maximal steps to run in the simulation
     * @param rowNumber - number of rows for the simulation grid
     * @param columnNumber - number of columns for the simulation grid
     */
    public Configuration(int stepsPerSecond, int stepsNumber, int rowNumber, int columnNumber) {
        this.stepsPerSecond = stepsPerSecond;
        this.stepsNumber = stepsNumber;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public int getStepsPerSecond() { return stepsPerSecond; }

    public void setStepsPerSecond(int stepsPerSecond) {
        if(stepsPerSecond >= 1) {
            this.stepsPerSecond = stepsPerSecond;
        } else {
            throw new IllegalArgumentException("stepsPerSeconds must be superior or equal to 1");
        }
    }

    public int getStepsNumber() { return stepsNumber; }

    public void setStepsNumber(int stepsNumber) {
        if(stepsNumber >= 1) {
            this.stepsNumber = stepsNumber;
        } else {
            throw new IllegalArgumentException("stepsNumber must be superior or equal to 1");
        }
    }

    public int getRowNumber() { return rowNumber; }

    public void setRowNumber(int rowNumber) {
        if(rowNumber >= 2) {
            this.rowNumber = rowNumber;
        } else {
            throw new IllegalArgumentException("rowNumber must be superior or equal to 2");
        }
    }

    public int getColumnNumber() { return columnNumber; }

    public void setColumnNumber(int columnNumber) {
        if(columnNumber >= 2) {
            this.columnNumber = columnNumber;
        } else {
            throw new IllegalArgumentException("columnNumber must be superior or equal to 2");
        }
    }

    /**
     * saveConfiguration - Method to store Configuration Data in DB
     *
     */
    public void saveConfiguration() {
        String req = MessageFormat.format("INSERT INTO configuration (stepsPerSecond, stepNumber, rowNumber, columnNumber) VALUES ({0}, {1}, {2}, {3})", this.getStepsPerSecond(), this.getStepsNumber(), this.getRowNumber(), this.getColumnNumber());
        create(req);
    }

    public ResultSet readAllConfiguration() {
        String req = "SELECT * FROM configuration";
        return read(req);
    }

    public ResultSet readOneConfiguration(int id) {
        String req = MessageFormat.format("SELECT stepsPerSecond, stepNumber, rowNumber, columnNumber FROM configuration WHERE ID = {0}", id);
        return read(req);
    }

    public void deleteOneConfiguration(int id) {
        String req = MessageFormat.format("DELETE FROM configuration WHERE ID = {0}", id);
        delete(req);
    }

    /**
     * getMaxIdConfiguration - Method to get the maximum ID of the configuration table in DB
     *
     * @return int - maximum ID
     * @throws SQLException
     */
    public int getMaxIdConfiguration() throws SQLException {
        String req = "SELECT MAX(id) as id FROM configuration";
        ResultSet res = read(req);
        int maxIdGrid = 0;
        while (res.next()) {
            maxIdGrid = res.getInt("id");
        }
        return maxIdGrid;
    }


}