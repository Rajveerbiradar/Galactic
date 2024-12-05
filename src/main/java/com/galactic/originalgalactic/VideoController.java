package com.galactic.originalgalactic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VideoController {

    static {
        OpenCV.loadLocally();
        System.out.println("hello world");
    }

    private final VideoCapture camera = CameraUtil.getInstance().getCamera();
    private final CameraUtil cameraUtil = CameraUtil.getInstance();
    private volatile boolean scanning = false;
    QRCodeDetector qrCodeDetector = new QRCodeDetector();


    @FXML
    private ImageView imageView;

    private ExecutorService executorService;
    private volatile boolean running = false;

    public void startCameraFeed(VideoCapture camera) {
        if (running) return;

        running = true;
        executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            Mat frame = new Mat();
            while (running && camera.isOpened()) {
                if (camera.read(frame)) {
                    Image image = matToImage(frame);
                    Platform.runLater(() -> imageView.setImage(image));
                }
            }
        });
    }

    public void stopCameraFeed() {
        running = false;
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    public Image matToImage(Mat frame){
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png",frame, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }


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
