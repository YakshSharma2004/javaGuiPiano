package mainPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public void addPayment(Payment payment) {
        String sql = "INSERT INTO payments (invoice_id, payment_date, amount, payment_method, status) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, payment.getInvoiceId());
            stmt.setTimestamp(2, new Timestamp(payment.getPaymentDate().getTime()));
            stmt.setDouble(3, payment.getAmount());
            stmt.setString(4, payment.getPaymentMethod());
            stmt.setString(5, payment.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT payment_id, invoice_id, payment_date, amount, payment_method, status FROM payments";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int paymentId = rs.getInt("payment_id");
                int invoiceId = rs.getInt("invoice_id");
                Timestamp paymentTS = rs.getTimestamp("payment_date");
                double amount = rs.getDouble("amount");
                String method = rs.getString("payment_method");
                String status = rs.getString("status");

                Payment payment = new Payment(
                    paymentId,
                    invoiceId,
                    new java.util.Date(paymentTS.getTime()),
                    amount,
                    method,
                    status
                );
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }
    
    // Additional methods if needed: getPaymentById, updatePayment, deletePayment, etc.
}
