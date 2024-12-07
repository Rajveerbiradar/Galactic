package com.galactic.originalgalactic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CameraView extends javafx.application.Application {

    @Override
    public void start(Stage cameraStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Camera-view.fxml"));
        Parent root = loader.load();
        CameraController controller = loader.getController();

        cameraStage = new Stage();
        controller.setStage(cameraStage);


        cameraStage.setScene(new Scene(root));
        cameraStage.setTitle("Camera Window");

        Stage finalCameraStage = cameraStage;
        finalCameraStage.show();
        cameraStage.setOnCloseRequest(e -> finalCameraStage.hide());
    }



    public static void main(String[] args) {
        launch();
    }

}