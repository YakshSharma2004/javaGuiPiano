package mainPackage;
import java.util.*;

public class CustomerService {
private CustomerDAO customerDAO = new CustomerDAO();
    
    public void addCustomer(int id, String firstName, String lastName, String contact, String email, String address) {
        Customers customer = new Customers(id, firstName, lastName, contact, email, address);
        customerDAO.addCustomer(customer);
    }
    
    public List<Customers> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
    
    public Customers getCustomerById(int id) {
        return customerDAO.getCustomerById(id);
    }
}