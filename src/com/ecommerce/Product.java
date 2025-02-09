package com.ecommerce;

public class Product {
    private int productID;
    private String name;
    private double price;
    private int quantity;  // Tracks the number of this product in the cart

    public Product(int productID, String name, double price) {
        if (productID <= 0) {
            throw new IllegalArgumentException("Product ID must be greater than 0");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.quantity = 1; // Default quantity is 1
    }

    public int getProductID() { return productID; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    @Override
    public String toString() {
        return "Product ID: " + productID + ", Name: " + name + ", Price: $" + price + ", Quantity: " + quantity;
    }
}
