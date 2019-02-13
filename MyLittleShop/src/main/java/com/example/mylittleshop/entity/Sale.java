package com.example.mylittleshop.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name="sale")
public class Sale {
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="shop_id",nullable = false,foreignKey = @ForeignKey(name = "SALE_SHOP_FK"))
    private Shop shop;

    @ManyToOne
    @JoinColumn(name="barcode",nullable = false,foreignKey = @ForeignKey(name = "SALE_ITEM_FK"))
    private Item item;

    @ManyToOne
    @JoinColumn(name = "emp_username",nullable = false,foreignKey = @ForeignKey(name="SALE_EMP_FK"))
    private Account account;

    @Temporal(TemporalType.DATE)
    @Column(name = "ex_date",columnDefinition = "DATE",nullable = false)
    private Date exp_date;

    @Column(name= "quantity",nullable = false)
    private int quantity;

    public long getId() {
        return id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getExp_date() {
        return exp_date;
    }

    public void setExp_date(Date exp_date) {
        this.exp_date = exp_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
