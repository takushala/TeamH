package com.example.mylittleshop.controller;

import com.example.mylittleshop.entity.Employee;
import com.example.mylittleshop.entity.Shop;
import com.example.mylittleshop.repository.EmployeeRepository;
import com.example.mylittleshop.repository.ShopRepository;
import com.example.mylittleshop.support.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    private ShopRepository shopRepository;

    @GetMapping("/all")
    @ResponseBody
    public Iterable<Shop>  getAllShops() {
        return shopRepository.findAll();
    }

    @GetMapping("/{id}")
    public Shop shop(@PathVariable("id") Long id) {
        return shopRepository.findById(id).get();
    }

    @Secured("ROLE_MANAGER")
    @PostMapping("/")
    public String create(@RequestParam(name = "name") String name, RedirectAttributes redirectAttributes ) {
        Shop newShop = new Shop();
        newShop.setName(name);
        shopRepository.save(newShop);
        redirectAttributes.addFlashAttribute("messageshop",new Message("Create new shop with name:\""+name+"\"  success",Message.Type.SUCCESS));

        return "redirect:/admin";
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id, @RequestParam(name = "name") String name, RedirectAttributes redirectAttributes){
        Shop shop = shopRepository.findById(id).get();
        shop.setName(name);
        redirectAttributes.addFlashAttribute("messageshop",new Message("Edit shop ID: "+id+" success",Message.Type.SUCCESS));
        shopRepository.save(shop);
        return "redirect:/admin";
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id,RedirectAttributes redirectAttributes){
        Shop shop = shopRepository.findById(id).get();
        shopRepository.delete(shop);
        redirectAttributes.addFlashAttribute("messageshop",new Message("Delete shop ID: "+id+" success",Message.Type.SUCCESS));
        return "redirect:/admin";
    }

}
