package com.galactic.originalgalactic;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nu.pattern.OpenCV;
import org.opencv.videoio.VideoCapture;

import java.io.IOException;

public class CameraUtil {

    static {
        OpenCV.loadLocally();
        System.out.println("hello world - CameraUtil initialized");
    }

    private static CameraUtil instance;
    private VideoCapture camera;
    private Stage cameraStage;
    private VideoController videoController;

    private CameraUtil() {
        camera = new VideoCapture();
    }

    public static synchronized CameraUtil getInstance() {
        if (instance == null) {
            instance = new CameraUtil();
        }
        return instance;
    }

    public void initializeCamera() {
        new Thread(() -> {
            if (!camera.isOpened()) {
                camera.open(0); // Open default camera
            }
            System.out.println("Camera initialized successfully");
        }).start();
    }

    public void showCameraWindow() {
        if (cameraStage == null) {
            Platform.runLater(() -> {
                try {
                    cameraStage = createCameraStage();
                    cameraStage.show();
                    if (videoController != null) {
                        videoController.startCameraFeed(camera); // Start camera feed
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } else if (!cameraStage.isShowing()) {
            Platform.runLater(() -> {
                cameraStage.show();
                if (videoController != null) {
                    videoController.startCameraFeed(camera);
                }
            });
        }
    }

    public void closeCameraWindow() {
        if (cameraStage != null && cameraStage.isShowing()) {
            Platform.runLater(() -> {
                cameraStage.hide();
                if (videoController != null) {
                    videoController.stopCameraFeed();
                }
            });
        }
    }

    private Stage createCameraStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Camera-view.fxml"));
        Scene scene = new Scene(loader.load());
        videoController = loader.getController(); // Link controller to manage camera actions

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Camera Window");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        return stage;
    }

    public VideoCapture getCamera() {
        return camera;
    }
}
