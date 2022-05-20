package org.sudokuclub.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SudokusRepository {
    private static final String table = "Sudokus";
    private static final Logger logger = LogManager.getLogger(SudokusRepository.class);

    public static void create(String login, String password, Connection conn) {
        try {
            Statement statement = conn.createStatement();
            String query =
                    String.format(
                            "INSERT INTO %s (login, password) VALUES (%s, %s)", table, login, password);
            statement.execute(query);
        } catch (SQLException e) {
            logger.warn("Failed to create a sudoku instance in the database");
        }
    }

    public static Sudoku get(int id, Connection conn) {
        try {
            Statement statement = conn.createStatement();
            String query = String.format("SELECT * from %s WHERE login='%s'", table, );
            ResultSet result = statement.executeQuery(query);
            String password = result.getString(2);
            return new Sudoku();
        } catch (SQLException e) {
            logger.warn("Failed to get the requested sudoku");
            return null;
        }
    }

    public static boolean delete(String login, Connection conn) {
        try {
            Statement statement = conn.createStatement();
            String query = String.format("DELETE FROM %s WHERE login='%s'", table, login);
            statement.execute(query);
            return statement.getUpdateCount() > 0;
        } catch (SQLException e) {
            logger.warn("Failed to delete entries");
            return false;
        }
    }

}


