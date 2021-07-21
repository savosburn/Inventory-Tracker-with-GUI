/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Savannah Osburn
 */

package ucf.assignments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InvalidSerialNumberController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML private Button okButton;

    // Post-conditions: Returns scene to InventoryTrackerController.fxml
    @FXML
    public String okButtonPressed() {
        try {
            // Close scene to return to ToDoList controller that is still open in the background
            Stage curStage = (Stage)okButton.getScene().getWindow();
            curStage.close();

            System.out.print("Scene switched to InventoryTrackerController.fxml\n");
            return "Scene switched to InventoryTrackerController.fxml\n";
        } catch(Exception e) {

            // Check that scene is properly switched
            System.out.print("Scene switch unsuccessful.\n");
            return "Scene switch unsuccessful.\n";
        }
    }

    // Post-conditions: Initializes the button
    @FXML
    void initialize() {
        assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'InvalidSerialNumberController.fxml'.";
    }
}