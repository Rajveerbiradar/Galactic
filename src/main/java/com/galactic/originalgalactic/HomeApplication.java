package com.galactic.originalgalactic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        HomeController homeController = fxmlLoader.getController();
        stage.setOnCloseRequest(event -> homeController.closeConnection());

    }

    public static void main(String[] args) {
        launch();
    }
}