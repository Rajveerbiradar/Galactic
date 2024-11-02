package com.galactic.originalgalactic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // Custom font
/*
        URL fontUrl = getClass().getResource("Montserrat/static/Montserrat-SemiBold.ttf");
        if (fontUrl == null) {
            System.err.println("Font file not found!");
            return;
        }
        Font.loadFont(fontUrl.toExternalForm(), 16);
*/

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}