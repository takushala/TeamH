package com.example.mylittleshop.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="import_history")
public class Import {
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="shop_id",nullable = false,foreignKey = @ForeignKey(name = "IMP_SHOP_FK"))
    private Shop shop;

    @ManyToOne
    @JoinColumn(name="barcode",nullable = false,foreignKey = @ForeignKey(name = "IMP_ITEM_FK"))
    private Item item;

    @ManyToOne
    @JoinColumn(name = "emp_username",nullable = false,foreignKey = @ForeignKey(name="IMP_EMP_FK"))
    private Account account;

    @Temporal(TemporalType.DATE)
    @Column(name = "imp_date",columnDefinition = "DATE",nullable = false)
    private Date imp_date;

    @Column(name="quantity",nullable = false)
    private int quantity;

    public Import(){}
    public Import(long id, Shop shop, Account account, Item item, Date date, int quantity){
        this.id = id;
        this.shop = shop;
        this.account = account;
        this.item = item;
        this.imp_date = date;
        this.quantity = quantity;
    }

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

    public Date getImp_date() {
        return imp_date;
    }
    public void setImp_date(Date imp_date) {
        this.imp_date = imp_date;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
