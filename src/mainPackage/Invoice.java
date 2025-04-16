package mainPackage;
import java.util.Date;

public class Invoice {
	private int invoiceId;
    private int customerId;
    private double totalAmount;
    private Date dueDate;

    public Invoice(int invoiceId, int customerId, double totalAmount, Date dueDate) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.dueDate = dueDate;
    }
	public int getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
}
