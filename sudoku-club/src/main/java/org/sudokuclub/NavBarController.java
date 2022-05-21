package org.sudokuclub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class NavBarController {

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
