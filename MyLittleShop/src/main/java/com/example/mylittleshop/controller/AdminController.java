package com.example.mylittleshop.controller;
import com.example.mylittleshop.entity.*;
import com.example.mylittleshop.repository.*;
import com.example.mylittleshop.service.InventoryService;
import com.example.mylittleshop.support.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@Secured("ROLE_MANAGER")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SaleRepository saleRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    InventoryService inventoryService;
    @Autowired
    ImportRepository importRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String shops(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("shops", shopRepository.findAll());
        model.addAttribute("availableAccount",accountRepository.findAvailableAccount("ROLE_EMPLOYEE"));
        return "admin.html";
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "account.html";
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public String tasks(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "item_list.html";
    }

    @RequestMapping(value = "/shops/{id}", method = RequestMethod.GET)
    public String shop(@PathVariable("id") Long id, Model model) {
        Shop shop = shopRepository.findById(id).get();
        List<Employee> employees = employeeRepository.findByShop(shop);
        List<Sale> sales = saleRepository.findByShop(shop);
        List<Inventory> inventories = inventoryService.findByShop(shop);
        List<Import> imports = importRepository.findByShop(shop);
        List<Item> items =itemRepository.findAll();
        model.addAttribute("items",items);
        model.addAttribute("shop_id",id);
        model.addAttribute("employees", employees);
        model.addAttribute("sales", sales);
        model.addAttribute("inventories", inventories);
        model.addAttribute("imports", imports);
        return "shop.html";
    }

    @RequestMapping(value = "password", method = RequestMethod.GET)
    public String password(){
        return "password.html";
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    String changePassword(@RequestParam Map<String, String> password, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String old_password = accountRepository.findByUsername(username).getPassword();
        if (!passwordEncoder.matches(password.get("old_password"), old_password)) {
            redirectAttributes.addFlashAttribute("message", new Message("Wrong old password!", Message.Type.DANGER));
            return "redirect:/admin/password";
        }

        if (password.get("new_password").equals(password.get("confirm_password"))) {

            Account account = accountRepository.findByUsername(username);
            account.setPassword(passwordEncoder.encode(password.get("new_password")));
            accountRepository.save(account);
            redirectAttributes.addFlashAttribute("message", new Message("Success", Message.Type.DANGER));
            return "redirect:/admin/password";
        } else {
            redirectAttributes.addFlashAttribute("message", new Message("Passwords not match", Message.Type.DANGER));
            return "redirect:/admin/password";
        }
    }

}
