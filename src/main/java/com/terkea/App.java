package com.terkea;

import com.terkea.system.Utilities;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.show();
    }


    /**
     * @param fxml the name of the fxml file
     *             changes the scene from the current stage
     */
    static void setRoot(String fxml) throws IOException {
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
        changeStage.setTitle(title);
        changeStage.show();

    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}