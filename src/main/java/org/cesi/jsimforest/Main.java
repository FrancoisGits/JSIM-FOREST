package org.cesi.jsimforest;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args ) throws SQLException {
        DataBaseConnection.connect();
        System.out.println( "Hello World!" );
        Configuration config = new Configuration();
        Simulation sim = new Simulation(config);
        System.out.println(sim.getGrid().getMaxIdGrid());
        sim.getGrid().getMatrix()[1][1].setState(State.youngTree);
        sim.getGrid().getMatrix()[0][1].setState(State.tree);
        sim.getGrid().getMatrix()[1][0].setState(State.tree);
        sim.getGrid().getMatrix()[1][2].setState(State.tree);
        sim.getGrid().getMatrix()[2][1].setState(State.tree);
        sim.saveEntireSimulation("nique bien ta mere");
        sim.process();
    }
}
