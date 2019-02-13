package com.example.mylittleshop.controller;


import com.example.mylittleshop.entity.*;
import com.example.mylittleshop.model.Cart;
import com.example.mylittleshop.model.CartLine;
import com.example.mylittleshop.model.JsonResult;
import com.example.mylittleshop.model.Utils;
import com.example.mylittleshop.repository.*;
import com.example.mylittleshop.service.InventoryService;
import com.example.mylittleshop.support.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/barcodes")
    @ResponseBody
    public Iterable<String> getAllBarcodes(){

        List<Item> items=  itemRepository.findAll();
        List<String> barcodes = new ArrayList<String>();
        for ( Item item : items){
            barcodes.add(item.getCode());
        }
        return barcodes;
    }


    //Get all items
    @GetMapping("/all")
    @ResponseBody
    public Iterable<Item> getAllItems(){
        return itemRepository.findAll();
    }

    @GetMapping("/")
    @ResponseBody
    public JsonResult getByBarcode(@RequestParam(name = "barcode") String barcode){
        JsonResult result = new JsonResult();
        if(itemRepository.existsById(barcode)){
            result.setStatus("ok");
            result.setData(itemRepository.findById(barcode).get());
        }

        else result.setStatus("fail");
        return result;
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping(value = "create",produces = "application/json",method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String create(Item item , RedirectAttributes redirectAttributes ) {
        if(itemRepository.existsById(item.getCode())){
            redirectAttributes.addFlashAttribute("message",new Message("Barcode already existed", Message.Type.DANGER));
            return "redirect:/admin/items";
        }
        itemRepository.save(item);
        redirectAttributes.addFlashAttribute("message",new Message("Create new item with barcode:\""+item.getCode()+"\"  success",Message.Type.SUCCESS));

        return "redirect:/admin/items";
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping(value = "/edit/{barcode}", method = RequestMethod.POST)
    public String update(@PathVariable("barcode") String barcode, @RequestParam Map<String,String> map, RedirectAttributes redirectAttributes){
        Item item =itemRepository.findById(barcode).get();
        item.setCategory(map.get("category"));
        item.setName(map.get("name"));
        item.setPrice(Integer.parseInt(map.get("price")));
        itemRepository.save(item);
        redirectAttributes.addFlashAttribute("message",new Message("Edit item with barcode: "+barcode+" success",Message.Type.SUCCESS));
        return "redirect:/admin/items";
    }

    @Secured("ROLE_MANAGER")
    @RequestMapping(value = "/delete/{barcode}", method = RequestMethod.POST)
    public String delete(@PathVariable("barcode") String barcode,RedirectAttributes redirectAttributes){
        Item item = itemRepository.findById(barcode).get();
        itemRepository.delete(item);
        redirectAttributes.addFlashAttribute("message",new Message("Delete item with barcode: "+barcode+" success",Message.Type.SUCCESS));
        return "redirect:/admin/items";
    }

    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
    @ResponseBody
    public List<CartLine> shoppingCartHandler(HttpServletRequest request) {
        Cart myCart = Utils.getCartInSession(request);
        return myCart.getList();
    }

    @RequestMapping(value = { "/shoppingCart/edit{barcode}" }, method = RequestMethod.POST)
    public String editCart(@PathVariable("barcode") String barcode,@RequestParam(name="quantity") int quantity, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        Cart myCart = Utils.getCartInSession(request);
        Item item = itemRepository.findById(barcode).get();
        myCart.setQuantity(item,quantity);
        System.out.println(myCart.getList());
        return "redirect:/emp";
    }

    @RequestMapping(value = { "/shoppingCart/delete{barcode}" }, method = RequestMethod.POST)
    public String deleteCart(@PathVariable("barcode") String barcode, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        Cart myCart = Utils.getCartInSession(request);
        Item item = itemRepository.findById(barcode).get();
        myCart.removeItem(item);
        return "redirect:/emp";
    }

    @RequestMapping(value = { "/shoppingCart" },method = RequestMethod.POST)
    public String shoppingCart(HttpServletRequest request, @RequestParam Map<String,String> map) {
        Cart myCart = Utils.getCartInSession(request);
        Item item = itemRepository.findById(map.get("barcode")).get();
        int quantity = Integer.parseInt(map.get("quantity"));
        myCart.addItem(item,quantity);
        return "redirect:/emp";
    }

    @RequestMapping(value = { "/addCart" },produces = "application/json",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult shoppingCartHandler(HttpServletRequest request, @RequestParam(name="barcode") String barcode) {
        JsonResult result = new JsonResult();
        Cart myCart = Utils.getCartInSession(request);
        if(itemRepository.existsById(barcode)){
            Item item = itemRepository.findById(barcode).get();
            myCart.addItem(item);
            result.setStatus("ok");
            result.setData(item);
            return result;
        }
        else return result;
    }

    @RequestMapping(value = { "/shoppingCart/save" },method = RequestMethod.POST)
    public String shoppingCart(HttpServletRequest request, RedirectAttributes redirectAttributes, Principal principal) {

        Cart myCart = Utils.getCartInSession(request);
        String username = principal.getName();
        Employee employee = employeeRepository.findById(username).get();
        Long id = employee.getShop().getId();
        Shop shop = shopRepository.findById(id).get();
        boolean check = true;
        List<Inventory> inventories = inventoryService.findByShop(shop);
        for(CartLine line : myCart.getList()){
            boolean in_inven=false;
            for(Inventory inven : inventories){
                if(inven.getId().getItem().equals(line.getItem())){
                    in_inven = true;
                    if(inven.getQuantity()<line.getQuantity()){
                        check = false;
                        redirectAttributes.addFlashAttribute("message",new Message("Inventory problem,change quantityor delete item: "+line.getItem().getCode(), Message.Type.DANGER));
                        return "redirect:/emp";
                    }
                }

            }

            if(!in_inven){
                redirectAttributes.addFlashAttribute("message",new Message("Inventory problem,change quantity or delete item: "+line.getItem().getCode(), Message.Type.DANGER));
                return "redirect:/emp";
            }
        }

            Account account = accountRepository.findByUsername(principal.getName());
            for(CartLine line : myCart.getList()){
                Sale sale = new Sale();
                sale.setAccount(account);
                sale.setShop(shop);
                sale.setItem(line.getItem());
                sale.setQuantity(line.getQuantity());
                sale.setExp_date(new Date());
                saleRepository.save(sale);
            }
            Utils.storeLastOrderedCartInSession(request,myCart);
            Utils.removeCartInSession(request);

            redirectAttributes.addFlashAttribute("message",new Message("Save success",Message.Type.SUCCESS));
            return "redirect:/emp";
        }


}
