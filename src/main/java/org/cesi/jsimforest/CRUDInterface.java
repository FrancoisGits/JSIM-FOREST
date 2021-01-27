package org.cesi.jsimforest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface CRUDInterface {

        /**
         * create - Method to insert data in db
         *
         * @param req - request to execute
         */
        default void create(String req) {
                try {
                        Statement stmt = DataBaseConnection.connection.createStatement();
                        stmt.executeUpdate(req);
                } catch (SQLException e) {
                        System.out.println(e.getMessage());
                }
        }

        /**
         * read - Method to select data in db
         *
         * @param req - request to execute
         */
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

        /**
         * update - Method to update data in db
         *
         * @param req - request to execute
         */
        default void update(String req) {
                try {
                        Statement stmt = DataBaseConnection.connection.createStatement();
                        stmt.executeUpdate(req);
                } catch (SQLException e) {
                        System.out.println(e.getMessage());
                }
        }

        /**
         * deete - Method to delete data in db
         *
         * @param req - request to execute
         */
        default void delete(String req) {
                try {
                        Statement stmt = DataBaseConnection.connection.createStatement();
                        stmt.executeUpdate(req);
                } catch (SQLException e) {
                     System.out.println(e.getMessage());
                }
        }
}
