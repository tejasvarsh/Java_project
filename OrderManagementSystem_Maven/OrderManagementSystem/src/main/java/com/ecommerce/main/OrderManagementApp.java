package com.ecommerce.main;
import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.Order;
import java.util.List;
import java.util.Scanner;

public class OrderManagementApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OrderDAO dao = new OrderDAO();
        int choice;

        do {
            System.out.println();
            System.out.println("========== E-COMMERCE ORDER MANAGEMENT SYSTEM ==========");
            System.out.println("1. Insert Order Records (Min 3 records recommended)");
            System.out.println("2. View Orders by Status (Quantity > 2, Sorted by Price DESC)");
            System.out.println("3. Increase Order Quantity");
            System.out.println("4. Update Order Status");
            System.out.println("5. View Total Order Value & Delete Cancelled Order");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    // Operation (a)
                    System.out.print("How many orders do you want to insert? (Enter at least 3): ");
                    int count = sc.nextInt();
                    sc.nextLine();

                    for (int i = 1; i <= count; i++) {
                        System.out.println();
                        System.out.println("--- Enter details for Order " + i + " ---");
                        System.out.print("Order ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Customer Name: ");
                        String custName = sc.nextLine();

                        System.out.print("Product Name: ");
                        String prodName = sc.nextLine();

                        System.out.print("Quantity: ");
                        int qty = sc.nextInt();

                        System.out.print("Unit Price: ");
                        double price = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("Status (Pending / Shipped / Delivered / Cancelled): ");
                        String status = sc.nextLine();

                        Order order = new Order(id, custName, prodName, qty, price, status);
                        if (dao.insertOrder(order)) {
                            System.out.println("Order inserted successfully!");
                        }
                    }
                    break;

                case 2:
                    // Operation (b)
                    System.out.print("Enter Order Status to search: ");
                    String searchStatus = sc.nextLine();
                    List<Order> orders = dao.getOrdersByStatusAndQty(searchStatus);

                    if (orders.isEmpty()) {
                        System.out.println("No orders found with status '" + searchStatus + "' and quantity > 2.");
                    } else {
                        System.out.println();
                        System.out.println("--- Matching Orders (Sorted by Unit Price DESC) ---");
                        for (Order o : orders) {
                            System.out.println(o);
                        }
                    }
                    break;

                case 3:
                    // Operation (c)
                    System.out.print("Enter Order ID to increase quantity: ");
                    int qtyOrderId = sc.nextInt();
                    System.out.print("Enter additional quantity to add: ");
                    int addQty = sc.nextInt();
                    sc.nextLine();

                    if (dao.getOrderById(qtyOrderId) == null) {
                        System.out.println("Error: Order ID " + qtyOrderId + " does not exist!");
                    } else {
                        if (dao.increaseOrderQuantity(qtyOrderId, addQty)) {
                            System.out.println("Quantity updated successfully!");
                            System.out.println("Updated Order: " + dao.getOrderById(qtyOrderId));
                        } else {
                            System.out.println("Failed to update quantity.");
                        }
                    }
                    break;

                case 4:
                    // Operation (d)
                    System.out.print("Enter Order ID to update status: ");
                    int statusOrderId = sc.nextInt();
                    sc.nextLine();

                    Order existingOrder = dao.getOrderById(statusOrderId);
                    if (existingOrder == null) {
                        System.out.println("Error: Order ID " + statusOrderId + " does not exist!");
                    } else {
                        System.out.println("Current Order Details: " + existingOrder);
                        System.out.print("Enter new status (Pending / Shipped / Delivered): ");
                        String newStatus = sc.nextLine();

                        if (newStatus.equalsIgnoreCase("Pending") ||
                            newStatus.equalsIgnoreCase("Shipped") ||
                            newStatus.equalsIgnoreCase("Delivered")) {

                            // Format cleanly (e.g., Pending, Shipped, Delivered)
                            String formattedStatus = newStatus.substring(0, 1).toUpperCase() + newStatus.substring(1).toLowerCase();
                            if (dao.updateOrderStatus(statusOrderId, formattedStatus)) {
                                System.out.println("Status updated successfully!");
                                System.out.println("Updated Order Details: " + dao.getOrderById(statusOrderId));
                            }
                        } else {
                            System.out.println("Invalid status! Only 'Pending', 'Shipped', or 'Delivered' are allowed.");
                        }
                    }
                    break;

                case 5:
                    // Operation (e)
                    double totalValue = dao.getTotalOrderValue();
                    System.out.println();
                    System.out.println("Total Order Value (SUM of quantity * unit_price): " + totalValue);

                    System.out.print("Enter Order ID to delete (Only Cancelled orders can be deleted): ");
                    int delOrderId = sc.nextInt();
                    sc.nextLine();

                    String resultMsg = dao.deleteCancelledOrder(delOrderId);
                    System.out.println(resultMsg);
                    break;

                case 6:
                    System.out.println("Exiting Order Management System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please select between 1 and 6.");
            }
        } while (choice != 6);

        sc.close();
    }
}
