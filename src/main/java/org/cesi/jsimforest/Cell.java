package org.cesi.jsimforest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Cell {

    private int coordX;
    private int coordY;
    private State state;
    private int age;

    /**
     * Cell Constructor
     *
     * @param coordX Coordinate of X axis
     * @param coordY Coordinate of Y axis
     */
    public Cell(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.state = State.empty;
        this.age = 0;
    }

    /**
     * Cell Constructor - Overload
     *
     * @param coordX Coordinate of X axis
     * @param coordY Coordinate of Y axis
     * @param state State of the Cell - enum
     */
    public Cell(int coordX, int coordY, State state) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.state = state;
        this.age = 0;
    }

    /**
     * Method to get a arraylist of the neightbors cells of one cell
     *
     * @return arrayList
     */
    public ArrayList getNeighbors() {
        ArrayList<Cell> neighborsList = new ArrayList<>();
        return neighborsList;
    }

    /**
     * Method to get informations (id, coordinates, state) on the cell
     *
     * @return String
     */
    public String infoCell() {
        String infos = "Cell : " + this.toString() + " CoordX : " + this.getCoordX() + " CoordY : " + this.getCoordY() + " State : " + this.state;
        return infos;
    }

    /**
     * Method to check if a number is sup or equal to 0 - throw exception if not
     *
     * @param nbr integer
     * @return boolean
     */
    public boolean checkNbr(int nbr) {
        if(nbr >= 0) {
            return true;
        } else {
            throw new IllegalArgumentException("Nbr must be superior or equal to 0");
        }
    }

    public int getCoordX() { return coordX; }

    public void setCoordX(int coordX) {
        if (checkNbr(coordX)) {
            this.coordX = coordX;
        }
    }

    public int getCoordY() { return coordY; }

    public void setCoordY(int coordY) {
        if (checkNbr(coordY)) {
            this.coordY = coordY;
        }
    }

    public State getState() { return state; }

    public void setState(State state) {
        this.state = state;
    }

    public int getAge() { return age; }

    public void setAge(int age) {
        if (checkNbr(age)) {
            this.age = age;
        }
    }

}
