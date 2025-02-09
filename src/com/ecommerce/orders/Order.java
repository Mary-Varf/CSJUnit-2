package com.ecommerce.orders;

import com.ecommerce.Product;
import com.ecommerce.Customer;
import java.util.List;

public class Order {
    private int orderID;
    private Customer customer;
    private List<Product> products;
    private double total;

    public Order(int orderID, Customer customer) {
        if (orderID <= 0) {
            throw new IllegalArgumentException("Order ID must be greater than 0");
        }
        this.orderID = orderID;
        this.customer = customer;
        this.products = customer.getCart();
        this.total = customer.calculateTotal();
    }

    public double generateOrderSummary() {
        return total;
    }

    public List<Product> getProductList() {
        return products;
    }

    public Customer getCustomer() {
        return customer;
    }
}
