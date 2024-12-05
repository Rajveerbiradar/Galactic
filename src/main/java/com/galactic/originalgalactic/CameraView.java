package com.galactic.originalgalactic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CameraView extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Camera-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 480);
        stage.setTitle("Camera View");
        stage.setScene(scene);
        stage.show();

        //the FXMLLoader actually creates the instance of VideoController (your controller class) when it loads the FXML file. So, creating another instance of VideoController manually will not connect
        //get the controller instance directly from the FXMLLoader
        VideoTesterController vdc = fxmlLoader.getController(); //taking the controller from the fxmlLoader and saving it in its own Type VideoController

        // Set the close request to properly close the camera
        stage.setOnCloseRequest(event -> vdc.closeCamera());// using the taken controller to close the camera;
    }



    public static void main(String[] args) {
        launch();
    }

}