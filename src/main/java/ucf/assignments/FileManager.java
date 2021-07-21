/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Savannah Osburn
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
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
            inventory.setItemSerialNumber(parsedString[1].toUpperCase());
            inventory.setItemPrice(parsedString[2]);

            tempList.add(inventory);
        }

        return tempList;
    }

    // Post-conditions: Adds the strings from a .txt to an ArrayList
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

    // Post-conditions: Splits the strings from a .txt at the tabs (\t)
    public String[] parseStrings(String item) {
        return item.split("\t");
    }

    // Post-conditions:
    public ArrayList<String> loadHTML(File file) {
        ArrayList<String> inventory = new ArrayList<>();

        try {
            File myFile = new File(file.getName());
            Scanner scanner = new Scanner(myFile);

            startScanner(scanner);

            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();

                if (!nextLine.equals("\t</body>") && !nextLine.equals("</html>")) {
                    inventory.add(nextLine);
                }
            }

            for (String s : inventory) {
                System.out.print(s + "\n");
            }
            return inventory;

        } catch (Exception e) {
            System.out.print("Could not load file.\n");
            return null;
        }
    }

    // Post-conditions: Makes the scanner start at a certain point in the .html document
    private void startScanner(Scanner scanner) {
        for (int i = 0; i < 8; i++) {
            if (scanner.hasNextLine()){
                scanner.nextLine();
            }
        }
    }

    // Post-conditions: Parses HTML strings at "&emsp;"
    public String[] parseHTMLStrings(String item) {
        return item.split("&emsp;");
    }

    // Post-conditions: Parses the strings in the ArrayList and returns them as an ObservableList for a .html document
    public ObservableList<InventoryItem> addToObservableListHTML(ArrayList<String> items) {
        ObservableList<InventoryItem> tempList = FXCollections.observableArrayList();

        // Iterate through every object string
        for (String item : items) {

            // Parse each string
            String[] parsedString = parseHTMLStrings(item);

            System.out.print(parsedString[0]);
            System.out.print(parsedString[1].toUpperCase());
            System.out.print(parsedString[2]);

            // Add the string to the observablelist

            InventoryItem inventory = new InventoryItem();
            inventory.setItemName(parsedString[0]);
            inventory.setItemSerialNumber(parsedString[1].toUpperCase());
            inventory.setItemPrice(parsedString[2]);

            tempList.add(inventory);
        }

        return tempList;
    }

    // Post-conditions: Converts observable list to a regular list
    public List<InventoryItem> observableToList(ObservableList<InventoryItem> items) {
        return new ArrayList<>(items);
    }

    // Post-conditions: Saves items to a .txt files
    public String saveToTXT(File file, List<InventoryItem> items) {
        StringBuilder outputString = new StringBuilder(" ");

        try(FileWriter writer = new FileWriter(file)) {

            // Add all the objects to the list with \t to separate the items in each object
            for (InventoryItem item : items) {

                writer.write(item.itemName + "\t");
                writer.write(item.itemSerialNumber.toUpperCase() + "\t");
                writer.write(item.itemPrice + "\t");

                // Newline signifies new object
                writer.write("\n");

                // Create strings for testing
                if(outputString.toString().equals(" ")) {
                    outputString = new StringBuilder(String.format("%s\t", item.itemName));
                } else {
                    outputString.append(String.format("%s\t", item.itemName));
                }

                outputString.append(String.format("%s\t", item.itemSerialNumber.toUpperCase()));
                outputString.append(String.format("%s\t", item.itemPrice));
                outputString.append("\n");
            }

            return outputString.toString();

        } catch (IOException e) {
            return "File does not exist.\n";
        }
    }

    // Post-conditions: Generates the contents to save to a .html file
    public String generateHeader(File file, List<InventoryItem> items) {
        String link = "<html>\n";

        // Generate the html link
        link += generateHead(file);
        link += generateBody(items);
        link += "</html>";

        return link;
    }

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

    // Post-conditions: Creates the body information to save to a .html file
    public String generateBody(List<InventoryItem> items) {

        StringBuilder bodyString = new StringBuilder();
        bodyString.append("\t<body>\n");

        for (InventoryItem item : items) {

            bodyString.append(item.itemName).append("&emsp;");
            bodyString.append(item.itemSerialNumber.toUpperCase()).append("&emsp;");
            bodyString.append(item.itemPrice).append("&emsp;").append("<br>\n");
        }
        
        bodyString.append("\t</body>\n");

        return bodyString.toString();
    }

    // Post-conditions: Prints information to the .html file
    public Boolean writeToHTMLFile(File file, String string) {
        try {
            PrintWriter writer = new PrintWriter(file);

            writer.write(string);
            writer.close();

            return true;

        } catch (IOException e) {
            System.out.print("File does not exist.\n");
            return false;
        }
    }
}
