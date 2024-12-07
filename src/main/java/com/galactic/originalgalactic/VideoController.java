package com.galactic.originalgalactic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;

public class VideoController {

    static {
        OpenCV.loadLocally();
        System.out.println("hello world - VideoController initialized");
    }

    private QRCodeDetector qrCodeDetector = new QRCodeDetector();
    private volatile boolean scanning = false;
    private volatile boolean detecting = false;

    @FXML
    private ImageView imageView;

    public void initialize() {
        // Intentionally left blank; Camera is controlled by CameraUtil
    }

    public Image matToImage(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", frame, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    public void startCameraFeed(VideoCapture camera) {
        scanning = true;
        Runnable frameGrabber = () -> {
            Mat frame = new Mat();
            while (scanning && camera.isOpened()) {
                if (camera.read(frame)) {
                    Core.flip(frame, frame, 1); // Flip the frame for better display
                    Image image = matToImage(frame);
                    Platform.runLater(() -> imageView.setImage(image));
                }
            }
        };

        Thread cameraThread = new Thread(frameGrabber);
        cameraThread.setDaemon(true);
        cameraThread.start();
    }

    public void stopCameraFeed() {
        scanning = false;
    }

    public void startQRCodeDetection(VideoCapture camera) {
        if (detecting || !camera.isOpened()) return;

        detecting = true;
        Runnable qrDetection = () -> {
            Mat frame = new Mat();
            while (detecting && camera.isOpened()) {
                if (camera.read(frame)) {
                    String qrCodeData = qrCodeDetector.detectAndDecode(frame);
                    if (qrCodeData != null && !qrCodeData.isEmpty()) {
                        detecting = false; // Stop detection once QR code is found
                        System.out.println("QR Code Detected: " + qrCodeData);

                        Platform.runLater(() -> handleQRCodeData(qrCodeData));
                        break;
                    }
                }
            }
        };

        Thread detectionThread = new Thread(qrDetection);
        detectionThread.setDaemon(true);
        detectionThread.start();
    }

    private void handleQRCodeData(String qrCodeData) {
        // Process the QR code data
        System.out.println("Processing QR Code: " + qrCodeData);

        // Close the camera window and stop feed
        CameraUtil.getInstance().closeCameraWindow();
    }

    public void onScanIDButtonClicked() {


    }


}
