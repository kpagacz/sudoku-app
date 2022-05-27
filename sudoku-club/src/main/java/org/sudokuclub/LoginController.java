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
import javafx.scene.layout.Pane;
import org.sudokuclub.services.SigningService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {
  @FXML private Pane navBar;
  @FXML private Button signInButton;
  @FXML private TextField loginField;
  @FXML private PasswordField passwordField;
  @FXML private Label signInMessage;

  private static class SignInTask extends Task<Boolean> {
    private final SigningService signingService;
    private final String login;
    private final String password;

    public SignInTask(String login, String password) throws SQLException, IOException {
      this.login = login;
      this.password = password;
      signingService = new SigningService();
    }

    @Override
    protected Boolean call() {
      return signingService.signIn(login, password);
    }
  }

  private void handleSignInSuccess(WorkerStateEvent event) {
    if ((boolean) event.getSource().getValue()) {
      signInMessage.setText("Success");
      goToMainView();
    } else {
      signInMessage.setText("Password incorrect");
    }
    signInButton.setDisable(false);
  }

  private void handleSignInFailure(WorkerStateEvent event) {
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

  private void goToMainView() {
    try {
      Parent newWindow =
          FXMLLoader.load(Objects.requireNonNull(App.class.getResource("main-view.fxml")));
      App.setNewScene(newWindow);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
