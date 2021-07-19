/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Savannah Osburn
 */

package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTrackerControllerTest {

    @Test
    public void alreadyInSetTest() {

    }

    @Test
    public void isCorrectNameLengthTest() {

        // Create controller object
        InventoryTrackerController itc = new InventoryTrackerController();

        for (int i = 2; i >= Integer.MIN_VALUE + 1 && i <= 2; i--) {
            assertFalse(itc.isCorrectNameLength(i));
        }

        // Test all the numbers
        for (int i = 3; i > 2 && i < 256; i++) {
            assertTrue(itc.isCorrectNameLength(i));
        }

        for (int i = 256; i >= 256 && i <= Integer.MAX_VALUE - 1; i++) {
            assertFalse(itc.isCorrectNameLength(i));
        }
    }

    @Test
    public void isCorrectSerialNumberFormatTest() {

    }

    @Test
    public void isCorrectPriceFormatTest() {


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
    public void isDoubleTest() {

        // Create controller object
        InventoryTrackerController itc = new InventoryTrackerController();

        // Create strings to test
        String[] testStrings = {"15.0", "15", "hello", "hello 15.0"};

        // Determine if they are properly sorted as doubles
        assertTrue(itc.isDouble(testStrings[0]));
        assertTrue(itc.isDouble(testStrings[1]));
        assertFalse(itc.isDouble(testStrings[2]));
        assertFalse(itc.isDouble(testStrings[3]));
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