package com.terkea;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terkea.controller.ClientController;
import com.terkea.model.Message;
import com.terkea.system.server.ClientThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("LandingPage"));
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(400);
        stage.setTitle("sChat - Better safe than sorry");
        stage.show();
        stage.setResizable(false);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }


    /**
     * @param fxml the name of the fxml file
     *             changes the scene from the current stage
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }


    /**
     * @param fxml the name of the fxml file
     *             changes the current stage with a new one, the size is given in the fxml file
     */
    public static void changeStage(String fxml, ActionEvent event, int height, int width, String title) throws IOException{
        Scene changeScene = new Scene(loadFXML(fxml));
        Stage changeStage = new Stage();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.hide();
        changeStage.setScene(changeScene);
        changeStage.setHeight(height);
        changeStage.setWidth(width);
        changeStage.setMinHeight(height);
        changeStage.setMinWidth(width);
        changeStage.setTitle(title);
        changeStage.show();

        changeStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}