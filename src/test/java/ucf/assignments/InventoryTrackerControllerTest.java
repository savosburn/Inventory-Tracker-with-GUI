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

        for (int i = 0; i > Integer.MIN_VALUE + 1 && i < Integer.MAX_VALUE - 1; i++) {
            assertTrue(itc.isCorrectPriceFormat(Integer.toString(i)));
        }

        //assertTrue(itc.isCorrectPriceFormat("4.31"));
        assertFalse(itc.isCorrectPriceFormat("a"));
        assertFalse(itc.isCorrectPriceFormat("-!"));
        assertFalse(itc.isCorrectPriceFormat("$4.99"));
        assertFalse(itc.isCorrectPriceFormat("b.sa"));


    }

    @Test
    public void setItemsTest() {

    }

    @Test
    public void priceFormatTest() {


    }

    @Test
    public void deleteItemTest() {


    }

    @Test
    public void setToNullTest() {


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

    // TODO MAYBE DO THE SAME FOR EDITING, BUT FIRST SEE IF EDITING CAN
    //      TODO BE MADE INTO TESTABLE FUNCTIONS

}