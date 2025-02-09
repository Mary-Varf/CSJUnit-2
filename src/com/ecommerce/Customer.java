package com.ecommerce;

import com.ecommerce.orders.Order;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int customerID;
    private String name;
    private List<Product> cart;

    public Customer(int customerID, String name) {
        if (customerID <= 0) {
            throw new IllegalArgumentException("Customer ID must be greater than 0");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        this.customerID = customerID;
        this.name = name;
        this.cart = new ArrayList<>();
    }

    public void addToCart(Product product) {
        for (Product p : cart) {
            if (p.getProductID() == product.getProductID()) {
                throw new IllegalArgumentException("Product with this ID already exists in the cart.");
            }
        }
        cart.add(product);
    }

    public void removeFromCart(Product product) {
        cart.remove(product);
    }

    public double calculateTotal() {
        return cart.stream().mapToDouble(Product::getPrice).sum();
    }

    public List<Product> getCart() { return cart; }

    public String getName() { return name; }

    public Order placeOrder(int orderID) {
        return new Order(orderID, this);
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerID + ", Name: " + name + ", Cart: " + cart;
    }

    public int getCustomerID() { return customerID; }
}
