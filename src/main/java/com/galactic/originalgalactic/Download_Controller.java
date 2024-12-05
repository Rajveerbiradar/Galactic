package com.galactic.originalgalactic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Download_Controller {
    @FXML
    private VBox classView;

    public void goToDataControlView(){
        Application.loadScene("DataControlView.fxml");
    }

    public void goToHomeView(){
        Application.loadScene("Home-View.fxml");
    }

    @FXML
    private Label theDate;

    @FXML
    private Label theTime;

    public void initialize() {

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
    }



}
