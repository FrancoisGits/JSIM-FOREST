package org.cesi.jsimforest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface CRUDInterface {

        default void create(String req) {
                try {
                        Statement stmt = DataBaseConnection.connection.createStatement();
                        stmt.executeUpdate(req);
                } catch (SQLException e) {
                        System.out.println(e.getMessage());
                }
        }

        default ResultSet read(String req) {
                ResultSet res = null;
                try {
                        Statement stmt = DataBaseConnection.connection.createStatement();
                        res = stmt.executeQuery(req);
                } catch (SQLException e){
                        System.out.println(e.getMessage());
                }
                return res;
        }

        default void update(String req) {
                try {
                        Statement stmt = DataBaseConnection.connection.createStatement();
                        stmt.executeUpdate(req);
                } catch (SQLException e) {
                        System.out.println(e.getMessage());
                }
        }

        default void delete(String req) {
                try {
                        Statement stmt = DataBaseConnection.connection.createStatement();
                        stmt.executeUpdate(req);
                } catch (SQLException e) {
                     System.out.println(e.getMessage());
                }
        }
}
