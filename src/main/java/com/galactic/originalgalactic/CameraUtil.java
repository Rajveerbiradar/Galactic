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
        System.out.println("hello world1");
    }

    private static CameraUtil instance;
    private VideoCapture camera;
    private Stage cameraStage;
    private VideoController videoController; // Reference to controller for updating ImageView
    private boolean isInitialized = false;

    private CameraUtil() {
        camera = new VideoCapture(); // Initialize OpenCV camera
    }

    public static synchronized CameraUtil getInstance() {
        if (instance == null) {
            instance = new CameraUtil();
        }
        return instance;
    }

    public void initializeCamera() {
        if (!isInitialized) {
            new Thread(() -> {
                if (!camera.isOpened()) {
                    camera.open(0); // 0 = default camera
                }
                isInitialized = true;
                System.out.println("Camera initialized successfully");
            }).start();
        }
    }

    public void showCameraWindow() {
        if (cameraStage == null) {
            Platform.runLater(() -> {
                try {
                    cameraStage = createCameraStage();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                cameraStage.show();
                if (videoController != null) {
                    videoController.startCameraFeed(camera);
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

    private Stage createCameraStage() throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Camera-view.fxml"));
            Scene scene = new Scene(loader.load());
            videoController = loader.getController(); // Get the controller instance

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Camera Window");
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            return stage;

        } catch (IOException e) {
            ErrorPopupUtil.showError(e.getMessage()); // Show the error in the popup
            return null;
        }
    }

    public boolean isCameraInitialized() {
        return isInitialized;
    }

    public VideoCapture getCamera() {
        return camera;
    }
}
