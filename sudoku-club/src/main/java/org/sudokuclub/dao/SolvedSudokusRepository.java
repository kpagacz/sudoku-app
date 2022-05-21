package org.sudokuclub.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SolvedSudokusRepository {
  private static final String table = "SolvedSudokus";
  private static final Logger logger = LogManager.getLogger(SolvedSudokusRepository.class);

  public static void create(int sudokuId, String userLogin, Connection conn) {
    try (Statement statement = conn.createStatement()) {
      String query =
          String.format(
              "INSERT INTO %s (sudokuId, userLogin) VALUES (%d, %s)", table, sudokuId, userLogin);
      statement.execute(query);
    } catch (SQLException e) {
      logger.warn("Failed to create a SolvedSudoku entry");
    }
  }

  public static SolvedSudoku get(int id, Connection conn) {
    try (Statement statement = conn.createStatement()) {
      String query = String.format("SELECT * from %s WHERE idSolvedSudokus=%d", table, id);
      ResultSet result = statement.executeQuery(query);
      return new SolvedSudoku(id, result.getInt(2), result.getString(3));
    } catch (SQLException e) {
      logger.warn("Failed to get the requested solved sudoku");
      return null;
    }
  }

  public static boolean delete(int id, Connection conn) {
    try (Statement statement = conn.createStatement()) {
      String query = String.format("DELETE FROM %s WHERE idSolvedSudokus=%d", table, id);
      statement.execute(query);
      return statement.getUpdateCount() > 0;
    } catch (SQLException e) {
      logger.warn("Failed to delete entries");
      return false;
    }
  }
}
