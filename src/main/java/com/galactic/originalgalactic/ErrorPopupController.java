package com.galactic.originalgalactic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorPopupController {
    @FXML
    private Label messageLabel; // Label to show the error message

    @FXML
    private VBox closeButton2; // VBox acting as the "Close" button

    private Stage popupStage; // Reference to the popup stage

    // Set the error message dynamically
    public void setErrorMessage(String message) {
        messageLabel.setText(message);
    }

    // Set the stage (for closing the popup)
    public void setStage(Stage stage) {
        this.popupStage = stage;
    }

    // Close the popup when the close button is clicked
    @FXML
    private void initialize() {
        closeButton2.setOnMouseClicked(event -> {
            if (popupStage != null) {
                popupStage.close();
            }
        });
    }
}
