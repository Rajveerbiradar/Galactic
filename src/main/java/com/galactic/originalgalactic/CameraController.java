package com.galactic.originalgalactic;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CameraController {

    private Stage stage;

    @FXML
    private ImageView cameraView;

    private CameraHelper cameraHelper;

    public ImageView getCameraView(){
        return cameraView;
    }


    public void setStage(Stage  stage){
        this.stage = stage;
        System.out.println("Stage has been stored in controller: " + stage);
    }


    public Stage getStage() {
        return stage;
    }

    public void showCamera() {
        Stage stage = (Stage) cameraView.getScene().getWindow();
        stage.show();
    }



    @FXML
    public void initialize() {

    }

}

