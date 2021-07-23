/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Savannah Osburn
 */

package ucf.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Try to open the primary scene
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InventoryTrackerController.fxml")));

            Scene scene = new Scene(root);

            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css.css")).toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Inventory Tracker");
            primaryStage.show();

        } catch (IOException e) {
            System.out.print("Could not load scene.\n");
        }
    }
}