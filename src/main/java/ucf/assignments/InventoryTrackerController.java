/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Savannah Osburn
 */

package ucf.assignments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<InventoryItem> inventoryTrackerTable;

    @FXML
    private TableColumn<InventoryItem, String> itemColumn;

    @FXML
    private TableColumn<InventoryItem, String> serialNumberColumn;

    @FXML
    private TableColumn<InventoryItem, String> priceColumn;

    @FXML
    private Button addItemButton;

    @FXML
    private MenuItem saveAsButton;

    @FXML
    private MenuItem loadButton;

    @FXML
    private Button removeItemButton;

    @FXML
    private MenuButton fileMenuButton;

    @FXML
    private TextField itemPriceTextField;

    @FXML
    private TextField itemSerialNumberTextField;

    @FXML
    private TextField itemNameTextField;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button helpButton;

    FileChooser fileChooser = new FileChooser();

    // Declare objects
    Set<String> serialNumbers = new HashSet<>();


    ObservableList<InventoryItem> inventoryItems = FXCollections.observableArrayList();

    @FXML
    void addItemButtonClicked() {

        String name = itemNameTextField.getText();
        String number = itemSerialNumberTextField.getText();
        String price = itemPriceTextField.getText();


        // If the name is the wrong length
        if (!isCorrectNameLength(name.length())) {
            // switch scenes
            System.out.print(toInvalidNameController());
        }

        // If the serial number is wrong
        else if (!isCorrectSerialNumberFormat(number) || !isCorrectSerialNumberLength(number)) {
            // switch scenes
            System.out.print(toInvalidSerialNumberController());
        }

        else if (!isCorrectPriceFormat(price)) {
            // Switch scenes
            System.out.print(toInvalidPriceController());
        }

        else if (alreadyInSet(serialNumbers, number, inventoryItems)) {
            // switch scenes
            System.out.print(toDuplicateItemController());
        }

        else {
            double priceDouble = Double.parseDouble(price);
            String priceToString = priceFormat(priceDouble);

            ObservableList<InventoryItem> temp = setItems(inventoryItems, name, number, priceToString);
            System.out.print(temp.get(temp.size() - 1).getItemName() + "added");
        }

        inventoryTrackerTable.refresh();
        inventoryTrackerTable.setItems(inventoryItems);

        clearTextFields();
    }

    // Post-conditions: Determines if it's already in the set
    public Boolean alreadyInSet(Set<String> serialNumber, String number, ObservableList<InventoryItem> allItems) {

        serialNumber.add(number);

        return serialNumber.size() != (allItems.size() + 1);
    }

    // Post-conditions: Determines if the item name is the correct length
    public Boolean isCorrectNameLength(Integer name) {
        return (name > 2 && name < 256);
    }

    // Post-conditions: Determines if the serial number is in the correct format
    public Boolean isCorrectSerialNumberFormat(String serialNumber) {
        return serialNumber.matches("[a-zA-Z0-9]*");
    }

    // Post-conditions: Determines if the serial number is the correct length
    public Boolean isCorrectSerialNumberLength(String serialNumber){
        return (serialNumber.length() == 10);
    }

    // Post-conditions: Determines if the price is in the correct format
    public Boolean isCorrectPriceFormat(String price) {
        try {
            double priceToDouble = Double.parseDouble(price);

            System.out.print(priceToDouble);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // Post-conditions: Creates an observable list with the values
    public ObservableList<InventoryItem> setItems(ObservableList<InventoryItem> items, String name, String number, String price) {
        InventoryItem item = new InventoryItem();

        // Make sure item is the correct length
        item.setItemName(name);
        item.setItemSerialNumber(number);
        item.setItemPrice(price);

        items.add(item);

        return items;
    }

    // Post-conditions: Formats the price into an acceptable currency format
    public String priceFormat(Double price) {

        // turn into a correct price if it is
        Locale usa = new Locale("en", "US");
        //  Currency dollars = Currency.getInstance(usa);
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

        return dollarFormat.format(price);
    }

    // Post-conditions: Clears all the text fields in the gui
    private void clearTextFields() {
        itemNameTextField.clear();
        itemSerialNumberTextField.clear();
        itemPriceTextField.clear();
    }

    public String toInvalidNameController() {

        try {
            // Open new stage
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InvalidNameController.fxml")));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Invalid Name");
            stage.show();

            return "Scene switched to Name.fxml\n";
        } catch(Exception e) {

            // Catch if the stage could not be opened
            return "Scene switch unsuccessful.\n";
        }

    }

    public  String toInvalidSerialNumberController() {

        try {
            // Open new stage
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InvalidSerialNumberController.fxml")));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Invalid Serial Number");
            stage.show();

            return "Scene switched to InvalidSerialNumber.fxml\n";
        } catch(Exception e) {

            // Catch if the stage could not be opened
            return "Scene switch unsuccessful.\n";
        }

    }

    public String toInvalidPriceController() {

        try {
            // Open new stage
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InvalidPriceController.fxml")));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Invalid Price");
            stage.show();

            return "Scene switched to InvalidPriceController.fxml\n";
        } catch(Exception e) {

            // Catch if the stage could not be opened
            return "Scene switch unsuccessful.\n";
        }
    }

    // Post-conditions: Switches scene to DuplicateItemController.fxml
    public String toDuplicateItemController() {

        try {
            // Open new stage
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

    @FXML
    void helpButtonClicked(ActionEvent event) {

    }


    // TODO SEARCH
    /*
    public void search {

        FilteredList<InventoryItem> filteredList = new FilteredList<>(inventoryItems, b -> true);

        searchTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredList.setPredicate(item -> {
                // If filter text is empty, display all persons
                if (newValue == null || newValue.isEmpty()) {
                    return  true;
                }

                // Compare
                String lowerCaseFilter = newValue.toLowerCase();

                if (item.getItemName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }

                else if (item.getItemSerialNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (item.getItemPrice().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        }));

        SortedList<InventoryItem> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(inventoryTrackerTable.comparatorProperty());
        inventoryTrackerTable.setItems(sortedList);
    } */








    // Post-conditions: Signifies that the file menu was opened
    @FXML
    public void fileMenuButtonClicked() {
        // User clicks on file menu button
        // Drop down appears
        System.out.print("File Menu Opened.\n");
    }

    @FXML
    void loadButtonClicked(ActionEvent event) {

        /*

        // File chooser opens
        //Window stage = fileMenuButton.getScene().getWindow();
        Window stage = fileMenuButton.getScene().getWindow();
        fileChooser.setTitle("Load To Do List");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text File", "*.txt"));

        try {
            // User chooses file
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile()); // save the chosen directory for the next time it opens

            // Clear ObservableList to load new ObservableList
            inventoryItems.clear();

            // Chosen file is loaded
            // TODO UNCOMMENT THESSE NEXT TWO LINES
            //ArrayList<String> fileStrings = loadFileStrings(file);
           // ObservableList<InventoryItem> loadedFile = addToObservableList(fileStrings);

            // Refresh and reset the table
           // inventoryTrackerTable.refresh();
            //inventoryTrackerTable.setItems(loadedFile);
        } catch (Exception e) {

            // Check if file could be loaded
            System.out.print("Could not load file.\n");
        } */

    }

    @FXML
    public Boolean removeItemButtonClicked(ActionEvent event) {
        System.out.print("Remove item button pressed.\n");

        try {
            // Delete selected item from ObservableList
            inventoryItems = deleteItem(inventoryTrackerTable.getSelectionModel().getSelectedItem(), inventoryItems, serialNumbers);

            // Remove selected item from gui table
            inventoryTrackerTable.refresh();
            inventoryTrackerTable.setItems(inventoryItems);

            // TODO: REMOVE, THIS IS JUST FOR TESTING
            for (InventoryItem inventoryItem : inventoryItems) {
                System.out.print(inventoryItem.getItemSerialNumber() + "\n");
            }

            System.out.print(serialNumbers + "\n");

            return true;
        } catch (Exception e) {

            // Return false if the item was not deleted
            System.out.print("The item was not deleted.\n");
            return false;
        }

    }

    // Post-conditions: Removes item from Observable list and from Set
    public ObservableList<InventoryItem> deleteItem(InventoryItem item, ObservableList<InventoryItem> inventoryTracker, Set<String> serialNum) {
        System.out.printf("Item number %s deleted from Inventory Tracker.\n", item.itemSerialNumber);

        // Remove from set
        serialNum.remove(item.itemSerialNumber);

        // Set everything to null, then remove
        item = setToNull(item);
        inventoryTracker.remove(item);

        return inventoryTracker;
    }

    public InventoryItem setToNull(InventoryItem item) {
        item.setItemName(null);
        item.setItemPrice(null);
        item.setItemSerialNumber(null);

        return item;
    }

    @FXML
    void saveAsButtonClicked(ActionEvent event) {

    }


    // Post-condition: Determines if the item is in the list
    private boolean searchFindsItem(InventoryItem item, String searchText) {
        return (item.getItemName().toLowerCase().contains(searchText.toLowerCase())) ||
                (item.getItemSerialNumber().toLowerCase().contains(searchText.toLowerCase())) ||
                (item.getItemPrice().toLowerCase().contains(searchText.toLowerCase()));
    }

    // Post-conditions: Adds to a filtered list
    private ObservableList<InventoryItem> filterList(ObservableList<InventoryItem> items, String searchText) {
        List <InventoryItem> filteredList = new ArrayList<>();
        for (InventoryItem item : items) {
            if (searchFindsItem(item, searchText)) filteredList.add(item);
        }

        return FXCollections.observableList(filteredList);
    }

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

        // Set new table placeholder
        inventoryTrackerTable.setPlaceholder(new Label("Nothing in inventory."));


        // Set the items in the inventory tracker
        inventoryTrackerTable.setItems(inventoryItems);

        // Set itemColumn to editable text fields
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        itemColumn.setOnEditCommit(event -> {
            InventoryItem item = event.getRowValue();
            item.setItemName(event.getNewValue());
        });

        inventoryTrackerTable.refresh();
        inventoryTrackerTable.setItems(inventoryItems);

        // Set serial number column
        serialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("itemSerialNumber"));
        serialNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        serialNumberColumn.setOnEditCommit(event -> {
            InventoryItem item = event.getRowValue();

            // Get the serial number
            String oldNum = item.itemSerialNumber;
            String num = event.getNewValue();

            // If it's valid
            if (isCorrectSerialNumberLength(num) && isCorrectSerialNumberFormat(num)) {

                // If it's not already in the set
                if (!alreadyInSet(serialNumbers, num, inventoryItems)) {
                    // set it
                    serialNumbers.remove(oldNum);
                    serialNumbers.add(num);
                    item.setItemSerialNumber(event.getNewValue());
                }
                // else
                else {
                    item.setItemSerialNumber(oldNum);
                    System.out.print(toDuplicateItemController());
                }
            }

            // Else
            else {
                // Bring up an error screen
                System.out.print(toInvalidSerialNumberController());

            }
            inventoryTrackerTable.refresh();
            inventoryTrackerTable.setItems(inventoryItems);
        });

        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());


        priceColumn.setOnEditCommit(event -> {
            InventoryItem item = event.getRowValue();

            // Make sure it's a valid double

            String newPrice = event.getNewValue();

            if (isDouble(newPrice)) {
                String formatted = priceFormat(Double.parseDouble(newPrice));
                item.setItemPrice(formatted);
            } else {

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

    public Boolean isDouble(String checkDouble) {

        try {
            Double testDouble = Double.parseDouble(checkDouble);

          //  System.out.print(testDouble + "\n");

            return true;
        } catch (Exception e) {

            return false;
        }
    }
}
