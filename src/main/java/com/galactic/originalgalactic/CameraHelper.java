package com.galactic.originalgalactic;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;

public class CameraHelper {
    private final VideoCapture videoCapture;
    private ImageView cameraView;
    QRCodeDetector qrCodeDetector;
    Stage cameraStage;
    CameraController cameraController;

    private volatile boolean running = false;
    private volatile Mat sharedFrame = new Mat();

    private CameraHelper() {
        qrCodeDetector = new QRCodeDetector();
        this.videoCapture = new VideoCapture();
        this.cameraStage = createCamera();
        startCamera();

    }

    public static CameraHelper getInstance(){
        CameraHelper cameraHelper = new CameraHelper();
        return cameraHelper;
    }






    public Image matToImage(Mat frame){
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png",frame, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    static {
        OpenCV.loadLocally();
        System.out.println("hello world - .dll file (locally) load");
    }

    public void startCamera() {
        if (!videoCapture.open(0)) {
            System.out.println("Error: Camera could not be opened!");
            return;
        }
        startCameraThread();

    }


    public Stage createCamera(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Camera-view.fxml"));
            Parent root = loader.load();

            Stage primaryStage = new Stage();
            cameraController = loader.getController();
            cameraView = cameraController.getCameraView();

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Camera Window");

            primaryStage.setOnCloseRequest(e -> stopCamera());
            System.out.println("Camera Stage Created !");
            primaryStage.show();

            return primaryStage;

        }catch (Exception e) {
            //ErrorPopupUtil.showError(e.getMessage()); // Show the error in the popup
            System.out.println(e);
            return null;
        }
    }


    private void startCameraThread() {
        System.out.println("start Camera image Updating Thread");
        Thread thread = new Thread(() -> {
            Mat frame = new Mat();
            while (true) {
                if (videoCapture.read(frame)) {
                    synchronized (this) {
                        Core.flip(frame, frame, 1);
                        sharedFrame = frame.clone();
                    }

                    if (cameraView.isVisible()) {

                        Image image = matToImage(frame);
                        Platform.runLater(() -> cameraView.setImage(image));
                    }sharedFrame.release();
                }
            }

        });

        thread.start();

        System.out.println("End Camera image Updating Thread");
    }

    public void startQRDetectionThread() {
        System.out.println("start QR Code detecting image Updating Thread");
        running = true;
        cameraStage.show();
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
                            cameraStage.hide();
                        });
                    }
                }
                frameCopy.release();
            }
        }).start();

        System.out.println("QR Decoded image Updating Thread");
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
