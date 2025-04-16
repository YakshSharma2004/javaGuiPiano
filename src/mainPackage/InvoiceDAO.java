package mainPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    
    public void addInvoice(Invoice invoice) {
        String sql = "INSERT INTO invoices ( customer_id, total_amount, due_date) "
                   + "VALUES ( ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
//            stmt.setInt(1, invoice.getInvoiceId());
            stmt.setInt(1, invoice.getCustomerId());
            stmt.setDouble(2, invoice.getTotalAmount());
            
            // For DATE columns, we can use java.sql.Date if we only want the date portion
            // If your column is DATETIME, use Timestamp
            stmt.setDate(3, new java.sql.Date(invoice.getDueDate().getTime()));
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteInvoiceById(int invoiceId) {
        String sql = "DELETE FROM invoices WHERE invoice_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT invoice_id, customer_id, total_amount, due_date FROM invoices";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int invoiceId = rs.getInt("invoice_id");
                int customerId = rs.getInt("customer_id");
                double totalAmount = rs.getDouble("total_amount");
                
                // For a DATE column, we use java.sql.Date -> convert to java.util.Date
                java.sql.Date date = rs.getDate("due_date");
                
                Invoice invoice = new Invoice(
                    invoiceId,
                    customerId,
                    totalAmount,
                    new java.util.Date(date.getTime())
                );
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }
}
