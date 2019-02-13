package com.example.mylittleshop.model;

import com.example.mylittleshop.entity.Employee;
import com.example.mylittleshop.entity.Item;
import java.util.*;

public class Cart {
    private Employee employee;
    private List<CartLine> list ;
    private Date date ;
    private long totalPrice;

    public Cart(){

        list= new ArrayList<CartLine>();
        totalPrice=0;
    }


    public void addItem(Item item) {
        if(list.isEmpty()) {
            list.add(new CartLine(item));
            return;
        }
        for(int i=0;i<this.list.size();i++){
            if(list.get(i).getItem().equals(item)) {
                list.set(i,new CartLine(item,list.get(i).getQuantity()+1));
                return;
            }
        }
        list.add(new CartLine(item));
    }

    public void addItem(Item item,int quantity) {
        if(list.isEmpty()) {
            list.add(new CartLine(item,quantity));
            return;
        }
        for(int i=0;i<this.list.size();i++){
            if(list.get(i).getItem().equals(item)) {
                list.set(i,new CartLine(item,list.get(i).getQuantity()+quantity));
                return;
            }
        }
        list.add(new CartLine(item,quantity));
    }

    public void removeItem(Item item){
        for(int i=0;i<this.list.size();i++){
            if(list.get(i).getItem().equals(item)) list.remove(i);
        }
    }

    public void setQuantity(Item item,int quantity){
        for(int i=0;i<this.list.size();i++){
            if(list.get(i).getItem().equals(item)) list.set(i,new CartLine(item,quantity));
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<CartLine> getList() {
        return list;
    }

    public void setList(List<CartLine> list) {
        this.list = list;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(){
        this.totalPrice=0;
        for(CartLine line : this.list){
            this.totalPrice += (long)line.getQuantity()*(long)line.getItem().getPrice();
        }
    }
}
