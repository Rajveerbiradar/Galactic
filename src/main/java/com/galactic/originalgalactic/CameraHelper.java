package com.galactic.originalgalactic;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;

public class CameraHelper {
    private final VideoCapture videoCapture;
    private final ImageView cameraView;
    QRCodeDetector qrCodeDetector;

    private volatile boolean running = false;
    private volatile Mat sharedFrame = new Mat();

    public CameraHelper(ImageView cameraView) {
        qrCodeDetector = new QRCodeDetector();
        this.videoCapture = new VideoCapture();
        this.cameraView = cameraView;
    }

    public Image matToImage(Mat frame){
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png",frame, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    public void startCamera() {
        if (!videoCapture.open(0)) {
            System.out.println("Error: Camera could not be opened!");
            return;
        }

        running = true;
        startCameraThread();
        startQRDetectionThread();
    }

    private void startCameraThread() {
        new Thread(() -> {
            Mat frame = new Mat();
            while (running) {
                if (videoCapture.read(frame)) {
                    synchronized (this) {
                        Core.flip(frame, frame, 1);
                        sharedFrame = frame.clone();
                    }

                    // Update the UI only if the camera view is visible
                    if (cameraView.isVisible()) {

                        Image image = matToImage(frame);
                        Platform.runLater(() -> cameraView.setImage(image));
                    }
                }
            }
            frame.release();
        }).start();
    }

    private void startQRDetectionThread() {
        new Thread(() -> {
            while (running) {
                Mat frameCopy;
                synchronized (this) {
                    frameCopy = sharedFrame.clone();
                }

                if (!frameCopy.empty()) {
                    String qrData = detectQRCode(frameCopy);
                    if (!qrData.isEmpty()) {
                        handleQRCodeData(qrData);
                        Platform.runLater(() -> {
                            running = false;



                        });
                    }
                }
                frameCopy.release();
            }
        }).start();
    }

    public void stopCamera() {
        running = false;
        if (videoCapture.isOpened()) {
            videoCapture.release(); // Stop the camera connection completely
        }


    }

    private String detectQRCode(Mat frame) {

        String qrCodeData = qrCodeDetector.detectAndDecode(frame);
        return qrCodeData; // Replace with actual QR code content if detected
    }

    private void handleQRCodeData(String qrCodeData) {
        System.out.println("Processing QR Code: " + qrCodeData);
    }
}
