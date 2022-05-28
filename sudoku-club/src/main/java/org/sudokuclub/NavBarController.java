package org.sudokuclub;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sudokuclub.services.UserSession;

import java.io.IOException;

public class NavBarController {

  private final Logger logger = LogManager.getLogger();
  @FXML private Button signInButton;
  @FXML private Button signOutButton;
  @FXML private Label loginLabel;

  public void initialize() {
    updateButtonsVisibility();
    signOutButton.setOnAction(this::singOutHandler);
  }

  private void singOutHandler(ActionEvent event) {
    UserSession.getLogin().setValue("");
    updateButtonsVisibility();
  }

  private void updateButtonsVisibility() {
    String login = UserSession.getLogin().getValue();
    if ("".equals(login)) {
      signOutButton.setVisible(false);
      signInButton.setVisible(true);
      loginLabel.setText("");
    } else {
      signOutButton.setVisible(true);
      signInButton.setVisible(false);
      loginLabel.setText(String.format("Signed in as: %s", login));
    }
  }

  @FXML
  public void goToMainView() {
    try {
      Parent newWindow = FXMLLoader.load(App.class.getResource("main-view.fxml"));
      App.setNewScene(newWindow);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void goToLoginView() {
    try {
      Parent newWindow = FXMLLoader.load(App.class.getResource("login-view.fxml"));
      App.setNewScene(newWindow);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void goToSudokuList() {
    try {
      Parent newWindow = FXMLLoader.load(App.class.getResource("sudoku-list-view.fxml"));
      App.setNewScene(newWindow);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
