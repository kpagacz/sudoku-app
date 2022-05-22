module org.sudokuclub {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires com.fasterxml.jackson.databind;

    opens org.sudokuclub to javafx.fxml;
    exports org.sudokuclub;
}