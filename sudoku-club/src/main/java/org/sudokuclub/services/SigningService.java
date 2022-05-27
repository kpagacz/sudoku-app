package org.sudokuclub.services;

import org.sudokuclub.dao.SudokuDbConnFactory;
import org.sudokuclub.dao.User;
import org.sudokuclub.dao.UsersRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class SigningService {
  private final Connection connection;

  public SigningService() throws SQLException, IOException {
    connection = SudokuDbConnFactory.get();
  }

  public boolean signIn(String login, String password) {
    User user = null;
    try {
      user = UsersRepository.get(login, connection);
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    if (user.password().equals(password)) {
      UserSession.create(login);
      return true;
    }
    return false;
  }

  public void signOut() {
    UserSession.clear();
  }
}
