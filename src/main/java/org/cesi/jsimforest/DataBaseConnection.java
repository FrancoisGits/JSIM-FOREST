package org.cesi.jsimforest;

import java.sql.*;

public class DataBaseConnection {

    public static boolean connect() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/jsimforest", "root", "");
            return true;
        } catch (SQLException e) {
            System.out.println("Message erreur connection DB : " + e.getMessage());
            System.out.println("SQLState : " + e.getSQLState());
            return false;
        }
    }
}
