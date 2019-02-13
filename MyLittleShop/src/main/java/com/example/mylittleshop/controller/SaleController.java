package com.example.mylittleshop.controller;

import com.example.mylittleshop.entity.Employee;
import com.example.mylittleshop.entity.Sale;
import com.example.mylittleshop.entity.Shop;
import com.example.mylittleshop.repository.SaleRepository;
import com.example.mylittleshop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
public class SaleController {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ShopRepository shopRepository;

    @GetMapping("/all")
    public Iterable<Sale> getAllSales(){
        return saleRepository.findAll();
    }


    @RequestMapping(value = "shop/{id}", method = RequestMethod.GET)
    public List<Sale> shop(@PathVariable("id") Long id, Model model) {
        Shop shop = shopRepository.findById(id).get();
        return saleRepository.findByShop(shop);
    }
}
