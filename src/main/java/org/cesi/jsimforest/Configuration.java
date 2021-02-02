package org.cesi.jsimforest;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;


public class Configuration implements CRUDInterface{

    private double stepsPerSecond;
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

    public double getStepsPerSecond() { return stepsPerSecond; }

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
        if(rowNumber >= 2 && rowNumber <= 1000){
            this.rowNumber = rowNumber;
        } else {
            throw new IllegalArgumentException("rowNumber must be superior or equal to 2");
        }
    }

    public int getColumnNumber() { return columnNumber; }

    public void setColumnNumber(int columnNumber) {
        if(columnNumber >= 2 && columnNumber <= 1000) {
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

    public Map<String, Integer> readOneConfig(int idConfig) {
        Map<String, Integer> configInfos = new HashMap<String, Integer>();
        String req = MessageFormat.format("SELECT stepsPerSecond, stepNumber, rowNumber, columnNumber FROM configuration WHERE ID = {0}", idConfig);
        try{
            ResultSet rs = read(req);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            int stepsPerSecond = 0;
            int stepNumber = 0;
            int rowNumber = 0;
            int columnNumber = 0;
            if (rs.next()) {
                stepsPerSecond = rs.getInt("stepsPerSecond");
                stepNumber = rs.getInt("stepNumber");
                rowNumber = rs.getInt("rowNumber");
                columnNumber = rs.getInt("columnNumber");
            }
            configInfos.put("stepsPerSecond",stepsPerSecond);
            configInfos.put("stepNumber", stepNumber);
            configInfos.put("rowNumber", rowNumber);
            configInfos.put("columnNumber", columnNumber);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return configInfos;
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