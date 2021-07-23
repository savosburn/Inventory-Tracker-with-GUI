/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Savannah Osburn
 */

package ucf.assignments;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;

public class HelpControllerDeletingItems {

    @FXML private Button backButton;
    @FXML private Button returnButton;
    @FXML private Button nextButton;

    // Post-conditions: Switch scene to previous controller
    @FXML
    public String backButtonPressed() {
        try {

            // Close current stage
            Stage curStage = (Stage)backButton.getScene().getWindow();
            curStage.close();

            // Open previous stage
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HelpController.fxml")));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Help");
            stage.show();

            System.out.print("Scene switched to HelpController.fxml\n");
            return "Scene switched to HelpController.fxml\n";
        } catch(Exception e) {

            // Check if scene could not be switched
            System.out.print("Scene switch unsuccessful.\n");
            return "Scene switch unsuccessful.\n";
        }

    }

    // Post-conditions: Switch scene to next controller
    @FXML
    public String nextButtonPressed() {

        try {

            // Close current stage
            Stage curStage = (Stage)nextButton.getScene().getWindow();
            curStage.close();

            // Open previous scene
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HelpControllerEditItems.fxml")));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Help");
            stage.show();

            System.out.print("Scene switched to HelpControllerEditItems.fxml\n");
            return "Scene switched to HelpControllerEditItems.fxml\n";
        } catch(Exception e) {

            // Check if scene doesn't exist
            System.out.print("Scene switch unsuccessful.\n");
            return "Scene switch unsuccessful.\n";
        }

    }

    // Post-conditions: Scene is switched back to InventoryTrackerController controller
    @FXML
    public String returnButtonPressed() {

        try {
            // Close stage to go back to InventoryTrackerController that is still open in the background
            Stage curStage = (Stage)returnButton.getScene().getWindow();
            curStage.close();

            System.out.print("Scene switched to InventoryTrackerController.fxml\n");
            return "Scene switched to InventoryTrackerController.fxml\n";
        } catch(Exception e) {

            // Check if the scene switch was unsuccessful
            System.out.print("Scene switch unsuccessful.\n");
            return "Scene switch unsuccessful.\n";
        }
    }

    @FXML
    public void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'HelpControllerDeletingItems.fxml'.";
        assert returnButton != null : "fx:id=\"returnButton\" was not injected: check your FXML file 'HelpControllerDeletingItems.fxml'.";
        assert nextButton != null : "fx:id=\"nextButton\" was not injected: check your FXML file 'HelpControllerDeletingItems.fxml'.";

    }
}
