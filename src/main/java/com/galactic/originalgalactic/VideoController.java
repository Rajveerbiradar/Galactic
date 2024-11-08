package com.galactic.originalgalactic;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.objdetect.QRCodeDetector;
import java.io.ByteArrayInputStream;

public class VideoController {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    VideoCapture capture ;
    QRCodeDetector qrCodeDetector = new QRCodeDetector();

    @FXML
    private ImageView imageView;

    public Image matToImage(Mat frame){
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png",frame, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    public void startCamera(){
        capture.open(0);
        if(capture.isOpened()){
            Runnable frameGrabber = () ->{
                Mat frame = new Mat();
                while(capture.isOpened()){
                    capture.read(frame);

                    String qrCodeData = qrCodeDetector.detectAndDecode(frame);
                    if(!qrCodeData.isEmpty()){
                        System.out.println(qrCodeData);
                    }
                    Core.flip(frame, frame, 1);
                    Image image = matToImage(frame);
                    imageView.setImage(image);
                }
            };
            Thread cameraThread = new Thread(frameGrabber);
            cameraThread.setDaemon(true);
            cameraThread.start();
        } else {
            System.out.println("Failed to open camera.");
        }
    }

    public void detectQR(){

    }

    public void closeCamera(){
        if(capture.isOpened()){
            capture.release();
        }
    }

    @FXML
    public void initialize(){
        capture = new VideoCapture();
        startCamera();

    }

}
