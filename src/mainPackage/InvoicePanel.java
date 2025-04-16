package mainPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InvoicePanel extends JPanel {

    private InvoiceService invoiceService;

    private JTable invoiceTable;
    private DefaultTableModel tableModel;

    // Fields for the form
    private JTextField txtCustomerId;
    private JTextField txtTotalAmount;
    private JTextField txtDueDate;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public InvoicePanel() {
        this.invoiceService = new InvoiceService();

        setLayout(new BorderLayout());

        // Top: Form panel
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.NORTH);

        // Center: Table panel
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // Bottom: Button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        // Initial load
        loadInvoicesIntoTable();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 5, 5));

        panel.add(new JLabel("Customer ID:"));
        panel.add(new JLabel("Total Amount:"));
        panel.add(new JLabel("Due Date (yyyy-MM-dd):"));

        txtCustomerId = new JTextField();
        txtTotalAmount = new JTextField();
        txtDueDate = new JTextField("2025-05-01"); // placeholder

        panel.add(txtCustomerId);
        panel.add(txtTotalAmount);
        panel.add(txtDueDate);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Invoice ID", "Customer ID", "Total Amount", "Due Date"}, 0);
        invoiceTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(invoiceTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        JButton btnGenerate = new JButton("Generate Invoice");
        btnGenerate.addActionListener(e -> generateInvoice());

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadInvoicesIntoTable());

        // NEW: Delete button
        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.addActionListener(e -> deleteSelectedInvoice());

        panel.add(btnGenerate);
        panel.add(btnRefresh);
        panel.add(btnDelete);

        return panel;
    }

    private void generateInvoice() {
        try {
            int customerId = Integer.parseInt(txtCustomerId.getText().trim());
            double totalAmount = Double.parseDouble(txtTotalAmount.getText().trim());
            String dueDateStr = txtDueDate.getText().trim();

            Date dueDate;
            try {
                dueDate = dateFormat.parse(dueDateStr);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd");
                return;
            }

            // ID is auto-increment in DB, so pass 0
            invoiceService.generateInvoice(0, customerId, totalAmount, dueDate);

            txtCustomerId.setText("");
            txtTotalAmount.setText("");
            txtDueDate.setText("");

            loadInvoicesIntoTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Customer ID and Total Amount must be numeric.");
        }
    }

    private void deleteSelectedInvoice() {
        int selectedRow = invoiceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an invoice to delete.");
            return;
        }
        // Invoice ID is in column 0
        int invoiceId = (int) tableModel.getValueAt(selectedRow, 0);

        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete Invoice ID: " + invoiceId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            invoiceService.deleteInvoice(invoiceId);
            loadInvoicesIntoTable();
        }
    }

    private void loadInvoicesIntoTable() {
        tableModel.setRowCount(0);

        List<Invoice> invoices = invoiceService.getAllInvoices();
        for (Invoice inv : invoices) {
            tableModel.addRow(new Object[]{
                inv.getInvoiceId(),
                inv.getCustomerId(),
                inv.getTotalAmount(),
                dateFormat.format(inv.getDueDate())
            });
        }
    }
}
