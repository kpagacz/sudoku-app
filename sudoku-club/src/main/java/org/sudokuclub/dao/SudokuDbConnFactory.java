package org.sudokuclub.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SudokuDbConnFactory {
  static {
    Properties dbConfig = new Properties();
    InputStream propertiesStream =
        SudokuDbConnFactory.class.getClassLoader().getResourceAsStream("dbConfig.properties");
    try {
      dbConfig.load(propertiesStream);
      Class.forName(dbConfig.getProperty("db.driver.class"));
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static Connection get() throws IOException, SQLException {
    Properties dbConfig = new Properties();
    InputStream propertiesStream =
        SudokuDbConnFactory.class.getClassLoader().getResourceAsStream("dbConfig.properties");
    dbConfig.load(propertiesStream);
    return DriverManager.getConnection(
        dbConfig.getProperty("db.conn.url"),
        dbConfig.getProperty("db.username"),
        dbConfig.getProperty("db.password"));
  }
}
