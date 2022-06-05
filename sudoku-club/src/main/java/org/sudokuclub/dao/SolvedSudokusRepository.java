package org.sudokuclub.dao;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SolvedSudokusRepository {
  private static final String table = "SolvedSudokus";

  public static void create(int sudokuId, String userLogin, Connection conn) throws SQLException {
    Statement statement = conn.createStatement();
    String query =
        String.format(
            "INSERT INTO %s (sudokuId, userLogin) VALUES (%d, '%s')", table, sudokuId, userLogin);
    statement.execute(query);
  }

  public static SolvedSudoku get(int id, Connection conn) throws SQLException {
    Statement statement = conn.createStatement();
    String query = String.format("SELECT * from %s WHERE idSolvedSudokus=%d", table, id);
    ResultSet result = statement.executeQuery(query);
    result.next();
    return new SolvedSudoku(id, result.getInt(2), result.getString(3));
  }

  public static List<SolvedSudoku> get(String player, Connection conn) throws SQLException {
    Statement statement = conn.createStatement();
    String query = String.format("SELECT * from %s WHERE userLogin='%s'", table, player);
    return extractSolvedSudokusFromQuery(statement, query);
  }

  public static int get(String player, int id, Connection conn) throws SQLException {
    Statement statement = conn.createStatement();
    String query = String.format("SELECT COUNT(*) FROM %s WHERE sudokuId=%d AND userLogin='%s'", table, id, player);
    ResultSet result = statement.executeQuery(query);
    result.next();
    return result.getInt(1);
  }

  private static List<SolvedSudoku> extractSolvedSudokusFromQuery(Statement statement, String query)
      throws SQLException {
    ResultSet result = statement.executeQuery(query);
    List<SolvedSudoku> sudokus = new ArrayList<>();
    while (result.next()) {
      sudokus.add(new SolvedSudoku(result.getInt(1), result.getInt(2), result.getString(3)));
    }
    return sudokus;
  }

  public static boolean delete(int id, Connection conn) throws SQLException {
    Statement statement = conn.createStatement();
    String query = String.format("DELETE FROM %s WHERE idSolvedSudokus=%d", table, id);
    statement.execute(query);
    return statement.getUpdateCount() > 0;
  }

  public static void main(String[] args) throws SQLException, IOException {
    Connection conn = SudokuDbConnFactory.get();
    System.out.println(SolvedSudokusRepository.get(2, conn));
  }
}
