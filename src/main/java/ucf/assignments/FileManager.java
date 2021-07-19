package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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













    // TODO SAVE AS TXT
    // Post-conditions: Converts observable list to a regular list
    public List<InventoryItem> observableToList(ObservableList<InventoryItem> items) {

        return new ArrayList<>(items);

    }

    public String saveToTXT(File file, List<InventoryItem> items) {
        StringBuilder outputString = new StringBuilder(" ");


        try(FileWriter writer = new FileWriter(file)) {

            // Add all the objects to the list with \t to separate the items in each object
            for (InventoryItem item : items) {

                writer.write(item.itemName + "\t");
                writer.write(item.itemSerialNumber + "\t");
                writer.write(item.itemPrice + "\t");

                // Newline signifies new object
                writer.write("\n");

                // Create strings for testing
                if(outputString.toString().equals(" ")) {
                    outputString = new StringBuilder(String.format("%s\t", item.itemName));
                } else {
                    outputString.append(String.format("%s\t", item.itemName));
                }

                outputString.append(String.format("%s\t", item.itemSerialNumber));
                outputString.append(String.format("%s\t", item.itemPrice));
                outputString.append("\n");
            }

            return outputString.toString();

        } catch (IOException e) {
            return "File does not exist.\n";
        }
    }


    // TODO SAVE AS HTML

    // Post-conditions: Creates a header to save
    public String generateHead(File file) {

        return String.format("""
                \t<head>
                \t\t<meta content = "%s">
                \t\t<title>
                \t\t\tInventory Tracker
                \t\t</title>
                \t</head>
                """, file.getName());
    }
}
