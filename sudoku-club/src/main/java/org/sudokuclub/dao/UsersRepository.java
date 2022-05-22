package org.sudokuclub.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersRepository {
  private static final String usersTable = "Users";

  public static void create(String login, String password, Connection conn) throws SQLException {
    Statement statement = conn.createStatement();
    String query =
        String.format(
            "INSERT INTO %s (login, password) VALUES ('%s', '%s')", usersTable, login, password);
    statement.execute(query);
    statement.close();
  }

  public static User get(String login, Connection conn) throws SQLException {
    Statement statement = conn.createStatement();
    String query = String.format("SELECT * from %s WHERE login='%s'", usersTable, login);
    ResultSet result = statement.executeQuery(query);
    result.next();
    return new User(login, result.getString(2));
  }

  public static boolean delete(String login, Connection conn) throws SQLException {
    Statement statement = conn.createStatement();
    String query = String.format("DELETE FROM %s WHERE login='%s'", usersTable, login);
    statement.execute(query);
    return statement.getUpdateCount() > 0;
  }

  public static void main(String[] args) throws SQLException, IOException {
    Connection conn = SudokuDbConnFactory.get();
    System.out.println(UsersRepository.get("test", conn));
    conn.close();
  }
}
