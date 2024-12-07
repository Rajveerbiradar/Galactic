package com.galactic.originalgalactic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {

        System.out.println(Runtime.getRuntime().availableProcessors());


        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void loadScene(String fxmlFile) {
        try {

            // Load the new scene
            FXMLLoader loader = new FXMLLoader(Application.class.getResource(fxmlFile));
            Parent root = loader.load();
            Scene newScene = new Scene(root);

            // Save stage properties
            boolean isFullScreen = primaryStage.isFullScreen(); // Check full-screen mode
            double width = primaryStage.getWidth();            // Save width
            double height = primaryStage.getHeight();          // Save height

            // Apply the new scene without resetting stage properties
            primaryStage.setScene(newScene);
            primaryStage.setFullScreen(isFullScreen); // Restore full-screen mode
            primaryStage.setWidth(width);            // Restore width
            primaryStage.setHeight(height);          // Restore height
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch();
    }
}