/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Savannah Osburn
 */

package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class InventoryItem {

    String itemName;
    String itemSerialNumber;
    String itemPrice;

    public InventoryItem(String itemName, String itemSerialNumber, String itemPrice) {
        this.itemName = itemName;
        this.itemSerialNumber = itemSerialNumber;
        this.itemPrice = itemPrice;
    }

    public InventoryItem() {

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSerialNumber() {
        return itemSerialNumber;
    }

    public void setItemSerialNumber(String itemSerialNumber) {
        this.itemSerialNumber = itemSerialNumber;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

        /*
        private final StringProperty itemName = new SimpleStringProperty();
        private final StringProperty itemSerialNumber = new SimpleStringProperty();
        private BigDecimal itemPrice;


        public InventoryItem(BigDecimal itemPrice) {
                this.itemPrice = itemPrice;
        }

        public void setItemPrice(BigDecimal itemPrice) {
                this.itemPrice = itemPrice;
        }

        public final String getItemName() {
                return itemName.get();
        }

        public final void setItemName(String name) {
                itemName.set(name);
        }

        public StringProperty itemNameProperty() {
                return itemName;
        }

        public String getItemSerialNumber() {
                return itemSerialNumber.get();
        }

        public StringProperty itemSerialNumberProperty() {
                return itemSerialNumber;
        }

        public void setItemSerialNumber(String itemSerialNumber) {
                this.itemSerialNumber.set(itemSerialNumber);
        }

        public BigDecimal getItemPrice() {
                return itemPrice;
        } */
}
