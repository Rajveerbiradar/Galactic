package com.galactic.originalgalactic;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

// The DOM API helps you work with XML or HTML documents by turning them into a tree-like structure (called a Document Object Model) so you can read, add, change, or remove parts of the document.
// the `org.w3c.dom` API supports any markup language that follows the basic rules of XML structure
// As long as the markup language is valid XML, the DOM API can parse and manipulate it.
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException; // SAX (Simple API for XML) it is required while parsing the document

import java.io.File;
import java.io.IOException;

public class DepartmentDataController {

    @FXML
    private FlowPane mainFlowPane;

    public void showDepartment(){

        try {

            // ========================== Making the XML Document parse ===============================

            DocumentBuilderFactory docfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docfac.newDocumentBuilder();

            //The Document class is the root of a DOM tree. It is the main entry point for working with an XML or HTML document.
            // All elements, attributes, text, and other nodes are children of the Document. so we can use the Document to access its children.
            Document doc = docBuilder.parse(new File("src/main/resources/com/galactic/originalgalactic/DynamicData.xml"));

            doc.getDocumentElement().normalize();
            //======================================================================================

            NodeList departmentList = doc.getElementsByTagName("DepartmentDataBox"); //Getting the all Department Tags and making their list

            //================ Creating the UI elements as many as the DepartmentDataBox element =================

            // the loop is going to each DepartmentDataBox Element and creating its UI using the value inside its child element
            for (int i = 0; i < departmentList.getLength(); i++) {
                Node department = departmentList.item(i);

                // The UI have outer VBox and then two Vbox inside
                // first Inner VBox contain the details (Name and No. of student) the second VBox is just acting as a button

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

                // adding info to the First Inner Vbox (infoBox)
                Label labelClass = null; // Declare labelClass
                Label labelStudent = null; // Declare labelStudent

                //DepartmentDataBox element (from XML) have two child element inside, of which data is required
                if (department.getNodeType() == Node.ELEMENT_NODE) {
                    // the `department.getNodeType() == Node.ELEMENT_NODE` ensures the data inside the DepartmentDataBox element is Element only

                    NodeList departmentDetails = department.getChildNodes(); // getting list of element inside the DepartmentDataBox element

                    // iterating through each child Node of the DepartmentDataBox Element
                    for (int j = 0; j < departmentDetails.getLength(); j++) {

                        Node details = departmentDetails.item(j); //getting indexed node

                        if (details.getNodeType() == Node.ELEMENT_NODE) { // checking if the node is element
                            String theDetails = details.getTextContent().trim(); // .trim() method is used in Java to remove any leading and trailing whitespace from a string.
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



                // Add outbox to the mainFlowPane
                mainFlowPane.getChildren().add(outbox);
                FlowPane.setMargin(outbox, new Insets(8));
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();  // (Throw Exact error)
        }
    }

    public void AddDepartment(){
        try{
            // ========================== Making the XML Document parse ===============================

            DocumentBuilderFactory docfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docfac.newDocumentBuilder();

            //The Document class is the root of a DOM tree. It is the main entry point for working with an XML or HTML document.
            // All elements, attributes, text, and other nodes are children of the Document. so we can use the Document to access its children.
            Document doc = docBuilder.parse(new File("src/main/resources/com/galactic/originalgalactic/DynamicData.xml"));

            doc.getDocumentElement().normalize();
            //======================================================================================

            Element elem1 = doc.createElement("DepartmentDataBox");
            Element elem2 = doc.createElement("DepartmentName");
            Element elem3 = doc.createElement("Year");

            elem2.setTextContent("BSc.CS");
            elem3.setTextContent("2023-24");

            elem1.appendChild(elem2);
            elem1.appendChild(elem3);

            doc.getDocumentElement().appendChild(elem1);


            TransformerFactory transfact = TransformerFactory.newInstance();
            Transformer transformer = transfact.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Indent by 4 spaces


            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File("src/main/resources/com/galactic/originalgalactic/DynamicData.xml"));
            transformer.transform(domSource,streamResult);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize(){
        showDepartment();
    }
}
