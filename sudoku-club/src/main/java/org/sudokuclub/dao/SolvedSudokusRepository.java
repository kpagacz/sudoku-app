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

  public static Sudoku get(int id, Connection conn) {
    return null;
  }

  public static boolean delete(int id, Connection conn) {
    return false;
  }
}
