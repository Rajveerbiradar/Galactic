package com.galactic.originalgalactic;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CameraController {

    private Stage stage;

    @FXML
    private ImageView cameraView;

    private CameraHelper cameraHelper;


    @FXML
    private void onCloseCamera() {
        // Just hide the camera window instead of releasing the camera
        Stage stage = (Stage) cameraView.getScene().getWindow();
        stage.hide();
    }

    public void setStage(Stage  stage){
        this.stage = stage;
        System.out.println("Stage has been stored in controller: " + stage);
    }

    @FXML
    public void initialize() {
        cameraHelper = new CameraHelper(cameraView);
        cameraHelper.startCamera();
    }

    public Stage getStage() {
        return stage;
    }

    public void showCamera() {
        Stage stage = (Stage) cameraView.getScene().getWindow();
        stage.show();
    }

    public void stopCamera() {
        cameraHelper.stopCamera(); // Stop the camera completely
    }
}

