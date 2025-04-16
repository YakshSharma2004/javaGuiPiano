package mainPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentPanel extends JPanel {
    private PaymentService paymentService;
    private JTable paymentTable;
    private DefaultTableModel tableModel;

    private JTextField txtInvoiceId;
    private JTextField txtPaymentDate;
    private JTextField txtAmount;
    private JTextField txtMethod;
    private JTextField txtStatus;

    // We'll parse date/time in "yyyy-MM-dd HH:mm" format
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public PaymentPanel() {
        this.paymentService = new PaymentService();
        setLayout(new BorderLayout());

        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        loadPaymentsIntoTable();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 5, 5, 5));

        panel.add(new JLabel("Invoice ID:"));
        panel.add(new JLabel("Payment Date (yyyy-MM-dd HH:mm):"));
        panel.add(new JLabel("Amount:"));
        panel.add(new JLabel("Method:"));
        panel.add(new JLabel("Status:"));

        txtInvoiceId = new JTextField();
        txtPaymentDate = new JTextField("2025-05-01 09:00"); 
        txtAmount = new JTextField();
        txtMethod = new JTextField("Credit Card"); 
        txtStatus = new JTextField("Completed");

        panel.add(txtInvoiceId);
        panel.add(txtPaymentDate);
        panel.add(txtAmount);
        panel.add(txtMethod);
        panel.add(txtStatus);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(
            new Object[]{"Payment ID", "Invoice ID", "Payment Date", "Amount", "Method", "Status"}, 
            0
        );
        paymentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(paymentTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        JButton btnAdd = new JButton("Record Payment");
        btnAdd.addActionListener(e -> addPayment());

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadPaymentsIntoTable());

        panel.add(btnAdd);
        panel.add(btnRefresh);
        return panel;
    }

    private void addPayment() {
        // Validate invoiceId
        String invoiceIdStr = txtInvoiceId.getText().trim();
        if (invoiceIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Invoice ID is required.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        int invoiceId;
        try {
            invoiceId = Integer.parseInt(invoiceIdStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Invoice ID must be a valid integer.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate payment date/time
        String payDateStr = txtPaymentDate.getText().trim();
        if (payDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Payment date is required.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        Date payDate;
        try {
            payDate = dateFormat.parse(payDateStr);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, 
                "Invalid date/time format. Use yyyy-MM-dd HH:mm", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate amount
        String amountStr = txtAmount.getText().trim();
        if (amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Amount is required.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Amount must be a non-negative number.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate method (optional)
        String method = txtMethod.getText().trim();
        if (method.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Payment method is required.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate status (optional)
        String status = txtStatus.getText().trim();
        if (status.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Status is required.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // If everything is valid, proceed
        paymentService.recordPayment(0, invoiceId, payDate, amount, method, status);

        // Clear fields
        txtInvoiceId.setText("");
        txtPaymentDate.setText("");
        txtAmount.setText("");
        txtMethod.setText("");
        txtStatus.setText("");

        loadPaymentsIntoTable();
    }


    private void loadPaymentsIntoTable() {
        tableModel.setRowCount(0);
        List<Payment> payments = paymentService.getAllPayments();
        for (Payment p : payments) {
            tableModel.addRow(new Object[]{
                p.getPaymentId(),
                p.getInvoiceId(),
                dateFormat.format(p.getPaymentDate()),
                p.getAmount(),
                p.getPaymentMethod(),
                p.getStatus()
            });
        }
    }
}
