package mainPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    
    public void addCustomer(Customers customer) {
        String sql = "INSERT INTO customers ( first_name, last_name, contact_number, email, address) "
                   + "VALUES ( ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
//            stmt.setInt(1, customer.getCustomerId());
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getContactNumber());
            stmt.setString(4, customer.getEmail());	
            stmt.setString(5, customer.getAddress());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Customers> getAllCustomers() {
        List<Customers> customersList = new ArrayList<>();
        String sql = "SELECT customer_id, first_name, last_name, contact_number, email, address FROM customers";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Customers customer = new Customers(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("contact_number"),
                    rs.getString("email"),
                    rs.getString("address")
                );
                customersList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }
    
    public Customers getCustomerById(int customerId) {
        Customers customer = null;
        String sql = "SELECT customer_id, first_name, last_name, contact_number, email, address "
                   + "FROM customers WHERE customer_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    customer = new Customers(
                        rs.getInt("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("contact_number"),
                        rs.getString("email"),
                        rs.getString("address")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
