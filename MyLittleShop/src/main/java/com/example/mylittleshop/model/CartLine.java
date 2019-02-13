package com.example.mylittleshop.model;

import com.example.mylittleshop.entity.Item;

public class CartLine {

    private Item item;
    private int quantity;

    public CartLine(){}

    public CartLine(Item item){
        this.item=item;
        this.quantity=1;
    }
    public CartLine(Item item,int quantity){
        this.item=item;
        this.quantity=quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
