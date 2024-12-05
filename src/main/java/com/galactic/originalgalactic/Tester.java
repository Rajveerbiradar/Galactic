package com.galactic.originalgalactic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.videoio.VideoCapture;

public class Tester {
    @FXML
    private Button scanIDButton;

    private final VideoCapture camera = CameraUtil.getInstance().getCamera();
    private final CameraUtil cameraUtil = CameraUtil.getInstance();
    private volatile boolean scanning = false;
    QRCodeDetector qrCodeDetector = new QRCodeDetector();

    @FXML
    public void onScanIDButtonClicked() {
        if (!scanning) {
            scanning = true;
            cameraUtil.showCameraWindow(); // Show the camera window
            startQRCodeDetection();       // Start detecting QR codes
        }
    }

    private void startQRCodeDetection() {
        new Thread(() -> {
            Mat frame = new Mat();
            while (scanning && camera.isOpened()) {
                if (camera.read(frame)) {
                    String qrCodeData = qrCodeDetector.detectAndDecode(frame);
                    if (qrCodeData != null && !qrCodeData.isEmpty()) {
                        scanning = false; // Stop scanning once QR is detected
                        System.out.println("QR Code Detected: " + qrCodeData);

                        Platform.runLater(() -> {
                            cameraUtil.closeCameraWindow(); // Hide the camera
                            handleQRCodeData(qrCodeData);   // Process the QR code
                        });
                    }
                }
            }
        }).start();
    }



    private void handleQRCodeData(String qrCodeData) {
        // Process the QR code data
        System.out.println("Processing QR Code: " + qrCodeData);

        // Add logic to update the database, show an alert, etc.
    }
}
