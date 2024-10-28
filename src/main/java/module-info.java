module com.galactic.originalgalactic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
//    requires org.apache.commons.collections4;


    opens com.galactic.originalgalactic to javafx.fxml;
    exports com.galactic.originalgalactic;
}