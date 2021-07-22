/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Savannah Osburn
 */

package ucf.assignments;

import java.io.File;
import java.text.NumberFormat;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class InventoryTrackerController {

    @FXML private TableView<InventoryItem> inventoryTrackerTable;
    @FXML private TableColumn<InventoryItem, String> itemColumn;
    @FXML private TableColumn<InventoryItem, String> serialNumberColumn;
    @FXML private TableColumn<InventoryItem, String> priceColumn;
    @FXML private Button addItemButton;
    @FXML private MenuItem saveAsButton;
    @FXML private MenuItem loadButton;
    @FXML private Button removeItemButton;
    @FXML private MenuButton fileMenuButton;
    @FXML private TextField itemPriceTextField;
    @FXML private TextField itemSerialNumberTextField;
    @FXML private TextField itemNameTextField;
    @FXML private TextField searchTextField;
    @FXML private Button helpButton;

    // Declare objects
    FileManager fileManager = new FileManager();
    FileChooser fileChooser = new FileChooser();

    Set<String> serialNumbers = new HashSet<>();
    ObservableList<InventoryItem> inventoryItems = FXCollections.observableArrayList();

    // Post-conditions: Adds a new inventory item to table
    @FXML
    public void addItemButtonClicked() {
        System.out.print("Add Item button clicked.\n");

        // Get the information that needs to be added to the inventory
        String name = itemNameTextField.getText();
        String number = itemSerialNumberTextField.getText();
        String price = itemPriceTextField.getText();

        // If the name is the wrong length
        if (!isCorrectNameLength(name.length())) {

            // Provide error message
            System.out.print(toInvalidNameController());
        }

        // If the serial number is the wrong length or the wrong format
        else if (!isCorrectSerialNumberFormat(number) || !isCorrectSerialNumberLength(number)) {

            // Provide error message
            System.out.print(toInvalidSerialNumberController());
        }

        // If the price is not a number
        else if (!isCorrectPriceFormat(price)) {

            // Provide error message
            System.out.print(toInvalidPriceController());
        }

        // If the item is already in the inventory tracker
        else if (alreadyInSet(serialNumbers, number, inventoryItems)) {

            // Provide error message
            System.out.print(toDuplicateItemController());
        }

        // Otherwise, if the item is valid
        else {

            // Set the price to the proper format
            double priceDouble = Double.parseDouble(price);
            String priceToString = priceFormat(priceDouble);

            // Add the item to the inventory tracker
            ObservableList<InventoryItem> temp = setItems(inventoryItems, name, number, priceToString);
            System.out.print(temp.get(temp.size() - 1).getItemName() + "added");
        }

        // Update the table and clear the text fields
        inventoryTrackerTable.refresh();
        inventoryTrackerTable.setItems(inventoryItems);

        clearTextFields();
    }

    // Post-conditions: Determines if a serial number is already in the set
    public Boolean alreadyInSet(Set<String> serialNumber, String number, ObservableList<InventoryItem> allItems) {
        // Add the serial number to a set
        serialNumber.add(number);

        // Based on if the set changes size, determine if the item is already in the inventory
        return serialNumber.size() != (allItems.size() + 1);
    }

    // Post-conditions: Returns true if the name is in the correct length requirements
    public Boolean isCorrectNameLength(Integer name) {
        return (name >= 2 && name <= 256);
    }

    // Post-conditions: Returns true if the serial number is in the correct format
    public Boolean isCorrectSerialNumberFormat(String serialNumber) {
        return serialNumber.matches("[a-zA-Z0-9]*");
    }

    // Post-conditions: Returns true if the serial number is the correct length
    public Boolean isCorrectSerialNumberLength(String serialNumber){
        return (serialNumber.length() == 10);
    }

    // Post-conditions: Returns true if the price is the correct format
    public Boolean isCorrectPriceFormat(String price) {
        try {
            // If the price can be parsed into a double, return true
            double priceToDouble = Double.parseDouble(price);

            //System.out.print("String parse: " + priceToDouble + "\n");

            return true;

        } catch (Exception e) {

            // If the price could not be parsed, return false
            return false;
        }
    }

    // Post-conditions: Sets values into an ObservableList
    public ObservableList<InventoryItem> setItems(ObservableList<InventoryItem> items, String name, String number, String price) {
        InventoryItem item = new InventoryItem();

        // Set the InventoryItem fields
        item.setItemName(name);
        item.setItemSerialNumber(number.toUpperCase());
        item.setItemPrice(price);

        // Add the InventoryItem to the ObservableList, and return the ObservableList
        items.add(item);

        return items;
    }

    // Post-conditions: Formats the price into an acceptable currency format
    public String priceFormat(Double price) {
        // Set the currency format
        Locale usa = new Locale("en", "US");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

        // Return the formatted price
        return dollarFormat.format(price);
    }

    // Post-conditions: Clears all the text fields in the GUI
    private void clearTextFields() {
        itemNameTextField.clear();
        itemSerialNumberTextField.clear();
        itemPriceTextField.clear();
    }

    // Post-conditions: Switches scene to InvalidNameController.fxml
    public String toInvalidNameController() {
        try {
            // Switch scene to InvalidNameController.fxml
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InvalidNameController.fxml")));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Invalid Name");
            stage.show();

            return "Scene switched to InvalidNameController.fxml\n";
        } catch(Exception e) {

            // Catch if the scene could not be opened
            return "Scene switch unsuccessful.\n";
        }
    }

    // Post-conditions: Switches scene to InvalidSerialNumberController.fxml
    public String toInvalidSerialNumberController() {
        try {
            // Switch scene to InvalidSerialNumberController.fxml
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InvalidSerialNumberController.fxml")));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Invalid Serial Number");
            stage.show();

            return "Scene switched to InvalidSerialNumber.fxml\n";
        } catch(Exception e) {

            // Catch if the scene could not be opened
            return "Scene switch unsuccessful.\n";
        }
    }

    // Post-conditions: Switches scene to InvalidPriceController.fxml
    public String toInvalidPriceController() {
        try {
            // Switch scene to InvalidPriceController.fxml
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InvalidPriceController.fxml")));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Invalid Price");
            stage.show();

            return "Scene switched to InvalidPriceController.fxml\n";
        } catch(Exception e) {

            // Catch if the scene could not be opened
            return "Scene switch unsuccessful.\n";
        }
    }

    // Post-conditions: Switches scene to DuplicateItemController.fxml
    public String toDuplicateItemController() {
        try {
            // Switch scene to DuplicateItemController.fxml
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("DuplicateItemController.fxml")));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Duplicate Item");
            stage.show();

            return "Scene switched to DuplicateItemController.fxml\n";
        } catch(Exception e) {

            // Catch if the stage could not be opened
            return "Scene switch unsuccessful.\n";
        }
    }

    // TODO CREATE A HELP SCREEN?
    @FXML
    void helpButtonClicked() {

    }

    // Post-conditions: Signifies that the file menu was opened
    @FXML
    public void fileMenuButtonClicked() {
        // User clicks on file menu button
        // Drop down appears
        System.out.print("File Menu Opened.\n");
    }

    // Post-conditions: Loads information from a chosen file to the TableView
    @FXML
    public void loadButtonClicked() {
        System.out.print("Load button clicked.\n");

        // Open file chooser, set title, and set extensions
        // TODO: FINISH OR REMOVE JSON
        Window stage = fileMenuButton.getScene().getWindow();
        fileChooser.setTitle("Load To Do List");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TSV (Text) File", "*.txt"),
                new FileChooser.ExtensionFilter("JSON File", "*.json"),
                new FileChooser.ExtensionFilter("HTML File", "*.html")
        );

        try {
            // Let user choose which file to load
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile()); // save the chosen directory for the next time it opens

            // Clear ObservableList to load new ObservableList
            inventoryItems.clear();

            // If the file is a .txt file
            if (file.toString().contains(".txt")) {
                // Parse the content and add them to the ObservableList
                ArrayList<String> inventoryStrings = fileManager.loadFileStrings(file);

                inventoryItems = fileManager.addToObservableList(inventoryStrings);
            }

            // If the file is a .html file
            else if (file.toString().contains(".html")) {
                // Parse the content and add them to the ObservableList
                ArrayList<String> inventoryStrings = fileManager.loadHTML(file);

                inventoryItems = fileManager.addToObservableListHTML(inventoryStrings);
            }

            // Refresh and reset the table
            inventoryTrackerTable.refresh();
            inventoryTrackerTable.setItems(inventoryItems);
        } catch (Exception e) {

            // Catch if the file could not be loaded
            System.out.print("Could not load file.\n");
        }
    }

    // Post-conditions: Removes a selected item from the TableView
    @FXML
    public Boolean removeItemButtonClicked() {
        System.out.print("Remove Item button pressed.\n");

        try {
            // Remove selected item from ObservableList
            inventoryItems = deleteItem(inventoryTrackerTable.getSelectionModel().getSelectedItem(), inventoryItems, serialNumbers);

            // Update the GUI table
            inventoryTrackerTable.refresh();
            inventoryTrackerTable.setItems(inventoryItems);

            return true;
        } catch (Exception e) {
            // Catch if the item could not be deleted
            System.out.print("The item was not deleted.\n");

            return false;
        }
    }

    // Post-conditions: Removes item from Observable list and from Set
    public ObservableList<InventoryItem> deleteItem(InventoryItem item, ObservableList<InventoryItem> inventoryTracker, Set<String> serialNum) {
        System.out.printf("Item number %s deleted from Inventory Tracker.\n", item.itemSerialNumber);

        // Remove item from the set
        serialNum.remove(item.itemSerialNumber);

        // Set the InventoryItem fields to null, and remove the item from the inventory tracker
        item = setToNull(item);
        inventoryTracker.remove(item);

        return inventoryTracker;
    }

    // Post-conditions: Sets all fields of an InventoryItem to null
    public InventoryItem setToNull(InventoryItem item) {
        item.setItemName(null);
        item.setItemPrice(null);
        item.setItemSerialNumber(null);

        return item;
    }

    // Post-conditions: Saves the items in the TableView to an external file
    @FXML
    public void saveAsButtonClicked() {
        System.out.print("Save as... button clicked.\n");

        // Open the FileChooser and set the extensions
        Window stage = fileMenuButton.getScene().getWindow();
        fileChooser.setTitle("Save Dialog");
        fileChooser.setInitialFileName("New File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TSV (Text) File", "*.txt"),
                new FileChooser.ExtensionFilter("JSON File", "*.json"),
                new FileChooser.ExtensionFilter("HTML File", "*.html")
        );

        try {
            // Let user name the file to save and save the chosen directory for the next time it opens
            File file = fileChooser.showSaveDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());

            // Change the list type of the items
            List<InventoryItem> saveList = fileManager.observableToList(inventoryItems);

            // If the user chooses .txt
            if (file.toString().contains(".txt")) {
                // Save to a .txt
                System.out.print(fileManager.saveToTXT(file, saveList));
            }

            // If the user chooses .html
            else if (file.toString().contains(".html")) {
                // Create the .html string and print it to a file
                String printString = fileManager.generateHeader(file, saveList);
                Boolean printed = fileManager.writeToHTMLFile(file, printString);

                System.out.print(printed);
            }

        } catch (Exception e) {
            // Catch if the file cannot be saved
            System.out.print("File could not be saved.\n");
        }

    }

    // Post-condition: Returns true if the user's search is in an InventoryItem
    private boolean searchFindsItem(InventoryItem item, String searchText) {
        return (item.getItemName().toLowerCase().contains(searchText.toLowerCase())) ||
                (item.getItemSerialNumber().toLowerCase().contains(searchText.toLowerCase())) ||
                (item.getItemPrice().toLowerCase().contains(searchText.toLowerCase()));
    }

    // Post-conditions: Adds to a filtered list if the searched item was found
    private ObservableList<InventoryItem> filterList(ObservableList<InventoryItem> items, String searchText) {
        // Create a list
        List <InventoryItem> filteredList = new ArrayList<>();

        // Loop through every item in the ObservableList
        for (InventoryItem item : items) {
            // If the search is found in the item, add it to the new list
            if (searchFindsItem(item, searchText)) {
                filteredList.add(item);
            }
        }

        // Return as an ObservableList
        return FXCollections.observableList(filteredList);
    }

    // Post-conditions: initializes the TableView
    @FXML
    void initialize() {
        assert inventoryTrackerTable != null : "fx:id=\"inventoryTrackerTable\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert itemColumn != null : "fx:id=\"itemColumn\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert serialNumberColumn != null : "fx:id=\"serialNumberColumn\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert priceColumn != null : "fx:id=\"priceColumn\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert addItemButton != null : "fx:id=\"addItemButton\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert fileMenuButton != null : "fx:id=\"fileMenuButton\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert saveAsButton != null : "fx:id=\"saveAsButton\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert loadButton != null : "fx:id=\"loadButton\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert removeItemButton != null : "fx:id=\"removeItemButton\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert itemPriceTextField != null : "fx:id=\"itemPriceTextField\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert itemSerialNumberTextField != null : "fx:id=\"itemSerialNumberTextField\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert itemNameTextField != null : "fx:id=\"itemNameTextField\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert searchTextField != null : "fx:id=\"searchTextField\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";
        assert helpButton != null : "fx:id=\"helpButton\" was not injected: check your FXML file 'InventoryTrackerController.fxml'.";

        // Set the initial directory of the file chooser to be the user's directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        // Set new table placeholder and update the table
        inventoryTrackerTable.setPlaceholder(new Label("Nothing in inventory."));
        inventoryTrackerTable.setItems(inventoryItems);

        // Set itemColumn to editable text fields
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        itemColumn.setOnEditCommit(event -> {
            InventoryItem item = event.getRowValue();

            // Get the old and new values
            String originalName = event.getOldValue();
            String newName = event.getNewValue();

            // If the name is the correct length
            if (isCorrectNameLength(newName.length())) {
                // Set the table with the new name
                item.setItemName(newName);
            }

            // If the name is not the correct length
            else {
                // Set the table with the original name and display an error message
                item.setItemName(originalName);
                System.out.print(toInvalidNameController());
            }

            // Update and refresh the table
            inventoryTrackerTable.refresh();
            inventoryTrackerTable.setItems(inventoryItems);
        });

        // Set serialNumberColumn to editable text fields
        serialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("itemSerialNumber"));
        serialNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        serialNumberColumn.setOnEditCommit(event -> {
            InventoryItem item = event.getRowValue();

            // Get original and new serial numbers
            String oldNum = item.itemSerialNumber;
            String num = event.getNewValue();

            // If the new serial number is the correct length and format
            if (isCorrectSerialNumberLength(num) && isCorrectSerialNumberFormat(num)) {

                // And if it is not already in the set
                if (!alreadyInSet(serialNumbers, num, inventoryItems)) {
                    // Set the new serial number and update the lists
                    serialNumbers.remove(oldNum);
                    serialNumbers.add(num.toUpperCase());
                    item.setItemSerialNumber(event.getNewValue().toUpperCase());
                }

                // If it is already in the set
                else {
                    // Keep the original value and display an error scene
                    item.setItemSerialNumber(oldNum.toUpperCase());
                    System.out.print(toDuplicateItemController());
                }
            }

            // If the serial number is an incorrect length and/or format
            else {
                // Display an error scene
                System.out.print(toInvalidSerialNumberController());
            }

            // Refresh and reset the table
            inventoryTrackerTable.refresh();
            inventoryTrackerTable.setItems(inventoryItems);
        });

        // Set the priceColumn to editable text fields
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setOnEditCommit(event -> {
            // Get the user's new value
            InventoryItem item = event.getRowValue();
            String newPrice = event.getNewValue();

            // If the price is a valid number
            if (isCorrectPriceFormat(newPrice)) {
                // Set the price in the table as a formatted currency amount
                String formatted = priceFormat(Double.parseDouble(newPrice));
                item.setItemPrice(formatted);
            }

            // If the price is not a valid number
            else {
                // Display an error scene
                System.out.print(toInvalidPriceController());
            }

            // Refresh and reset the table
            inventoryTrackerTable.refresh();
            inventoryTrackerTable.setItems(inventoryItems);
        });

        // Set table to update with the search text field
        searchTextField.textProperty().addListener(((observable, oldValue, newValue) ->
                inventoryTrackerTable.setItems(filterList(inventoryItems, newValue))));
    }
}
