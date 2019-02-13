package com.example.mylittleshop.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="emp_shop")
public class Employee implements Serializable {
    @Id
    private String emp_username;

    @OneToOne
    @JoinColumn(name = "emp_username",nullable = false,foreignKey = @ForeignKey(name = "EMP_SHOP_FK"))
    private Account account;

    @ManyToOne
    @JoinColumn(name="shop_id",nullable = false,foreignKey = @ForeignKey(name = "SHOP_EMP_FK"))
    private Shop shop;

    public Employee(){}

    public Employee(Account account, Shop shop){
        this.account = account;
        this.shop = shop;
    }

    public String getUsername() {
        return emp_username;
    }

    public void setUsername(String emp_username) {
        this.emp_username = emp_username;
    }

    public Account getAccount(){
        return account;
    }

    public void setAccount(Account account){
        this.account = account;
    }

    public Shop getShop(){
        return this.shop;
    }

    public void setShop(Shop shop){
        this.shop = shop;
    }

}
