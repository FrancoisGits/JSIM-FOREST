package org.cesi.jsimforest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Grid {

    private int row;
    private int column;
    private Cell[][] matrix;

    /**
     * Grid Constructor
     *
     * @param row the number of row in the grid
     * @param column the number of column if the grid
     */
    public Grid(int row, int column) {
        this.row = row;
        this.column = column;
        this.matrix = new Cell[row][column];
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++) {
                matrix[i][j] = new Cell(i,j,State.empty);
            }
        }
    }

    /**
     * Check if a number is sup or equal to 2 - throw Exception if not
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


    public Cell[][] getMatrix() { return matrix; }

    public int getRow() { return row; }

    public void setRow(int row) {
        if (checkNbr(row)) {
            this.row = row;
        }
    }

    public int getColumn() { return column; }

    public void setColumn(int column) {
        if (checkNbr(column)) {
            this.column = column;
        }
    }
}

// A garder pour le moment si on doit repasser sur des arraysLists
    /*
    private ArrayList<ArrayList<Cell>> matrix;

    public Grid(int width, int height) {
        setHeight(height);
        setWidth(width);

        //ArrayList<ArrayList<Cell>> matrix = new ArrayList<>();
        for(int i=0;i<height;i++){
            matrix.add(new ArrayList<Cell>());
            ArrayList<Cell> line = matrix.get(i);
            for(int j=0;j<width;j++){
                line.add(new Cell(i, j));
            }
        }
        System.out.println("Matrix : ");
        System.out.println(matrix);
        System.out.println(matrix.get(1).get(0));
        System.out.println(matrix.get(1).get(0).getCoordX());
        System.out.println(matrix.get(1).get(0).getCoordY());

    }

    public ArrayList<ArrayList<Cell>> getMatrix() {
        return matrix;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
*/