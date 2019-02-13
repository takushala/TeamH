package com.example.mylittleshop.controller;

import com.example.mylittleshop.entity.Account;
import com.example.mylittleshop.entity.Employee;
import com.example.mylittleshop.entity.Shop;
import com.example.mylittleshop.repository.AccountRepository;
import com.example.mylittleshop.repository.EmployeeRepository;
import com.example.mylittleshop.repository.ShopRepository;
import com.example.mylittleshop.support.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/employee")
public class EmployeeAPIController {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ShopRepository shopRepository;

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<List<Employee>>(employeeRepository.findAll(), HttpStatus.OK);
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam Map<String,String> map, RedirectAttributes redirectAttributes){
        Long shop_id = Long.parseLong(map.get("shop_id"));
        Shop shop = shopRepository.findById(shop_id).get();
        Account account = accountRepository.findByUsername(map.get("username"));
        Employee employee = new Employee(account,shop);
        employee.setUsername(account.getUsername());
        employeeRepository.save(employee);
        redirectAttributes.addFlashAttribute("messageemp",new Message("Create employee: "+map.get("username")+" success",Message.Type.SUCCESS));

        return "redirect:/admin";
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public List<Employee> shop(@PathVariable("id") Long id, Model model) {
        Shop shop = shopRepository.findById(id).get();
        return employeeRepository.findByShop(shop);
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping(value = "/edit/{username}", method = RequestMethod.POST)
    public String edit(@PathVariable("username") String username, @RequestParam Map<String,String> map, RedirectAttributes redirectAttributes){
        Account account = accountRepository.findByUsername(username);
        account.setName(map.get("name"));
        Long shop_id = Long.parseLong(map.get("shop_id"));
        Shop shop = shopRepository.findById(shop_id).get();
        Employee employee = employeeRepository.findById(username).get();
        employee.setShop(shop);

        redirectAttributes.addFlashAttribute("messageemp",new Message("Edit employee: "+username+" success",Message.Type.SUCCESS));
        employeeRepository.save(employee);
        accountRepository.save(account);
        return "redirect:/admin";
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping(value = "/delete/{username}", method = RequestMethod.POST)
    public String delete(@PathVariable("username") String username,RedirectAttributes redirectAttributes){
        Employee employee = employeeRepository.findById(username).get();
        employeeRepository.delete(employee);
        redirectAttributes.addFlashAttribute("messageemp",new Message("Delete employee: "+username+" success",Message.Type.SUCCESS));
        return "redirect:/admin";
    }

}
