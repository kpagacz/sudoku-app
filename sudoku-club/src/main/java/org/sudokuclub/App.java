package org.sudokuclub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.sudokuclub.dao.SudokuDbConnFactory;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            System.out.println(SudokuDbConnFactory.get().getSchema());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        launch();
    }
}