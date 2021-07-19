package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    // Post-conditions: Parses the strings in the ArrayList and returns them as an ObservableList
    public ObservableList<InventoryItem> addToObservableList(ArrayList<String> items) {
        ObservableList<InventoryItem> tempList = FXCollections.observableArrayList();

        // Iterate through every object string
        for (String item : items) {

            // Parse each string
            String[] parsedString = parseStrings(item);

            System.out.print(parsedString[0]);
            System.out.print(parsedString[1]);
            System.out.print(parsedString[2]);

            // Add the string to the observablelist

            InventoryItem inventory = new InventoryItem();
            inventory.setItemName(parsedString[0]);
            inventory.setItemSerialNumber(parsedString[1]);
            inventory.setItemPrice(parsedString[2]);

            tempList.add(inventory);


            //tempList = setItems(inventoryItems, parsedString[0], parsedString[1], parsedString[3]);
        }

        return tempList;
    }

    // Post-conditions: Adds the strings to an ArrayList
    public ArrayList<String> loadFileStrings(File file) {
        // Create new ArrayList
        ArrayList<String> items = new ArrayList<>();

        try {
            // Get the file and make it readable
            File myFile = new File(file.getName());
            Scanner scanner = new Scanner(myFile);

            // Each line holds one object
            while (scanner.hasNextLine()) {
                items.add(scanner.nextLine());
            }

            return items;
        } catch (FileNotFoundException e) {

            // Check if the file was not found
            System.out.print("File not found.\n");

            return null;
        }
    }

    public String[] parseStrings(String item) {
        return item.split("\t");
    }
}
