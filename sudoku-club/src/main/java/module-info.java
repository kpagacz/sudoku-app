module com.example.sudokuclub {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.sudokuclub to javafx.fxml;
    exports com.example.sudokuclub;
}