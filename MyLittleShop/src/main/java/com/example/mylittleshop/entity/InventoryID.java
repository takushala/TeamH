package com.example.mylittleshop.entity;

import java.util.Objects;

public class InventoryID  {

    private Shop shop;
    private Item item;

    public InventoryID(){}

    public InventoryID(Shop shop,Item item){
        this.shop = shop;
        this.item = item;
    }

    public Shop getShop() {
        return this.shop;
    }

    public Item getItem() {
        return this.item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryID)) return false;
        InventoryID that = (InventoryID) o;
        return Objects.equals(getShop().getId(), that.getShop().getId()) &&
                Objects.equals(getItem().getCode(), that.getItem().getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShop().getId(), getItem().getCode());
    }
}
