module org.sudokuclub {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.sudokuclub to javafx.fxml;
    exports org.sudokuclub;
}