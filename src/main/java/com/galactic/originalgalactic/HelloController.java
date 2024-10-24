package com.galactic.originalgalactic;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class HelloController {

    @FXML
    private Label theDate;

    @FXML
    private Label theTime;

    @FXML
    private AnchorPane imageButton;

    public void onHelloButtonClick(){
        theDate.setText("Hello world");
    }

//     This method is called after the FXML elements are loaded
    @FXML
    public void initialize() {
        // Create a rectangle with rounded corners
        Rectangle rect = new Rectangle(274, 160); // Width and height of the clipping area
        rect.setArcWidth(60);  // Set rounded corner width
        rect.setArcHeight(60); // Set rounded corner height

        // Set the clipping shape to the AnchorPane

        imageButton.setClip(rect);
    }


}
