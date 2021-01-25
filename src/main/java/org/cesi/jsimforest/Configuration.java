package org.cesi.jsimforest;

public class Configuration {

    private int stepsPerSecond;
    private int stepsNumber;
    private int rowNumber;
    private int columnNumber;

    /**
     * Configuration Constructor - Default parameter for test
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
            this.columnNumber = rowNumber;
        } else {
            throw new IllegalArgumentException("columnNumber must be superior or equal to 2");
        }
    }
}