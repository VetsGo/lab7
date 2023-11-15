package org.example;

import java.util.HashMap;
import java.util.Map;

public class User {
    private Integer id;
    private String username;
    private Map<Product, Integer> cart = new HashMap<>();

    public User(String username) {
        this.username = username;
    }

    public void addProductToCart(Product product, int quantity) {
        cart.put(product, quantity);
    }

    public void removeProductFromCart(Product product) {
        cart.remove(product);
    }

    public void discardCart() {
        cart.clear();
    }

    public Order makeOrderFromCart() {
        Order order = new Order();
        order.setUserId(id);
        order.setOrderDetails(new HashMap<>(cart));
        discardCart();

        return order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
}