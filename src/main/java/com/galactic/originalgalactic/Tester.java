package com.galactic.originalgalactic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.videoio.VideoCapture;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Tester {



    VideoCapture videoCapture;
    QRCodeDetector qrCodeDetector;
    Stage theStage;
    VideoController videoController;
    Mat frame;
    boolean isStageCreated;
    boolean isStageActive ;
    FXMLLoader loader;


    @FXML
    ImageView imageView;

    Tester(){
        frame = new Mat();
        qrCodeDetector = new QRCodeDetector();
        videoCapture = new VideoCapture();
        this.isStageCreated = false;
        this.isStageActive = false;
    }

    public void startCamera(){
        videoCapture.open(0);
        System.out.println("camera started");
    }

    public Image matToImage(Mat frame){
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png",frame, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    public void createCameraStage() throws IOException {

        try {
            // Load the camera FXML layout
            loader = new FXMLLoader(getClass().getResource("Camera-view.fxml"));
            Scene scene = new Scene(loader.load());

            theStage = new Stage();
            theStage.setScene(scene);
            theStage.setTitle("Camera Window");
            theStage.setResizable(false);
            theStage.initModality(Modality.APPLICATION_MODAL);
            theStage.initStyle(StageStyle.UNDECORATED);
            isStageCreated = true;


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void stowStage() throws Exception {
        if(!isStageActive){
            if (theStage == null || loader == null) {
                createCameraStage(); // Initialize the popup if not already done
            }

            theStage.show();
            isStageActive = true;
            System.out.println("stage is shown");
        }
    }

    public void hideStage(){
        if(isStageActive){
            theStage.hide();
            isStageActive = false;
        }
    }

    public void StartDetectAndDecode(Mat frame){
        if(videoCapture.isOpened()){
            Runnable decoding = () -> {
                while(isStageActive){
                    videoCapture.read(frame);
                    String qrCodeData = qrCodeDetector.detectAndDecode(frame);
                    if(!qrCodeData.isEmpty()){
                        System.out.println(qrCodeData);
                        hideStage();
                    }
                }
            };

            Thread thread = new Thread(decoding);
            thread.start();
        }
    }

    public void updateImage(Mat frame){

        if(isStageCreated){
            if (videoCapture.isOpened()){
                Runnable frameGrabber = ()->{

                    while(videoCapture.isOpened()){
                        videoCapture.read(frame);
                        Core.flip(frame, frame, 1);
                        Image image = matToImage(frame);
                        if(frame != null){
                            Platform.runLater(() -> imageView.setImage(image));
                        }
                    }
                };

                Thread thread = new Thread(frameGrabber);
                thread.start();
            }
        }
    }



    public void onScanIdClick() throws Exception {
        startCamera();
        stowStage();
        updateImage(frame);
        StartDetectAndDecode(frame);
    }


}
