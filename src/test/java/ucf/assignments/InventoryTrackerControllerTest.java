/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Savannah Osburn
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTrackerControllerTest {

    @Test
    public void alreadyInSetTest() {

        // Create controller object
        InventoryTrackerController itc = new InventoryTrackerController();

        // Create an ObservableList and a Set, and add information to them
        ObservableList<InventoryItem> allItems = FXCollections.observableArrayList();

        Set<String> testSet = new HashSet<>();
        testSet.add("0123456789");
        testSet.add("01234ASDFG");
        testSet.add("0000000000");

        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        InventoryItem itemTwo = new InventoryItem();
        itemTwo.setItemName("pears");
        itemTwo.setItemPrice("4.00");
        itemTwo.setItemSerialNumber("01234ASDFG");

        InventoryItem itemThree = new InventoryItem();
        itemThree.setItemName("grapes");
        itemThree.setItemPrice("1.00");
        itemThree.setItemSerialNumber("0000000000");

        allItems.addAll(itemOne, itemTwo, itemThree);

        // Check for true cases first because these shouldn't add anything to the set
        assertTrue(itc.alreadyInSet(testSet, "0000000000", allItems));
        assertTrue(itc.alreadyInSet(testSet, "01234ASDFG", allItems));
        assertTrue(itc.alreadyInSet(testSet, "0123456789", allItems));

        // Check a false statement
        assertFalse(itc.alreadyInSet(testSet, "aaa000aaa0", allItems));
    }

    @Test
    public void isCorrectNameLengthTest() {

        // Create controller object
        InventoryTrackerController itc = new InventoryTrackerController();

        for (int i = 1; i >= Integer.MIN_VALUE + 1 && i <= 1; i--) {
            assertFalse(itc.isCorrectNameLength(i));
        }

        // Test all the numbers
        for (int i = 2; i >= 2 && i <= 256; i++) {
            assertTrue(itc.isCorrectNameLength(i));
        }

        for (int i = 257; i > 256 && i <= Integer.MAX_VALUE - 1; i++) {
            assertFalse(itc.isCorrectNameLength(i));
        }
    }

    @Test
    public void isCorrectSerialNumberFormatTest() {
        // Create controller object
        InventoryTrackerController itc = new InventoryTrackerController();

        // Create sample strings
        String[] expected = {"aaaaaaaaaa", "0000000000", "0a0a0a0a0a", "a-a!j,k=p10", "-=[])*^%$@"};

        // Check the string formats
        for (int i = 0; i < 3; i++) {
            assertTrue(itc.isCorrectSerialNumberFormat(expected[i]));
        }

        for (int i = 3; i < 5; i++) {
            assertFalse(itc.isCorrectSerialNumberFormat(expected[i]));
        }
    }

    @Test
    public void isCorrectPriceFormatTest() {
        // Create controller object
        InventoryTrackerController itc = new InventoryTrackerController();

        // Complete tests
        assertTrue(itc.isCorrectPriceFormat("10"));
        assertTrue(itc.isCorrectPriceFormat("4.31"));
        assertFalse(itc.isCorrectPriceFormat("a"));
        assertFalse(itc.isCorrectPriceFormat("-!"));
        assertFalse(itc.isCorrectPriceFormat("$4.99"));
        assertFalse(itc.isCorrectPriceFormat("b.sa"));
    }

    @Test
    public void setItemsTest() {

        // Create controller object
        InventoryTrackerController itc = new InventoryTrackerController();

        // Create an ObservableList and add information
        ObservableList<InventoryItem> allItems = FXCollections.observableArrayList();
        ObservableList<InventoryItem> testItems = FXCollections.observableArrayList();

        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");
        allItems.add(itemOne);

        itc.setItems(testItems, "apples", "0123456789", "$3.00");

        // Assert that both lists are equal
        assertEquals(allItems.get(0).itemName, testItems.get(0).itemName);
        assertEquals(allItems.get(0).itemPrice, testItems.get(0).itemPrice);
        assertEquals(allItems.get(0).itemSerialNumber, testItems.get(0).itemSerialNumber);
    }

    @Test
    public void priceFormatTest() {
        // Create controller object
        InventoryTrackerController itc = new InventoryTrackerController();

        String expected = "$4.00";

        assertEquals(expected, itc.priceFormat(4.00));
    }

    @Test
    public void deleteItemTest() {

        // Create controller object
        InventoryTrackerController itc = new InventoryTrackerController();

        // Create an ObservableList and a Set, and add information to them
        ObservableList<InventoryItem> expected = FXCollections.observableArrayList();
        ObservableList<InventoryItem> actual = FXCollections.observableArrayList();

        Set<String> testSet = new HashSet<>();
        testSet.add("0123456789");
        testSet.add("01234ASDFG");
        testSet.add("0000000000");

        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        InventoryItem itemTwo = new InventoryItem();
        itemTwo.setItemName("pears");
        itemTwo.setItemPrice("4.00");
        itemTwo.setItemSerialNumber("01234ASDFG");

        InventoryItem itemThree = new InventoryItem();
        itemThree.setItemName("grapes");
        itemThree.setItemPrice("1.00");
        itemThree.setItemSerialNumber("0000000000");

        actual.addAll(itemOne, itemTwo, itemThree);
        expected.addAll(itemOne, itemThree);

        actual = itc.deleteItem(itemTwo, actual, testSet);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).itemName, actual.get(i).itemName);
            assertEquals(expected.get(i).itemPrice, actual.get(i).itemPrice);
            assertEquals(expected.get(i).itemSerialNumber, actual.get(i).itemSerialNumber);
        }
    }

    @Test
    public void setToNullTest() {

        // Create controller object
        InventoryTrackerController itc = new InventoryTrackerController();

        // Create an item
        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        itemOne = itc.setToNull(itemOne);

        assertNull(itemOne.itemName);
        assertNull(itemOne.itemPrice);
        assertNull(itemOne.itemSerialNumber);
    }

    @Test
    public void searchFindsItemTest() {

        // Create controller object
        InventoryTrackerController itc = new InventoryTrackerController();

        // Create a test item
        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        // Test the method
        assertTrue(itc.searchFindsItem(itemOne, "app"));
        assertTrue(itc.searchFindsItem(itemOne, "3"));
        assertTrue(itc.searchFindsItem(itemOne, "0123456789"));
        assertFalse(itc.searchFindsItem(itemOne, "hi"));
    }

    @Test
    public void filterListTest() {
        // Create controller object
        InventoryTrackerController itc = new InventoryTrackerController();

        // Create an ObservableList and a Set, and add information to them
        ObservableList<InventoryItem> filtered = FXCollections.observableArrayList();
        ObservableList<InventoryItem> actual = FXCollections.observableArrayList();

        InventoryItem itemOne = new InventoryItem();
        itemOne.setItemName("apples");
        itemOne.setItemPrice("$3.00");
        itemOne.setItemSerialNumber("0123456789");

        InventoryItem itemTwo = new InventoryItem();
        itemTwo.setItemName("pears");
        itemTwo.setItemPrice("4.00");
        itemTwo.setItemSerialNumber("01234ASDFG");

        InventoryItem itemThree = new InventoryItem();
        itemThree.setItemName("grapes");
        itemThree.setItemPrice("1.00");
        itemThree.setItemSerialNumber("0000000000");

        filtered.add(itemThree);
        actual.addAll(itemOne, itemTwo, itemThree);

        // Filter the list
        actual = itc.filterList(actual, "gra");

        // Check that the lists are the same
        for (int i = 0; i < filtered.size(); i++) {
            assertEquals(filtered.get(i).itemName, actual.get(i).itemName);
            assertEquals(filtered.get(i).itemSerialNumber, actual.get(i).itemSerialNumber);
            assertEquals(filtered.get(i).itemPrice, actual.get(i).itemPrice);
        }
    }

    @Test
    public void sortByName() {
        // nothing to test since tableview handles sorting
    }

    @Test
    public void sortBySerialNumber() {
        // nothing to test since tableview handles sorting
    }

    @Test
    public void sortByPrice() {
        // Nothing to test since tableview handles sorting
    }

    @Test
    public void editName() {
        // Editing is handled by the initialize fxml function when the table columns are initialized and set to editable
        // Uses isCorrectNameLength, which was already successfully tested
    }

    @Test
    public void editSerialNumber() {
        // Editing is handled by the initialize fxml function when the table columns are initialized and set to editable
        // Uses isCorrectSerialNumberLength, isCorrectSerialNumberFormat, and alreadyInSet
        //   which were already successfully tested
    }

    @Test
    public void editPrice() {
        // Editing is handled by the initialize fxml function when the table columns are initialized and set to editable
        // Uses isCorrectPriceFormat, which was already successfully tested
    }
}