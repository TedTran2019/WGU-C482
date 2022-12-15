module ted.wgu482 {
    requires javafx.controls;
    requires javafx.fxml;

    opens ted.wgu482 to javafx.fxml;
    opens ted.wgu482.model to javafx.base;
    exports ted.wgu482;
    exports examples;
    opens examples to javafx.base, javafx.fxml;
}