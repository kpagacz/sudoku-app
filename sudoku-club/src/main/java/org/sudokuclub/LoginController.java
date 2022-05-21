package org.sudokuclub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class LoginController {
    @FXML
    private Label testLabel;

    public void initialize() {
        this.testLabel.setText("Login Window");
    }

    @FXML
    public void goToRegisterView() {
        try {
            Parent newWindow = FXMLLoader.load(App.class.getResource("register-view.fxml"));
            App.setNewScene(newWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
