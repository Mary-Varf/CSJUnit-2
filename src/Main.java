import com.ecommerce.*;
import com.ecommerce.orders.Order;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Creating a preset list of products
        List<Product> availableProducts = new ArrayList<>();
        availableProducts.add(new Product(101, "Laptop", 899.99));
        availableProducts.add(new Product(102, "Headphones", 199.99));
        availableProducts.add(new Product(103, "Smartphone", 499.99));
        availableProducts.add(new Product(104, "Tablet", 299.99));

        while (true) {
            int customerID = getIntInput("Enter Customer ID:");
            if (customerID == -1) System.exit(0);

            String customerName = getStringInput("Enter Customer Name:");
            if (customerName == null) System.exit(0);

            Customer customer = new Customer(customerID, customerName);

            while (true) {
                int choice = JOptionPane.showOptionDialog(null, "Choose an action:", "Shopping Cart",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        new String[]{"Browse Products", "Remove Product", "View Cart", "Place Order"}, "Browse Products");

                if (choice == JOptionPane.CLOSED_OPTION) {
                    System.exit(0);
                }

                if (choice == 0) { // Browse Products
                    // Create a JComboBox to display products as a dropdown list
                    JComboBox<String> productDropdown = new JComboBox<>();
                    availableProducts.forEach(p -> productDropdown.addItem(p.getName()));

                    int option = JOptionPane.showConfirmDialog(null, productDropdown,
                            "Select a Product", JOptionPane.OK_CANCEL_OPTION);

                    if (option == JOptionPane.CANCEL_OPTION || productDropdown.getSelectedIndex() == -1) {
                        continue;
                    }

                    // Get the selected product
                    Product selectedProduct = availableProducts.get(productDropdown.getSelectedIndex());

                    // Add product to cart (or increment quantity if it already exists)
                    customer.addToCart(selectedProduct);
                } else if (choice == 1) { // Remove Product from Cart
                    if (customer.getCart().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Cart is empty.");
                    } else {
                        // Create a JComboBox for removing products from the cart
                        JComboBox<String> cartDropdown = new JComboBox<>();
                        customer.getCart().forEach(p -> cartDropdown.addItem(p.getName()));

                        int option = JOptionPane.showConfirmDialog(null, cartDropdown,
                                "Select a Product to Remove", JOptionPane.OK_CANCEL_OPTION);

                        if (option == JOptionPane.CANCEL_OPTION || cartDropdown.getSelectedIndex() == -1) {
                            continue;
                        }

                        // Get the selected product to remove
                        Product selectedProduct = customer.getCart().get(cartDropdown.getSelectedIndex());

                        // Remove the selected product from the cart
                        customer.removeFromCart(selectedProduct);
                    }
                } else if (choice == 2) { // View Cart
                    showCartTable(customer);
                } else if (choice == 3) { // Place Order
                    if (customer.getCart().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Cart is empty. Add products before placing an order.");
                    } else {
                        Order order = customer.placeOrder(1);
                        showOrderSummary(order);
                        System.exit(0);
                    }
                }
            }
        }
    }

    private static void showCartTable(Customer customer) {
        String[] columns = {"ID", "Product", "Price", "Quantity", "Sum"};
        Object[][] data = customer.getCart().stream()
                .map(p -> new Object[]{
                        p.getProductID(),
                        p.getName(),
                        p.getPrice(),
                        p.getQuantity(),
                        p.getPrice() * p.getQuantity()  // Calculating the sum (price * quantity)
                })
                .toArray(Object[][]::new);

        JTable table = new JTable(new DefaultTableModel(data, columns));
        JOptionPane.showMessageDialog(null, new JScrollPane(table), "Your Cart", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showOrderSummary(Order order) {
        Customer customer = order.getCustomer();
        String[] columns = {"ID", "Product", "Price", "Quantity", "Sum"};
        Object[][] data = order.getProductList().stream()
                .map(p -> new Object[]{
                        p.getProductID(),
                        p.getName(),
                        p.getPrice(),
                        p.getQuantity(),
                        p.getPrice() * p.getQuantity()  // Calculating the sum (price * quantity)
                })
                .toArray(Object[][]::new);

        JTable table = new JTable(new DefaultTableModel(data, columns));
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(new JLabel("Customer: " + customer.getName() + " (ID: " + customer.getCustomerID() + ")"), BorderLayout.NORTH);
        panel.add(new JLabel("Total: $" + order.generateOrderSummary()), BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(null, panel, "Order Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    private static int getIntInput(String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(message);
            if (input == null) return -1;
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static String getStringInput(String message) {
        String input;
        do {
            input = JOptionPane.showInputDialog(message);
            if (input == null) return null;
            if (input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Input cannot be empty. Please enter a valid value.");
            }
        } while (input.trim().isEmpty());
        return input;
    }
}
