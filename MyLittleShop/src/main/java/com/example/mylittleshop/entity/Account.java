package com.example.mylittleshop.entity;

import javax.persistence.*;

@Entity
@Table(name="emp")
public class Account {
    @Id
    @Column(name = "emp_username",length = 20,nullable = false)
    private String username;
    @Column(name = "emp_name",length = 20,nullable = false)
    private String name;
    @Column(name = "emp_password", length = 70, nullable = false)
    private String password;
    @Column(name = "active", length = 1, nullable = false)
    private boolean active;
    @Column(name = "role",length = 20,nullable = false)
    private String role;

    public String getUsername(){
        return username;
    }

    public void setUsername(String name){
        this.username = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRole(String role ){
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    @Override
    public String toString()  {
        return "["+ this.name+","+ this.password+","+role+"]";
    }
}
