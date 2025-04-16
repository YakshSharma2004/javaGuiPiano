package mainPackage;
import java.util.*;

public class InvoiceService {
private InvoiceDAO invoiceDAO = new InvoiceDAO();
    
    public void generateInvoice(int id, int customerId, double totalAmount, Date dueDate) {
        Invoice invoice = new Invoice(id, customerId, totalAmount, dueDate);
        invoiceDAO.addInvoice(invoice);
    }
    public void deleteInvoice(int invoiceId) {
        invoiceDAO.deleteInvoiceById(invoiceId);
    }

    
    public List<Invoice> getAllInvoices() {
        return invoiceDAO.getAllInvoices();
    }
}
