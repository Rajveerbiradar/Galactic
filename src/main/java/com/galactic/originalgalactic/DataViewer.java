package com.galactic.originalgalactic;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DataViewer {

    @FXML
    private FlowPane mainFlowPane;

    public void createDepartmentBox(){
        DocumentBuilderFactory docfac = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docfac.newDocumentBuilder();
            Document doc = docBuilder.parse(new File("src/main/resources/com/galactic/originalgalactic/DynamicData.xml"));
            doc.getDocumentElement().normalize();

            NodeList departmentList = doc.getElementsByTagName("DepartmentDataBox");
            for (int i = 0; i < departmentList.getLength(); i++) {
                Node department = departmentList.item(i);

                // Outer VBox
                VBox outbox = new VBox();
                outbox.setPrefSize(200, 200);
                outbox.getStyleClass().add("departmentData");


                // Inner First (infoBox)
                VBox infoBox = new VBox();
                infoBox.setPrefSize(200, 150);
                infoBox.getStyleClass().add("DD_info");
                infoBox.setPadding(new Insets(32));

                // Inner Second (DD_Button)
                VBox DD_Button = new VBox();
                DD_Button.setPrefSize(200, 50);
                DD_Button.getStyleClass().add("DD_Button");
                DD_Button.setAlignment(Pos.CENTER);
                Label buttonText = new Label("Edit");
                buttonText.setStyle("-fx-font-size: 24; ");
                DD_Button.getChildren().add(buttonText);

                Label labelClass = null; // Declare labelClass
                Label labelStudent = null; // Declare labelStudent

                if (department.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList departmentDetails = department.getChildNodes();

                    for (int j = 0; j < departmentDetails.getLength(); j++) {
                        Node details = departmentDetails.item(j);
                        if (details.getNodeType() == Node.ELEMENT_NODE) {
                            String theDetails = details.getTextContent().trim();
                            if (j == 1) {
                                labelClass = new Label(theDetails);
                                labelClass.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: white;");
                            } else if (j == 3) {
                                labelStudent = new Label("Students: " + theDetails);
                                labelStudent.setStyle("-fx-font-size: 18; -fx-text-fill: White;");
                            }
                        }
                    }
                }

                // Add labels to infoBox if they were initialized
                if (labelClass != null) {
                    infoBox.getChildren().add(labelClass);
                }
                if (labelStudent != null) {
                    infoBox.getChildren().add(labelStudent);
                }

                // Add infoBox and DD_Button to outbox
                outbox.getChildren().addAll(infoBox, DD_Button);


                //VBox.setMargin(outbox, new javafx.geometry.Insets(10));

                // Add outbox to the mainFlowPane
                mainFlowPane.getChildren().add(outbox);
                FlowPane.setMargin(outbox, new Insets(8));
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();  // Use this for easier debugging
        }
    }


    @FXML
    public void initialize(){
        createDepartmentBox();
    }
}
