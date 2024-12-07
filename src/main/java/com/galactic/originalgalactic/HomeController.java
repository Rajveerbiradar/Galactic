package com.galactic.originalgalactic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import nu.pattern.OpenCV;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomeController {

    CameraHelper cameraHelper;

    @FXML
    private Label theDate;

    @FXML
    private Label theTime;

    @FXML
    private AnchorPane imageButton;



    @FXML
    public void onScanIDButtonClicked() throws Exception {

        cameraHelper.startQRDetectionThread();



          // This triggers the camera view to appear
    }

    public void onHelloButtonClick() throws Exception {
        try {
            // Your logic here (e.g., scanning QR code)
            throw new Exception("Invalid QR data!"); // Simulating an error
        }catch (Exception e) {
            ErrorPopupUtil.showError(e.getMessage()); // Show the error in the popup
        }
    }

    public void goToDataControlView(){
        Application.loadScene("DataControlView.fxml");
    }

    public void goToDownloadView(){
        Application.loadScene("Download-view.fxml");
    }

    @FXML
    public void initialize() {
        // Rounding corners
        Rectangle rect = new Rectangle(274, 160); // Width and height of the clipping area
        rect.setArcWidth(60);  // Set rounded corner width
        rect.setArcHeight(60); // Set rounded corner height

        // Set the clipping shape to the AnchorPane

        imageButton.setClip(rect);

        // Day and Time
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> { // Set up Timeline for real-time updates
            LocalDateTime now = LocalDateTime.now();

            // Update Date
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE");
            theDate.setText(now.format(dateFormatter));

            // Update Time
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            theTime.setText(now.format(timeFormatter));
        }));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();

        // Camera
        cameraHelper = CameraHelper.getInstance();

    }



}
