package com.example.mylittleshop.controller;

import com.example.mylittleshop.entity.Account;
import com.example.mylittleshop.repository.AccountRepository;
import com.example.mylittleshop.support.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Secured("ROLE_MANAGER")
    @RequestMapping(value = "/create",produces = "application/json",method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createUser ( Account account, RedirectAttributes redirectAttributes) {
         if(accountRepository.existsById(account.getUsername())){
            redirectAttributes.addFlashAttribute(new Message("Username already existed!", Message.Type.DANGER));
            return "redirect:/admin/account";
        }
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
         account.setPassword(passwordEncoder.encode(account.getPassword()));
         System.out.println(account.getPassword());
        redirectAttributes.addFlashAttribute(new Message("Success", Message.Type.SUCCESS));

        accountRepository.save(account);
        return "redirect:/admin/account";
    }

    @Secured("ROLE_MANAGER")
    @PostMapping(path="/edit{username}")
    public String editUser(@PathVariable("username") String username,@RequestParam Map<String,String> map, RedirectAttributes redirectAttributes) {
        Account account = accountRepository.findByUsername(username);
        account.setName(map.get("name"));
        boolean active;
        if(map.get("active").equals("true")) active = true;
        else active =false;
        account.setActive(active);
        account.setRole(map.get("role"));
        redirectAttributes.addFlashAttribute(new Message("Success", Message.Type.SUCCESS));
        accountRepository.save(account);
        return "redirect:/admin/account";
    }

    // Get All shops


    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Account>>  getAllShops() {
        return new ResponseEntity<List<Account>>(accountRepository.findAll(),HttpStatus.OK);
    }

    @GetMapping("/available")
    @ResponseBody
    public List<Account> available(){
        return accountRepository.findAvailableAccount("ROLE_EMPLOYEE");
    }
}