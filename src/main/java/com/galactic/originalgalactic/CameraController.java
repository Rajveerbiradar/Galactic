package com.galactic.originalgalactic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.MatOfByte;
import java.io.ByteArrayInputStream;

public class CameraController extends Application {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    private VideoCapture capture;
    private ImageView imageView;

    @Override
    public void start(Stage primaryStage) {
        capture = new VideoCapture();
        imageView = new ImageView();
        startCamera();

        StackPane root = new StackPane();
        root.getChildren().add(imageView);
        Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("Camera View");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> stopCamera());
    }

    private void startCamera() {
        capture.open(0);

        if (capture.isOpened()) {
            Runnable frameGrabber = () -> {
                Mat frame = new Mat();
                while (capture.isOpened()) {
                    capture.read(frame);
                    if (!frame.empty()) {
                        Image img = matToImage(frame);
                        updateImageView(imageView, img);
                    }
                }
            };

            Thread cameraThread = new Thread(frameGrabber);
            cameraThread.setDaemon(true);
            cameraThread.start();
        } else {
            System.out.println("Failed to open camera.");
        }
    }

    private Image matToImage(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", frame, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    private void updateImageView(ImageView view, Image image) {
        view.setImage(image);
    }

    private void stopCamera() {
        if (capture.isOpened()) {
            capture.release();
        }
    }

}
