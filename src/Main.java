import com.ecommerce.*;
import com.ecommerce.orders.Order;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        while (true) {
            int customerID = getIntInput("Enter Customer ID:");
            if (customerID == -1) System.exit(0);

            String customerName = getStringInput("Enter Customer Name:");
            if (customerName == null) System.exit(0);

            Customer customer = new Customer(customerID, customerName);

            while (true) {
                int choice = JOptionPane.showOptionDialog(null, "Choose an action:", "Shopping Cart",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        new String[]{"Add Product", "Remove Product", "View Cart", "Place Order"}, "Add Product");

                if (choice == JOptionPane.CLOSED_OPTION) {
                    System.exit(0);
                }

                if (choice == 0) {
                    int productID = getIntInput("Enter Product ID:");
                    if (productID == -1) continue;

                    if (customer.getCart().stream().anyMatch(p -> p.getProductID() == productID)) {
                        JOptionPane.showMessageDialog(null, "Product with this ID already exists in the cart.");
                        continue;
                    }

                    String productName = getStringInput("Enter Product Name:");
                    if (productName == null) continue;

                    double productPrice = getDoubleInput("Enter Product Price:");
                    if (productPrice == -1) continue;

                    customer.addToCart(new Product(productID, productName, productPrice));
                } else if (choice == 1) {
                    if (customer.getCart().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Cart is empty.");
                    } else {
                        int productID = getIntInput("Enter Product ID to Remove:");
                        customer.getCart().removeIf(p -> p.getProductID() == productID);
                    }
                } else if (choice == 2) {
                    showCartTable(customer);
                } else if (choice == 3) {
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
        String[] columns = {"ID", "Product", "Price"};
        Object[][] data = customer.getCart().stream()
                .map(p -> new Object[]{p.getProductID(), p.getName(), p.getPrice()})
                .toArray(Object[][]::new);

        JTable table = new JTable(new DefaultTableModel(data, columns));
        JOptionPane.showMessageDialog(null, new JScrollPane(table), "Your Cart", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showOrderSummary(Order order) {
        Customer customer = order.getCustomer();
        String[] columns = {"ID", "Product", "Price"};
        Object[][] data = order.getProductList().stream()
                .map(p -> new Object[]{p.getProductID(), p.getName(), p.getPrice()})
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

    private static double getDoubleInput(String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(message);
            if (input == null) return -1;
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
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
