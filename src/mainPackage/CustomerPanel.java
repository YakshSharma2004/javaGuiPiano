package mainPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CustomerPanel extends JPanel {

    private CustomerService customerService;
    private JTable customerTable;
    private DefaultTableModel tableModel;

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtContactNumber;
    private JTextField txtEmail;
    private JTextField txtAddress;

    public CustomerPanel() {
        this.customerService = new CustomerService();
        setLayout(new BorderLayout());

        // Top panel (Form fields)
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.NORTH);

        // Center panel (Table with customer data)
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // Bottom panel (Buttons)
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        // Initial load of data
        loadCustomersIntoTable();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 6, 5, 5)); 
        // 2 rows, 6 columns, some spacing

        // Labels
        panel.add(new JLabel("First Name:"));
        panel.add(new JLabel("Last Name:"));
        panel.add(new JLabel("Contact:"));
        panel.add(new JLabel("Email:"));
        panel.add(new JLabel("Address:"));
        // an empty label to keep the layout symmetrical
        panel.add(new JLabel(""));

        // Text Fields
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtContactNumber = new JTextField();
        txtEmail = new JTextField();
        txtAddress = new JTextField();

        panel.add(txtFirstName);
        panel.add(txtLastName);
        panel.add(txtContactNumber);
        panel.add(txtEmail);
        panel.add(txtAddress);

        // an empty label or button placeholder
        panel.add(new JLabel(""));

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "Contact", "Email", "Address"}, 0);
        customerTable = new JTable(tableModel);

        // Wrap in a scroll pane
        JScrollPane scrollPane = new JScrollPane(customerTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        JButton btnAdd = new JButton("Add Customer");
        btnAdd.addActionListener(e -> addCustomer());

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadCustomersIntoTable());

        panel.add(btnAdd);
        panel.add(btnRefresh);

        return panel;
    }

    private void addCustomer() {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String contact = txtContactNumber.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();

        // 1. Check for empty fields
        if (firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "First name and last name are required.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Optional phone check - at least not empty or maybe a numeric check
        if (contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Contact number is required.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        // You could do more advanced phone checks. Example: 
        // if(!contact.matches("\\d+")) { ... }

        // 3. Basic email format check (simple example)
        if (!email.isEmpty() && !email.contains("@")) {
            JOptionPane.showMessageDialog(this, 
                "Invalid email format (must contain @).", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // If all validations pass, proceed to add
        customerService.addCustomer(0, firstName, lastName, contact, email, address);

        // Clear fields
        txtFirstName.setText("");
        txtLastName.setText("");
        txtContactNumber.setText("");
        txtEmail.setText("");
        txtAddress.setText("");

        // Refresh table
        loadCustomersIntoTable();
    }


    private void loadCustomersIntoTable() {
        // Clear table first
        tableModel.setRowCount(0);

        // Retrieve from DB
        List<Customers> customersList = customerService.getAllCustomers();

        // Populate table
        for (Customers c : customersList) {
            tableModel.addRow(new Object[]{
                c.getCustomerId(),
                c.getFirstName(),
                c.getLastName(),
                c.getContactNumber(),
                c.getEmail(),
                c.getAddress()
            });
        }
    }
}
