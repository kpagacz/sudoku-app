package org.sudokuclub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public class MainController {

  public void initialize() {}

  @FXML
  public void goToSudokuDesigner() {
    try {
      Parent newWindow =
          FXMLLoader.load(
              Objects.requireNonNull(App.class.getResource("sudoku-designer-view.fxml")));
      App.setNewScene(newWindow);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void goToSudokuList() {
    try {
      Parent newWindow =
          FXMLLoader.load(Objects.requireNonNull(App.class.getResource("sudoku-list-view.fxml")));
      App.setNewScene(newWindow);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
