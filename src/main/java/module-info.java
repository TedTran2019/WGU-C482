module ted.wgu482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ted.wgu482 to javafx.fxml;
    exports ted.wgu482;
}