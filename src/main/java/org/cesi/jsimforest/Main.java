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
        sim.getGrid().getMatrix()[0][2].setState(State.tree);
        sim.getGrid().getMatrix()[2][2].setState(State.tree);
        sim.process();
    }
}
