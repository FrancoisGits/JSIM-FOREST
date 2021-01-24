package org.cesi.jsimforest;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Simulation sim = new Simulation(5,3,3);
        System.out.println("Matrix : ");
        System.out.println(Arrays.deepToString(sim.getGrid().getMatrix()));
        System.out.println("Liste Cells : ");
        for(int i = 0;i<sim.getGrid().getMatrix().length;i++) {
            for(int j=0;j<sim.getGrid().getMatrix()[0].length;j++) {
                System.out.println(sim.getGrid().getMatrix()[i][j].infoCell());
            }
        }
        System.out.println("Cell target : ");
        System.out.println(sim.getGrid().getMatrix()[0][0].infoCell());
        System.out.println("Voisines de Cell target : ");

        System.out.println(sim.getGrid().getNeighborsOfOneCell(0,0));
        System.out.println("Cell had : " + sim.getGrid().getNeighborsOfOneCell(0,0).size() + " neighbors");
    }
}
