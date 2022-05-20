package org.sudokuclub.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SudokuDbConnFactory {
  public static Connection get() {
    Properties dbConfig = new Properties();
    InputStream propertiesStream =
        SudokuDbConnFactory.class.getClassLoader().getResourceAsStream("org/sudokuclub/dbConfig.properties");
    try {
      dbConfig.load(propertiesStream);
      Class.forName(dbConfig.getProperty("db.driver.class"));
      return DriverManager.getConnection(
          dbConfig.getProperty("db.conn.url"),
          dbConfig.getProperty("db.username"),
          dbConfig.getProperty("db.password"));
    } catch (ClassNotFoundException | IOException | SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
