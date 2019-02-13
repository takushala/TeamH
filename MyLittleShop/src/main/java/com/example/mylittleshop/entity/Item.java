package com.example.mylittleshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table (name = "item")
public class Item {

    private String name;
    private String category;
    private String code;
    private int price;

    public Item(){
    }

    public Item (String name , String category, String code, int price){
        this.name = name;
        this.category = category;
        this.code = code;
        this.price = price;
    }

    @Id
    @Column(name="barcode",length = 20,nullable = false)
    public String getCode(){
        return this.code;
    }

    public void setCode(String code){
        this.code = code;
    }

    @Column (name = "item_name", length = 30, nullable = false)
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Column(name="item_category",length = 20,nullable = false)
    public String getCategory(){
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column (name = "price",nullable = false)
    public int getPrice(){
        return this.price;
    }

    public void setPrice(int price){
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(code, item.code);
    }

    @Override
    public int hashCode() {

        return Objects.hash(code);
    }

}
