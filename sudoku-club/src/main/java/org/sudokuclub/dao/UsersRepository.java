package org.sudokuclub.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class UsersRepository {
  private static final String usersTable = "Users";
  private static final Logger logger = LogManager.getLogger(UsersRepository.class);

  public static void create(String login, String password, Connection conn) {
    try {
      Statement statement = conn.createStatement();
      String query =
          String.format(
              "INSERT INTO %s (login, password) VALUES (%s, %s)", usersTable, login, password);
      statement.execute(query);
    } catch (SQLException e) {
      logger.warn("Failed to create a user instance in the database");
    }
  }

  public static User get(String login, Connection conn) {
    try {
      Statement statement = conn.createStatement();
      String query = String.format("SELECT * from %s WHERE login='%s'", usersTable, login);
      ResultSet result = statement.executeQuery(query);
      String password = result.getString(2);
      return new User(login, password);
    } catch (SQLException e) {
      logger.warn("Failed to get the requested user");
      return null;
    }
  }

  public static boolean delete(String login, Connection conn) {
    try {
      Statement statement = conn.createStatement();
      String query = String.format("DELETE FROM %s WHERE login='%s'", usersTable, login);
      statement.execute(query);
      return statement.getUpdateCount() > 0;
    } catch (SQLException e) {
      logger.warn("Failed to delete entries");
      return false;
    }
  }
}
