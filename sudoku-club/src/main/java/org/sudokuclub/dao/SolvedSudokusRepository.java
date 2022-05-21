package org.sudokuclub.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
