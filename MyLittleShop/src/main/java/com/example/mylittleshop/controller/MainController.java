package com.example.mylittleshop.controller;

import com.example.mylittleshop.repository.AccountRepository;
import com.example.mylittleshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.unbescape.html.HtmlEscape;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ItemRepository itemRepository;


    @GetMapping("/")
    String index(Authentication authentication) {
        if(authentication != null){
            //System.out.println(authentication);
            for (GrantedAuthority auth : authentication.getAuthorities()) {
                if ("ROLE_MANAGER".equals(auth.getAuthority())){
                    return "redirect:/admin";
                }
            }
            return "redirect:/emp";
        }
        else return "redirect:/login";
    }

    @GetMapping("/login")
    String login(){
        return "login";
    }


    @RequestMapping("/error.html")
    public String error(HttpServletRequest request, Model model) {
        model.addAttribute("errorCode", "Error " + request.getAttribute("javax.servlet.error.status_code"));
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("<ul>");
        while (throwable != null) {
            errorMessage.append("<li>").append(HtmlEscape.escapeHtml5(throwable.getMessage())).append("</li>");
            throwable = throwable.getCause();
        }
        errorMessage.append("</ul>");
        model.addAttribute("errorMessage", errorMessage.toString());
        return "error";
    }

    /** Error page. */
    @RequestMapping("/403.html")
    public String forbidden() {
        return "403";
    }

}
