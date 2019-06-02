module com.terkea {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    opens com.terkea to javafx.fxml;
    exports com.terkea;

    opens com.terkea.controller to javafx.fxml;
    exports com.terkea.controller;
}