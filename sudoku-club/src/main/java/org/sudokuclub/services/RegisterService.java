package org.sudokuclub.services;

import org.sudokuclub.dao.SudokuDbConnFactory;
import org.sudokuclub.dao.UsersRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class RegisterService {
  Connection connection;

  public RegisterService() throws SQLException, IOException {
    connection = SudokuDbConnFactory.get();
  }

  public boolean registerUser(String login, String password) {
    try {
      UsersRepository.create(login, password, connection);
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
