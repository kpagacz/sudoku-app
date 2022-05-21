package org.sudokuclub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Parent root;
    public static Stage mainStage;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        mainStage.setTitle("Doki Doki Sudoku Club");
        Parent newWindow = FXMLLoader.load(App.class.getResource("main-view.fxml"));
        setNewScene(newWindow);
    }

    public static void setNewScene(Parent newWindow) throws IOException{
        root = newWindow;
        scene = new Scene(root, 800, 550);
        String css = App.class.getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}