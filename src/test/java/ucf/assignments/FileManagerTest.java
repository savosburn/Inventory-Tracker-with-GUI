/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Savannah Osburn
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @Test
    public void toJsonTest() {
        // Create objects and lists
        FileManager fileManager = new FileManager();
        List<InventoryItem> inventoryItems = new ArrayList<>();

        // Add information to the list
        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        InventoryItem itemTwo = new InventoryItem();
        itemTwo.setItemName("pears");
        itemTwo.setItemPrice("$4.00");
        itemTwo.setItemSerialNumber("01234ASDFG");

        InventoryItem itemThree = new InventoryItem();
        itemThree.setItemName("grapes");
        itemThree.setItemPrice("$1.00");
        itemThree.setItemSerialNumber("0000000000");

        inventoryItems.add(itemOne);
        inventoryItems.add(itemTwo);
        inventoryItems.add(itemThree);

        // Create expected string
        String expected = """
                [
                  {
                    "itemName": "apples",
                    "itemSerialNumber": "0123456789",
                    "itemPrice": "$3.00"
                  },
                  {
                    "itemName": "pears",
                    "itemSerialNumber": "01234ASDFG",
                    "itemPrice": "$4.00"
                  },
                  {
                    "itemName": "grapes",
                    "itemSerialNumber": "0000000000",
                    "itemPrice": "$1.00"
                  }
                ]""";

        // Get the actual string
        String actual = fileManager.toJSON(inventoryItems);

        // Check that the two strings are equal
        assertEquals(expected, actual);
    }

    @Test
    public void saveToJSONFile() {
        // Create objects, files, and strings
        FileManager fileManager = new FileManager();
        File file = new File("JSONInputTest.json");

        // Create a string to save to the file
        String jsonString =  """
                [
                  {
                    "itemName": "apples",
                    "itemSerialNumber": "0123456789",
                    "itemPrice": "$3.00"
                  },
                  {
                    "itemName": "pears",
                    "itemSerialNumber": "01234ASDFG",
                    "itemPrice": "$4.00"
                  },
                  {
                    "itemName": "grapes",
                    "itemSerialNumber": "0000000000",
                    "itemPrice": "$1.00"
                  }
                ]""";

        // Create the expected output
        String expected =  """
                [
                  {
                    "itemName": "apples",
                    "itemSerialNumber": "0123456789",
                    "itemPrice": "$3.00"
                  },
                  {
                    "itemName": "pears",
                    "itemSerialNumber": "01234ASDFG",
                    "itemPrice": "$4.00"
                  },
                  {
                    "itemName": "grapes",
                    "itemSerialNumber": "0000000000",
                    "itemPrice": "$1.00"
                  }
                ]""";

        // Get the actual string
        String actual = fileManager.saveToJSONFile(jsonString, file);

        // Check that the two are equal
        assertEquals(expected, actual);

    }

    @Test
    public void fromJSON() {
        // Create objects, files, and lists
        FileManager fileManager = new FileManager();
        File file = new File("JsonInput.json");
        List<InventoryItem> expected = new ArrayList<>();
        List<InventoryItem> actual = new ArrayList<>();

        // Fill the expected list with information
        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("Couches");
        itemOne.setItemPrice("$10,851.00");
        itemOne.setItemSerialNumber("1526CO15KL");

        InventoryItem itemTwo = new InventoryItem();
        itemTwo.setItemName("Dining Table");
        itemTwo.setItemPrice("$567.51");
        itemTwo.setItemSerialNumber("DT1515H7F4");

        InventoryItem itemThree = new InventoryItem();
        itemThree.setItemName("Bed");
        itemThree.setItemPrice("$710.00");
        itemThree.setItemSerialNumber("BD17SJ6N89");

        expected.add(itemOne);
        expected.add(itemTwo);
        expected.add(itemThree);

        // Get the actual list
        actual = fileManager.fromJSON(file);

        // Check that the two are equal
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).itemName, actual.get(i).itemName);
            assertEquals(expected.get(i).itemPrice, actual.get(i).itemPrice);
            assertEquals(expected.get(i).itemSerialNumber, actual.get(i).itemSerialNumber);
        }
    }

    @Test
    public void addToObservableList() {
        // Create objects and lists
        FileManager fileManager = new FileManager();
        ArrayList<String> items = new ArrayList<>();
        ObservableList<InventoryItem> result;
        ObservableList<InventoryItem> expected = FXCollections.observableArrayList();

        // Add information to the lists
        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        InventoryItem itemTwo = new InventoryItem();
        itemTwo.setItemName("pears");
        itemTwo.setItemPrice("$4.00");
        itemTwo.setItemSerialNumber("01234ASDFG");

        InventoryItem itemThree = new InventoryItem();
        itemThree.setItemName("grapes");
        itemThree.setItemPrice("$1.00");
        itemThree.setItemSerialNumber("0000000000");

        items.add("apples\t0123456789\t$3.00");
        items.add("pears\t01234ASDFG\t$4.00");
        items.add("grapes\t0000000000\t$1.00");

        // Get the expected results
        expected.addAll(itemOne, itemTwo, itemThree);
        result = fileManager.addToObservableList(items);

        // Check that the results are equal
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).itemName, result.get(i).itemName);
            assertEquals(expected.get(i).itemPrice, result.get(i).itemPrice);
            assertEquals(expected.get(i).itemSerialNumber, result.get(i).itemSerialNumber);
        }
    }

    @Test
    public void addToSetTest() {
        // Create FileManager object
        FileManager fileManager = new FileManager();
        ObservableList<InventoryItem> inventoryItems = FXCollections.observableArrayList();
        Set<String> expected = new HashSet<>();
        Set<String> actual = new HashSet<>();

        // Add information to the lists
        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        InventoryItem itemTwo = new InventoryItem();
        itemTwo.setItemName("pears");
        itemTwo.setItemPrice("$4.00");
        itemTwo.setItemSerialNumber("01234ASDFG");

        InventoryItem itemThree = new InventoryItem();
        itemThree.setItemName("grapes");
        itemThree.setItemPrice("$1.00");
        itemThree.setItemSerialNumber("0000000000");

        inventoryItems.addAll(itemOne, itemTwo, itemThree);

        // Create the expected set
        expected.add("0123456789");
        expected.add("01234ASDFG");
        expected.add("0000000000");

        // Get the actual set
        actual = fileManager.addToSet(inventoryItems);

        // Check that the sets are equal
        assertEquals(expected, actual);
    }

    @Test
    public void loadFileStrings() {
        // Create FileManager object, file, and ArrayLists
        FileManager fileManager = new FileManager();
        File file = new File("InventoryTrackerTextInput.txt");

        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> actual;

        // Fill in the expected information
        expected.add("Apples\t01A45JH4OP\t$3.29\t");
        expected.add("Pears\t1011UY78L6\t$4.79\t");
        expected.add("Grapes\tQQ8O5TY961\t$5.66\t");

        // Get the actual information
        actual = fileManager.loadFileStrings(file);

        // Check that the actual and expected are equal
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void parseStrings() {

        // Create the FileManager object
        FileManager fileManager = new FileManager();

        // Get the string and the expected string, then parse
        String parseString = "test\ttestOne\ttestTwo\t";

        String[] expected = {"test", "testOne", "testTwo"};
        String[] actual = fileManager.parseStrings(parseString);

        // Check that the expected string is equal to the parsed string
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void loadHTML() {

        // Create FileManager object, file, and ArrayLists
        FileManager fileManager = new FileManager();
        File file = new File("HTMLInventoryTrackerInput.html");

        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> actual;

        // Fill in the expected information
        expected.add("Hammers&emsp;H45O41K69J&emsp;$34.17&emsp;<br>");
        expected.add("Saws&emsp;S14YHG521I&emsp;$15.16&emsp;<br>");
        expected.add("Monkey Wrenches&emsp;MW1515F8G4&emsp;$24.10&emsp;<br>");

        // Get the actual information
        actual = fileManager.loadHTML(file);

        // Check that the actual and expected are equal
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void parseHTMLStrings() {

        // Create the FileManager object
        FileManager fileManager = new FileManager();

        // Get the string and the expected string, then parse
        String parseString = "test&emsp;testOne&emsp;testTwo&emsp;";

        String[] expected = {"test", "testOne", "testTwo"};
        String[] actual = fileManager.parseHTMLStrings(parseString);

        // Check that the expected string is equal to the parsed string
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void addToObservableListHTML() {

        // Create objects and lists
        FileManager fileManager = new FileManager();
        ArrayList<String> items = new ArrayList<>();
        ObservableList<InventoryItem> result;
        ObservableList<InventoryItem> expected = FXCollections.observableArrayList();

        // Add information to the lists
        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        InventoryItem itemTwo = new InventoryItem();
        itemTwo.setItemName("pears");
        itemTwo.setItemPrice("$4.00");
        itemTwo.setItemSerialNumber("01234ASDFG");

        InventoryItem itemThree = new InventoryItem();
        itemThree.setItemName("grapes");
        itemThree.setItemPrice("$1.00");
        itemThree.setItemSerialNumber("0000000000");

        items.add("apples&emsp;0123456789&emsp;$3.00");
        items.add("pears&emsp;01234ASDFG&emsp;$4.00");
        items.add("grapes&emsp;0000000000&emsp;$1.00");

        // Get the expected results
        expected.addAll(itemOne, itemTwo, itemThree);
        result = fileManager.addToObservableListHTML(items);

        // Check that the results are equal
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).itemName, result.get(i).itemName);
            assertEquals(expected.get(i).itemPrice, result.get(i).itemPrice);
            assertEquals(expected.get(i).itemSerialNumber, result.get(i).itemSerialNumber);
        }
    }

    @Test
    public void observableToList() {
        // Create objects and lists
        FileManager fileManager = new FileManager();
        ObservableList<InventoryItem> inventoryList = FXCollections.observableArrayList();
        List<InventoryItem> expected = new ArrayList<>();
        List<InventoryItem> actual = new ArrayList<>();

        // Add information to the ObservableList
        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        InventoryItem itemTwo = new InventoryItem();
        itemTwo.setItemName("pears");
        itemTwo.setItemPrice("$4.00");
        itemTwo.setItemSerialNumber("01234ASDFG");

        InventoryItem itemThree = new InventoryItem();
        itemThree.setItemName("grapes");
        itemThree.setItemPrice("$1.00");
        itemThree.setItemSerialNumber("0000000000");

        inventoryList.addAll(itemOne, itemTwo, itemThree);

        // Add information to the expected List
        expected.add(itemOne);
        expected.add(itemTwo);
        expected.add(itemThree);

        // Create the actual List
        actual = fileManager.observableToList(inventoryList);

        // Check that the two lists are equal
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).itemName, actual.get(i).itemName);
            assertEquals(expected.get(i).itemSerialNumber, actual.get(i).itemSerialNumber);
            assertEquals(expected.get(i).itemPrice, actual.get(i).itemPrice);
        }
    }

    @Test
    public void saveToTXT() {

        // Create FileManager, file, and List
        FileManager fileManager = new FileManager();
        File file = new File("InventoryTrackerTextInputTest.txt");
        List<InventoryItem> inventoryItems = new ArrayList<>();

        // Add information to the List
        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        InventoryItem itemTwo = new InventoryItem();
        itemTwo.setItemName("pears");
        itemTwo.setItemPrice("$4.00");
        itemTwo.setItemSerialNumber("01234ASDFG");

        InventoryItem itemThree = new InventoryItem();
        itemThree.setItemName("grapes");
        itemThree.setItemPrice("$1.00");
        itemThree.setItemSerialNumber("0000000000");

        inventoryItems.add(itemOne);
        inventoryItems.add(itemTwo);
        inventoryItems.add(itemThree);

        // Get the expected string
        String expected = """
                apples\t0123456789\t$3.00\t
                pears\t01234ASDFG\t$4.00\t
                grapes\t0000000000\t$1.00\t
                """;

        // Create the actual string that is saved
        String actual = fileManager.saveToTXT(file, inventoryItems);

        // Check that the two are equal
        assertEquals(expected, actual);
    }

    @Test
    public void generateHeader() {

        // Create the FileManager, a file, and the List
        FileManager fileManager = new FileManager();
        File file = new File("HTMLInventoryTrackerInputTest.html");
        List<InventoryItem> inventoryItems = new ArrayList<>();

        // Add information to the List
        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        InventoryItem itemTwo = new InventoryItem();
        itemTwo.setItemName("pears");
        itemTwo.setItemPrice("$4.00");
        itemTwo.setItemSerialNumber("01234ASDFG");

        InventoryItem itemThree = new InventoryItem();
        itemThree.setItemName("grapes");
        itemThree.setItemPrice("$1.00");
        itemThree.setItemSerialNumber("0000000000");

        inventoryItems.add(itemOne);
        inventoryItems.add(itemTwo);
        inventoryItems.add(itemThree);

        // Get the expected string
        String expected = """
                <html>
                \t<head>
                \t\t<meta content = "HTMLInventoryTrackerInputTest.html">
                \t\t<title>
                \t\t\tInventory Tracker
                \t\t</title>
                \t</head>
                \t<body>
                apples&emsp;0123456789&emsp;$3.00&emsp;<br>
                pears&emsp;01234ASDFG&emsp;$4.00&emsp;<br>
                grapes&emsp;0000000000&emsp;$1.00&emsp;<br>
                \t</body>
                </html>""";

        // Get the actual string
        String actual = fileManager.generateHeader(file, inventoryItems);

        // Check that the two are equal
        assertEquals(expected, actual);
    }

    @Test
    public void generateHead() {
        // Create the FileManager object and the file
        FileManager fileManager = new FileManager();
        File file = new File("HTMLInventoryTrackerInputTest.html");

        // Get the expected and actual strings
        String expected = """
                \t<head>
                \t\t<meta content = "HTMLInventoryTrackerInputTest.html">
                \t\t<title>
                \t\t\tInventory Tracker
                \t\t</title>
                \t</head>
                """;

        String actual = fileManager.generateHead(file);

        // Check that they are equal
        assertEquals(expected, actual);
    }

    @Test
    public void generateBody() {

        // Create the FileManager object and a List
        FileManager fileManager = new FileManager();
        List<InventoryItem> inventoryItems = new ArrayList<>();

        // Get the expected String
        String expected = """
                \t<body>
                apples&emsp;0123456789&emsp;$3.00&emsp;<br>
                pears&emsp;01234ASDFG&emsp;$4.00&emsp;<br>
                grapes&emsp;0000000000&emsp;$1.00&emsp;<br>
                \t</body>
                """;

        String actual;

        // Add information to the List
        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        InventoryItem itemTwo = new InventoryItem();
        itemTwo.setItemName("pears");
        itemTwo.setItemPrice("$4.00");
        itemTwo.setItemSerialNumber("01234ASDFG");

        InventoryItem itemThree = new InventoryItem();
        itemThree.setItemName("grapes");
        itemThree.setItemPrice("$1.00");
        itemThree.setItemSerialNumber("0000000000");

        inventoryItems.add(itemOne);
        inventoryItems.add(itemTwo);
        inventoryItems.add(itemThree);

        // Get the actual string
        actual = fileManager.generateBody(inventoryItems);

        // Check that the two strings are equal
        assertEquals(expected, actual);
    }

    @Test
    public void writeToHTMLFile() {

        // Create the FileManager and a file
        FileManager fileManager = new FileManager();
        File file = new File("HTMLInventoryTrackerInputTest.html");

        // Create input to put into the method
        String input = """
                <html>
                \t<head>
                \t\t<meta content = "HTMLInventoryTrackerInputTest.html">
                \t\t<title>
                \t\t\tInventory Tracker
                \t\t</title>
                \t</head>
                \t<body>
                apples&emsp;0123456789&emsp;$3.00&emsp;<br>
                pears&emsp;01234ASDFG&emsp;$4.00&emsp;<br>
                grapes&emsp;0000000000&emsp;$1.00&emsp;<br>
                \t</body>
                </html>""";

        // Get the expected string
        String expected = """
                <html>
                \t<head>
                \t\t<meta content = "HTMLInventoryTrackerInputTest.html">
                \t\t<title>
                \t\t\tInventory Tracker
                \t\t</title>
                \t</head>
                \t<body>
                apples&emsp;0123456789&emsp;$3.00&emsp;<br>
                pears&emsp;01234ASDFG&emsp;$4.00&emsp;<br>
                grapes&emsp;0000000000&emsp;$1.00&emsp;<br>
                \t</body>
                </html>""";

        // Get the actual string
        String actual = fileManager.writeToHTMLFile(file, input);

        // Check that the two are equal
        assertEquals(expected, actual);
    }
}