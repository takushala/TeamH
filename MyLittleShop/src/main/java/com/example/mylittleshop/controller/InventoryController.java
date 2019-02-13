package com.example.mylittleshop.controller;


import com.example.mylittleshop.entity.*;
import com.example.mylittleshop.repository.*;
import com.example.mylittleshop.service.InventoryService;
import com.example.mylittleshop.support.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ImportRepository importRepository;
    @Autowired
    InventoryService inventoryService;



    //Get all inventories
    @GetMapping("/all")
    @ResponseBody
    public Iterable<Inventory> getAllInventories(){
        return inventoryService.getAll();
    }

    @PostMapping("/{id}")
    public String create(@PathVariable("id") Long id, @RequestParam Map<String,String> map, RedirectAttributes redirectAttributes, Authentication authentication) {
        Shop shop = shopRepository.findById(id).get();
        Item item = itemRepository.findById(map.get("barcode")).get();
        Account account = accountRepository.findByUsername(authentication.getName());
        int quantity = Integer.parseInt(map.get("quantity"));
        Import imp = new Import();
        imp.setItem(item);
        imp.setShop(shop);
        imp.setQuantity(quantity);
        imp.setImp_date(new Date());
        imp.setAccount(account);
        importRepository.save(imp);
        redirectAttributes.addFlashAttribute("message",new Message("Add to inventory success",Message.Type.SUCCESS));
        boolean admin = false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ROLE_MANAGER".equals(auth.getAuthority())){
                admin = true;
            }
        }
        if(admin)
        return "redirect:/admin/shops/"+id;
        else return "redirect:/emp/report";
    }




}
