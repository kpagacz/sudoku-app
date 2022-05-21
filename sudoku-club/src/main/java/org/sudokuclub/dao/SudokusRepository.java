package org.sudokuclub.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SudokusRepository {
  private static final String table = "Sudokus";
  private static final Logger logger = LogManager.getLogger(SudokusRepository.class);

  public static void create(String title, int[][] cells, String author, Connection conn) {
    try (Statement statement = conn.createStatement()) {
      String board = new ObjectMapper().writeValueAsString(cells);
      String query =
          String.format(
              "INSERT INTO %s (title, cells, userLogin) VALUES (%s, %s, %s)",
              table, title, board, author);
      statement.execute(query);
    } catch (SQLException | JsonProcessingException e) {
      logger.warn("Failed to create a sudoku instance in the database");
    }
  }

  public static List<Sudoku> get(Connection conn) {
    try (Statement statement = conn.createStatement()) {
      String query = String.format("SELECT * from %s", table);
      ResultSet result = statement.executeQuery(query);
      List<Sudoku> sudokus = new ArrayList<>();
      while(result.next()) {
        sudokus.add(new Sudoku(
                result.getInt(1),
                result.getString(2),
                result.getString(3),
                result.getString(4)
        ));
      }

      return sudokus;
    } catch (SQLException | JsonProcessingException e) {
      logger.warn("Failed to get sudokus");
      return null;
    }
  }

  public static Sudoku get(int id, Connection conn) {
    try (Statement statement = conn.createStatement()) {
      String query = String.format("SELECT * from %s WHERE idSudokus=%d", table, id);
      ResultSet result = statement.executeQuery(query);
      return new Sudoku(id, result.getString(2), result.getString(3), result.getString(4));
    } catch (SQLException | JsonProcessingException e) {
      logger.warn("Failed to get the requested sudoku");
      return null;
    }
  }

  public static boolean delete(int id, Connection conn) {
    try (Statement statement = conn.createStatement()) {
      String query = String.format("DELETE FROM %s WHERE idSudokus=%d", table, id);
      statement.execute(query);
      return statement.getUpdateCount() > 0;
    } catch (SQLException e) {
      logger.warn("Failed to delete entries");
      return false;
    }
  }
}
