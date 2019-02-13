package com.example.mylittleshop.model;

import javax.servlet.http.HttpServletRequest;

public class Utils {

    // Products in the cart, stored in Session.
    public static Cart getCartInSession(HttpServletRequest request) {

        Cart cart = (Cart) request.getSession().getAttribute("myCart");


        if (cart == null) {
            cart = new Cart();

            request.getSession().setAttribute("myCart", cart);
        }

        return cart;
    }

    public static void removeCartInSession(HttpServletRequest request) {
        request.getSession().removeAttribute("myCart");
    }

    public static void storeLastOrderedCartInSession(HttpServletRequest request, Cart cart) {
        request.getSession().setAttribute("lastOrderedCart", cart);
    }

    public static Cart getLastOrderedCartInSession(HttpServletRequest request) {
        return (Cart) request.getSession().getAttribute("lastOrderedCart");
    }

}
