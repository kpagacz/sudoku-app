package org.sudokuclub.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SudokusRepository {
  private static final String table = "Sudokus";

  public static void create(String title, int[][] cells, String author, Connection conn)
      throws SQLException, JsonProcessingException {
    Statement statement = conn.createStatement();
    String board = new ObjectMapper().writeValueAsString(cells);
    String query =
        String.format(
            "INSERT INTO %s (title, cells, userLogin) VALUES ('%s', '%s', '%s')",
            table, title, board, author);
    statement.execute(query);
    statement.close();
  }

  public static List<Sudoku> get(Connection conn) throws SQLException, JsonProcessingException {
    Statement statement = conn.createStatement();
    String query = String.format("SELECT * from %s", table);
    ResultSet result = statement.executeQuery(query);
    List<Sudoku> sudokus = new ArrayList<>();
    while (result.next()) {
      sudokus.add(
          new Sudoku(
              result.getInt(1), result.getString(2), result.getString(3), result.getString(4)));
    }

    return sudokus;
  }

  public static Sudoku get(int id, Connection conn) throws SQLException, JsonProcessingException {
    Statement statement = conn.createStatement();
    String query = String.format("SELECT * from %s WHERE idSudokus=%d", table, id);
    ResultSet result = statement.executeQuery(query);
    result.next();
    return new Sudoku(id, result.getString(2), result.getString(3), result.getString(4));
  }

  public static boolean delete(int id, Connection conn) throws SQLException {
    Statement statement = conn.createStatement();
    String query = String.format("DELETE FROM %s WHERE idSudokus=%d", table, id);
    statement.execute(query);
    return statement.getUpdateCount() > 0;
  }

  public static void main(String[] args) throws SQLException, IOException {
    Connection conn = SudokuDbConnFactory.get();
    SudokusRepository.create("title", new int[][] {{1, 2, 3}, {4, 5, 6}}, "test", conn);
    //    SudokusRepository.delete(4, conn);
    var sudokus = SudokusRepository.get(conn);
    sudokus.forEach(System.out::println);
  }
}
