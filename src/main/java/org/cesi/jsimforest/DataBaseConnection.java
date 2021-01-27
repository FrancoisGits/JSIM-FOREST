package org.cesi.jsimforest;

import java.sql.*;

public class DataBaseConnection {

    static Connection connection;

    /**
     * connect - Method to create a connection to the DB
     *
     */
    public static void connect() {
        try {
             connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/jsimforesttest", "root", "");
        } catch (SQLException e) {
            System.out.println("Message erreur connection DB : " + e.getMessage());
            System.out.println("SQLState : " + e.getSQLState());
        }
    }
}
