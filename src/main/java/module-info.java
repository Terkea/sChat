module com.terkea {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    //JSON
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires jackson.annotations;
    exports com.terkea.model;

    opens com.terkea to javafx.fxml;
    exports com.terkea;

    opens com.terkea.controller to javafx.fxml;
    exports com.terkea.controller;
}