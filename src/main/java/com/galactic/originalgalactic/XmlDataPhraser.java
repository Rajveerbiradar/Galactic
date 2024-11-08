package com.galactic.originalgalactic;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlDataPhraser {
    //xml to data
    public void xmlToData(String fileName){



        //Document Builder Maker Factory.
        DocumentBuilderFactory docfac = DocumentBuilderFactory.newInstance();
        try {
            //Document Builder.
            DocumentBuilder docBuilder = docfac.newDocumentBuilder();

            //Get Document.
            Document doc = docBuilder.parse(new File(fileName));

            //Normalize the xml document.
            doc.getDocumentElement().normalize();

            NodeList departmentList = doc.getElementsByTagName("DepartmentDataBox");
            for(int i = 0; i < departmentList.getLength(); i++){
                Node department = departmentList.item(i);

                if(department.getNodeType() == Node.ELEMENT_NODE){
                    NodeList departmentDetails = department.getChildNodes();

                    for(int j = 0; j < departmentDetails.getLength(); j++){
                        Node details = departmentDetails.item(j);
                        if (details.getNodeType() == Node.ELEMENT_NODE) {
                            System.out.println("  " + details.getNodeName() + ": " + details.getTextContent().trim());
                        }
                    }
                    System.out.println(" ");
                }
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


    }





}

class tabtak{
    public static void main(String[] args){
        XmlDataPhraser xdp = new XmlDataPhraser();
        xdp.xmlToData("src/main/resources/com/galactic/originalgalactic/DynamicData.xml");
    }
}