package org.sudokuclub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class RegisterController {
    @FXML
    private Label testLabel;

    public void initialize() {
        this.testLabel.setText("Register Window");
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
}
