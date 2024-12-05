package com.galactic.originalgalactic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorPopupUtil {
    private static Stage popupStage; // Stage for the popup
    private static FXMLLoader loader; // FXML loader for the popup

    // Initialize the popup (only once for reusability)
    public static void initPopup() throws Exception {
        loader = new FXMLLoader(ErrorPopupUtil.class.getResource("Error_POPUP.fxml"));
        Scene scene = new Scene(loader.load());
        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with main window
        popupStage.setScene(scene);
        popupStage.setResizable(false);
    }

    // Show the error popup with the provided message
    public static void showError(String errorMessage) throws Exception {
        if (popupStage == null || loader == null) {
            initPopup(); // Initialize the popup if not already done
        }

        // Get the controller and update the error message
        ErrorPopupController controller = loader.getController();
        controller.setErrorMessage(errorMessage);

        // Set the stage in the controller (optional for the close button)
        controller.setStage(popupStage);

        popupStage.showAndWait(); // Show the popup and wait for it to be closed
    }
}
