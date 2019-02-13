package com.example.mylittleshop.entity;

public class Inventory {

    private InventoryID id;
    private int quantity;
    public Inventory(){}
    public Inventory(InventoryID id,int quantity){
        this.id = id;
        this.quantity=quantity;
    }
    public InventoryID getId() {
        return this.id;
    }
    public void setId(InventoryID id) {
        this.id = id;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
