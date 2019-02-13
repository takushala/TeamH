package com.example.mylittleshop.service;

import com.example.mylittleshop.entity.*;

import com.example.mylittleshop.repository.ImportRepository;
import com.example.mylittleshop.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class InventoryService {

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    ImportRepository importRepository;


    public List<Inventory> getAll(){
        List<Inventory> inventories = new ArrayList<Inventory>();
        HashMap<InventoryID,Integer> map = new HashMap<InventoryID,Integer>();
        List<Sale> sales = saleRepository.findAll();
        List<Import> imports =importRepository.findAll();

        for ( Import i : imports){
            InventoryID id = new InventoryID(i.getShop(),i.getItem());
            if(map.containsKey(id)){
                map.put(id,i.getQuantity()+map.get(id));
            }
            else{
                map.put(id,i.getQuantity());
            }
        }

        for ( Sale s : sales){
            InventoryID id = new InventoryID(s.getShop(),s.getItem());
            map.put(id,map.get(id)-s.getQuantity());
        }

        map.forEach((k, v) -> inventories.add(new Inventory(k,v)));

        return inventories;
    }

    public List<Inventory> findByShop(Shop shop){
        List<Inventory> inventories = new ArrayList<Inventory>();
        HashMap<InventoryID,Integer> map = new HashMap<InventoryID,Integer>();
        List<Sale> sales = saleRepository.findByShop(shop);
        List<Import> imports =importRepository.findByShop(shop);

        for ( Import i : imports){
            InventoryID id = new InventoryID(i.getShop(),i.getItem());
            if(map.containsKey(id)){
                map.put(id,i.getQuantity()+map.get(id));
            }
            else{
                map.put(id,i.getQuantity());
            }
        }

        for ( Sale s : sales){
            InventoryID id = new InventoryID(s.getShop(),s.getItem());
            map.put(id,map.get(id)-s.getQuantity());
        }

        map.forEach((k, v) -> inventories.add(new Inventory(k,v)));

        return inventories;
    }
}
