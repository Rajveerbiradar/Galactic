module com.galactic.originalgalactic {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.galactic.originalgalactic to javafx.fxml;
    exports com.galactic.originalgalactic;
}