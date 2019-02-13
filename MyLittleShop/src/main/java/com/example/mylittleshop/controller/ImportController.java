package com.example.mylittleshop.controller;

import com.example.mylittleshop.entity.Import;
import com.example.mylittleshop.entity.Sale;
import com.example.mylittleshop.entity.Shop;
import com.example.mylittleshop.repository.ImportRepository;
import com.example.mylittleshop.repository.SaleRepository;
import com.example.mylittleshop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    @Autowired
    private ImportRepository importRepository;

    @Autowired
    private ShopRepository shopRepository;

    @GetMapping("/all")
    public Iterable<Import> getAllSales(){
        return importRepository.findAll();
    }

    @RequestMapping(value = "shop/{id}", method = RequestMethod.GET)
    public List<Import> shop(@PathVariable("id") Long id, Model model) {
        Shop shop = shopRepository.findById(id).get();
        return importRepository.findByShop(shop);
    }


}
