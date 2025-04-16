package mainPackage;

import java.util.Date;
import java.util.List;

public class PaymentService {
    private PaymentDAO paymentDAO = new PaymentDAO();

    public void recordPayment(int paymentId, int invoiceId, Date paymentDate, double amount, String method, String status) {
        Payment payment = new Payment(paymentId, invoiceId, paymentDate, amount, method, status);
        paymentDAO.addPayment(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentDAO.getAllPayments();
    }
}
