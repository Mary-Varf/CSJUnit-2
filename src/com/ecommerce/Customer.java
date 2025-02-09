package com.ecommerce;

import com.ecommerce.orders.Order;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

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

    // Remove one product from the cart
    public void removeFromCart(Product product) {
        for (Product p : cart) {
            if (p.getProductID() == product.getProductID()) {
                if (p.getQuantity() > 1) {
                    p.setQuantity(p.getQuantity() - 1);  // Decrease the quantity by 1
                } else {
                    cart.remove(p);  // Remove product if quantity becomes 0
                }
                return;  // Exit after removing one instance of the product
            }
        }
    }

    // Calculate the total price of the products in the cart
    public double calculateTotal() {
        double total = cart.stream().mapToDouble(p -> p.getPrice() * p.getQuantity()).sum();

        // Round the total price to 2 decimal places
        BigDecimal bd = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
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
