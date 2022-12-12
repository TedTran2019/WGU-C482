module ted.wgu482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ted.wgu482 to javafx.fxml;
    exports ted.wgu482;
    exports ted.wgu482.controller;
    opens ted.wgu482.controller to javafx.fxml;
}