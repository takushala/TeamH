package com.example.mylittleshop.controller;

import com.example.mylittleshop.entity.*;
import com.example.mylittleshop.model.Cart;
import com.example.mylittleshop.model.Utils;
import com.example.mylittleshop.repository.*;
import com.example.mylittleshop.service.InventoryService;
import com.example.mylittleshop.support.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@Secured("ROLE_EMPLOYEE")
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    ShopRepository shopRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SaleRepository saleRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    InventoryService inventoryService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ImportRepository importRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String employee(Model model,Principal principal,HttpServletRequest request) {
        String username = principal.getName();
        if(!employeeRepository.existsById(username)){
            model.addAttribute("message",new Message("You dont belong to any shop! Contact manager", Message.Type.DANGER));
            return "emp_error.html";
        }
        Employee employee = employeeRepository.findById(username).get();
        Long id = employee.getShop().getId();
        Shop shop = shopRepository.findById(employee.getShop().getId()).get();
        Cart myCart = Utils.getCartInSession(request);
        myCart.setTotalPrice();
        model.addAttribute("shop",shop);
        model.addAttribute("cartTotal",myCart.getTotalPrice());
        model.addAttribute("items",itemRepository.findAll());
        return "employee.html";
    }
    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public String tasks(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "item_list.html";
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String shop( Model model, Principal principal) {
        String username = principal.getName();
        if(!employeeRepository.existsById(username)){
            model.addAttribute("message",new Message("You dont belong to any shop! Contact manager", Message.Type.DANGER));
            return "emp_error.html";
        }
        Employee employee = employeeRepository.findById(username).get();
        Long id = employee.getShop().getId();
        Shop shop = shopRepository.findById(employee.getShop().getId()).get();

        List<Sale> sales = saleRepository.findByShop(shop);
        List<Inventory> inventories = inventoryService.findByShop(shop);
        List<Import> imports = importRepository.findByShop(shop);
        List<Employee> employees = employeeRepository.findByShop(shop);
        List<Item> items =itemRepository.findAll();

        model.addAttribute("items",items);
        model.addAttribute("shop_id",id);
        model.addAttribute("employees", employees);
        model.addAttribute("sales", sales);
        model.addAttribute("inventories", inventories);
        model.addAttribute("imports", imports);

        return "emp_report.html";
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String password(Model model){
        model.addAttribute("role","emp");
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
            return "redirect:/emp/password";
        }

        if (password.get("new_password").equals(password.get("confirm_password"))) {

            Account account = accountRepository.findByUsername(username);
            account.setPassword(passwordEncoder.encode(password.get("new_password")));
            accountRepository.save(account);
            redirectAttributes.addFlashAttribute("message", new Message("Success", Message.Type.DANGER));
            return "redirect:/emp/password";
        } else {
            redirectAttributes.addFlashAttribute("message", new Message("Passwords not match", Message.Type.DANGER));
            return "redirect:/emp/password";
        }
    }


}
