package org.sudokuclub;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.sudokuclub.dao.SudokuDbConnFactory;
import org.sudokuclub.dao.User;
import org.sudokuclub.dao.UsersRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {
  @FXML private Button signInButton;
  @FXML private TextField loginField;
  @FXML private PasswordField passwordField;
  @FXML private Label signInMessage;

  private static class SignInTask extends Task<Boolean> {
    private final String login;
    private final String password;
    private final Connection conn;

    public SignInTask(String login, String password) throws SQLException, IOException {
      this.login = login;
      this.password = password;
      this.conn = SudokuDbConnFactory.get();
    }

    @Override
    protected Boolean call() throws Exception {
      User user = UsersRepository.get(login, conn);
      return user.password().equals(password);
    }

    @Override
    protected void cancelled() {
      super.cancelled();
      try {
        conn.close();
      } catch (SQLException ignored) {
      }
    }

    @Override
    protected void succeeded() {
      super.succeeded();
      try {
        conn.close();
      } catch (SQLException ignored) {
      }
    }
  }

  private void handleSignInSuccess(WorkerStateEvent event) {
    if ((boolean) event.getSource().getValue()) {
      System.out.println("Sign In Success");
      signInMessage.setText("Success");
    } else {
      System.out.println("Sign In Failure");
      signInMessage.setText("Password incorrect");
    }
  }

  private void handleSignInFailure(WorkerStateEvent event) {
    System.out.println("Sign In Failure");
    signInMessage.setText("Failure");
    signInButton.setDisable(false);
  }

  private void handleSignIn(ActionEvent event) {
    signInButton.setDisable(true);
    try {
      SignInTask signInTask = new SignInTask(loginField.getText(), passwordField.getText());
      signInTask.setOnSucceeded(this::handleSignInSuccess);
      signInTask.setOnFailed(this::handleSignInFailure);
      Thread thread = new Thread(signInTask);
      thread.setDaemon(true);
      thread.start();
    } catch (Exception e) {
      signInMessage.setText("Failure");
      signInButton.setDisable(false);
    }
  }

  @FXML
  public void initialize() {
    signInButton.setOnAction(this::handleSignIn);
  }

  @FXML
  public void goToRegisterView() {
    try {
      Parent newWindow =
          FXMLLoader.load(Objects.requireNonNull(App.class.getResource("register-view.fxml")));
      App.setNewScene(newWindow);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
