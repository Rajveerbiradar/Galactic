package com.galactic.originalgalactic;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Tester extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Example with a try-catch block
        try {
            // Simulating an error (replace this with your code)
            int result = 10 / 0; // This will cause an exception
        } catch (Exception e) {
            // Show error popup
            showErrorPopup("Oops! Something went wrong: " + e.getMessage());
        }

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Galactic Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to show the error popup
    private void showErrorPopup(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Detected");
        alert.setHeaderText("An Error Occurred!");
        alert.setContentText(errorMessage);
        alert.showAndWait(); // Shows the alert and waits for the user to close it
    }

    public static void main(String[] args) {
        launch(args);
    }
}
