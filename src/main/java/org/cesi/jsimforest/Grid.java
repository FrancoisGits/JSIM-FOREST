package org.cesi.jsimforest;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.EnumMap;

public class Grid implements CRUDInterface {

    private int row;
    private int column;
    private Cell[][] matrix;

    /**
     * Grid Constructor
     *
     * @param row    the number of row in the grid
     * @param column the number of column if the grid
     */
    public Grid(int row, int column) {
        this.row = row;
        this.column = column;
        this.matrix = new Cell[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = new Cell(i, j, State.empty);
            }
        }
    }

    /**
     * getNeighborsOfOneCell - Method to get an arraylist of the neighbors cells of one cell
     *
     * @param coordXCellTarget - coordinate X of the cell
     * @param coordYCellTarget - coordinate Y of the cell
     * @return arrayList - neighbors of the cell
     */
    public ArrayList<Cell> getNeighborsOfOneCell(int coordXCellTarget, int coordYCellTarget) {
        ArrayList<Cell> neighborsList = new ArrayList<>();
        for (int i = coordXCellTarget - 1; i <= coordXCellTarget + 1; i++) {
            if (i >= 0 && i < getMatrix().length) {
                for (int j = coordYCellTarget - 1; j <= coordYCellTarget + 1; j++) {
                    if (j >= 0 && j < getMatrix()[i].length && (i != coordXCellTarget || j != coordYCellTarget)) {
                        neighborsList.add(matrix[i][j]);
                    }
                }
            }
        }
        return neighborsList;
    }

    /**
     * getStateofNeighborsCell - Method to get the states of the neighbors cells
     *
     * @param cells - arrayslist of neighbors cells
     * @return states - arraysList of state of neighbors cells
     */
    public List<State> getStateOfNeighborsCell(ArrayList<Cell> cells) {
        List<State> statesList = new ArrayList<>();
        for (Cell c : cells) {
            statesList.add(c.getState());
        }
        return statesList;
    }

    /**
     * getNeighborsStatesCount - Method to get the numbers of each states of the neighbors cells
     *
     * @param stateList - the states list of neighbors cells
     * @return eachStateNumber - Map of numbers of occurences of each state int the list. Association (State->number)
     */
    public EnumMap<State, Integer> getNeighborsStatesCount(List<State> stateList) {
        EnumMap<State, Integer> eachStateNumber = new EnumMap<>(State.class);
        int statesCount;
        for (State state : State.values()) {
            statesCount = Collections.frequency(stateList, state);
            eachStateNumber.put(state, statesCount);
        }
        return eachStateNumber;
    }

    /**
     * checkNumber - Check if a number is sup or equal to 2 - throw Exception if not
     *
     * @param nbr integer
     * @return boolean
     */
    public boolean checkNbr(int nbr) {
        if (nbr <= 2) {
            return true;
        } else {
            throw new IllegalArgumentException("Nbr must be superior or equal to 2");
        }
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        if (checkNbr(row)) {
            this.row = row;
        }
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        if (checkNbr(column)) {
            this.column = column;
        }
    }

    /**
     *  saveGrid - Method to store grid data in DB
     *
     */
    public void saveGrid() {
        String req = MessageFormat.format("INSERT INTO grid (height, width) VALUES ({0}, {1})", this.getRow(), this.getColumn());
        create(req);
    }

    public ResultSet readOneGrid(int id) {
        String req = MessageFormat.format("SELECT * FROM grid WHERE ID = {0}", id);
        return read(req);
    }

    public void deleteOneGrid(int id) {
        String req = MessageFormat.format("DELETE FROM grid WHERE ID = {0}", id);
        delete(req);
    }

    /**
     * getMaxIdGrid - method to get the maximum ID of the grid table in DB
     *
     * @return int - max id
     * @throws SQLException
     */
    public int getMaxIdGrid() throws SQLException {
        String req = "SELECT MAX(id) as id FROM grid";
        ResultSet res = read(req);
        int maxIdGrid = 0;
        while (res.next()) {
            maxIdGrid = res.getInt("id");
        }
        return maxIdGrid;
    }

    public EnumMap<State, Double> getCellsDensity() {
        EnumMap<State, Double> cellsDensity = new EnumMap<>(State.class);
        double matrixSize = this.getColumn() * this.getRow();
        int youngTreeCount = 0;
        int bushCount = 0;
        int treeCount = 0;
        int burningCount = 0;
        int ashesCount = 0;
        int infectedCount = 0;

        for (int i = 0; i < this.getMatrix().length; i++) {
            for (int j = 0; j < this.getMatrix()[i].length; j++) {
                State stateCell = this.getMatrix()[i][j].getState();
                    switch (stateCell) {
                        case youngTree:
                            youngTreeCount += 1;
                            break;
                        case bush:
                            bushCount += 1;
                            break;
                        case tree:
                            treeCount += 1;
                            break;
                        case ashes:
                            ashesCount += 1;
                            break;
                        case burning:
                            burningCount += 1;
                            break;
                        case infected:
                            infectedCount += 1;
                            break;
                    }
            }
        }
        double youngTreeDensity = youngTreeCount / matrixSize;
        double bushDensity = bushCount / matrixSize;
        double treeDensity = treeCount / matrixSize;
        double ashesDensity = ashesCount / matrixSize;
        double burningDensity = burningCount / matrixSize;
        double infectedDensity = infectedCount / matrixSize;

        cellsDensity.put(State.youngTree, youngTreeDensity);
        cellsDensity.put(State.bush, bushDensity);
        cellsDensity.put(State.tree, treeDensity);
        cellsDensity.put(State.ashes, ashesDensity);
        cellsDensity.put(State.burning, burningDensity);
        cellsDensity.put(State.infected, infectedDensity);

        return cellsDensity;
    }
}
