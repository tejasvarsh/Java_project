package com.ecommerce.dao;

import com.ecommerce.model.Order;
import com.ecommerce.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // Operation (a): Insert order using PreparedStatement and handle duplicate IDs
    public boolean insertOrder(Order order) {
        String sql = "INSERT INTO Orders (order_id, customer_name, product_name, quantity, unit_price, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, order.getOrderId());
            ps.setString(2, order.getCustomerName());
            ps.setString(3, order.getProductName());
            ps.setInt(4, order.getQuantity());
            ps.setDouble(5, order.getUnitPrice());
            ps.setString(6, order.getStatus());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: Order ID " + order.getOrderId() + " already exists! Duplicate ID is not allowed.");
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Error: " + e.getMessage());
            return false;
        }
    }

    // Operation (b): Display orders by status with quantity > 2, sorted by unit_price DESC
    public List<Order> getOrdersByStatusAndQty(String status) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE status = ? AND quantity > 2 ORDER BY unit_price DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("order_id"),
                    rs.getString("customer_name"),
                    rs.getString("product_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price"),
                    rs.getString("status")
                );
                list.add(order);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
        return list;
    }

    // Helper method to get single order by ID
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM Orders WHERE order_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Order(
                    rs.getInt("order_id"),
                    rs.getString("customer_name"),
                    rs.getString("product_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price"),
                    rs.getString("status")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
        return null;
    }

    // Operation (c): Increase existing quantity of specified order
    public boolean increaseOrderQuantity(int orderId, int additionalQty) {
        String sql = "UPDATE Orders SET quantity = quantity + ? WHERE order_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, additionalQty);
            ps.setInt(2, orderId);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Error: " + e.getMessage());
            return false;
        }
    }

    // Operation (d): Update order status
    public boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE Orders SET status = ? WHERE order_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, orderId);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Error: " + e.getMessage());
            return false;
        }
    }

    // Operation (e) Part 1: Total order value using SUM(quantity * unit_price)
    public double getTotalOrderValue() {
        String sql = "SELECT SUM(quantity * unit_price) AS total_value FROM Orders";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total_value");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
        return 0.0;
    }

    // Operation (e) Part 2: Delete order only if status is Cancelled
    public String deleteCancelledOrder(int orderId) {
        Order order = getOrderById(orderId);
        if (order == null) {
            return "Order ID " + orderId + " does not exist.";
        }
        if (!order.getStatus().equalsIgnoreCase("Cancelled")) {
            return "Cannot delete Order ID " + orderId + ". Current status is '" + order.getStatus() + "' (Only 'Cancelled' orders can be deleted).";
        }

        String sql = "DELETE FROM Orders WHERE order_id = ? AND status = 'Cancelled'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return "Order ID " + orderId + " deleted successfully!";
            } else {
                return "Failed to delete Order ID " + orderId + ".";
            }
        } catch (SQLException | ClassNotFoundException e) {
            return "Database Error: " + e.getMessage();
        }
    }
}
