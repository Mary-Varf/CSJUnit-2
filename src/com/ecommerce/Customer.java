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

    // Add product to cart, or increment quantity if it already exists
    public void addToCart(Product product) {
        for (Product p : cart) {
            if (p.getProductID() == product.getProductID()) {
                p.incrementQuantity();  // Increment quantity if product exists in the cart
                return;
            }
        }
        cart.add(product);  // Add new product to the cart if it doesn't exist
    }

    // Remove product from cart
    public void removeFromCart(Product product) {
        cart.remove(product);
    }

    // Calculate the total price of the products in the cart
    public double calculateTotal() {
        return cart.stream().mapToDouble(p -> p.getPrice() * p.getQuantity()).sum();
    }

    public List<Product> getCart() { return cart; }
    public String getName() { return name; }
    public int getCustomerID() { return customerID; }

    public Order placeOrder(int orderID) {
        return new Order(orderID, this);
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerID + ", Name: " + name + ", Cart: " + cart;
    }
}
