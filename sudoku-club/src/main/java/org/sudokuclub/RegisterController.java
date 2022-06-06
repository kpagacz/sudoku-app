package org.sudokuclub;

import javafx.beans.value.ChangeListener;
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
import org.sudokuclub.services.RegisterService;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {
  @FXML private Button registerButton;
  @FXML private Pane navBar;
  @FXML private Label resultLabel;
  @FXML private TextField loginField;
  @FXML private PasswordField passwordField;
  @FXML private PasswordField confirmPasswordField;

  private static class RegisterTask extends Task<Boolean> {
    private final RegisterService registerService;
    private final String login;
    private final String password;

    public RegisterTask(String login, String password) {
      this.login = login;
      this.password = password;
      try {
        registerService = new RegisterService();
      } catch (SQLException | IOException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    protected Boolean call() throws Exception {
      return registerService.registerUser(login, password);
    }
  }

  public void initialize() {
    loginField.focusedProperty().addListener(validateLogin);
  }

  @FXML
  private void handleRegisterButton(ActionEvent event) {
    if (!validateInputs()) return;
    registerButton.setDisable(true);

    Task<Boolean> registerTask = new RegisterTask(loginField.getText(), passwordField.getText());
    registerTask.setOnSucceeded(this::handleRegisterSuccess);
    registerTask.setOnFailed(this::handleRegisterFailure);
    Thread thread = new Thread(registerTask);
    thread.setDaemon(true);
    thread.start();
  }

  private void handleRegisterSuccess(WorkerStateEvent event) {
    resultLabel.setText("Account registered!");
    loginField.clear();
    passwordField.clear();
    confirmPasswordField.clear();
    registerButton.setDisable(false);
  }

  private void handleRegisterFailure(WorkerStateEvent event) {
    resultLabel.setText("Failed to register the account");
    passwordField.clear();
    confirmPasswordField.clear();
    registerButton.setDisable(false);
  }

  private boolean validateInputs() {
    if (!passwordField.getText().equals(confirmPasswordField.getText())) {
      resultLabel.setText("Passwords do not match!");
      return false;
    }

    return true;
  }

  private final ChangeListener<Boolean> validateLogin = (((observableValue, aBoolean, t1) -> {
    if (!t1) {
      String login = loginField.getText();
      if (login.length() < 4 || login.length() > 20) {
        resultLabel.setText("Login must have between 4 and 20 characters");
        registerButton.setDisable(true);
        return;
      }
      resultLabel.setText("");
      registerButton.setDisable(false);
    }
  }));

  @FXML
  public void goToLoginView() {
    try {
      Parent newWindow = FXMLLoader.load(App.class.getResource("login-view.fxml"));
      App.setNewScene(newWindow);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
