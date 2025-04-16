package mainPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TunerPanel extends JPanel {
    private TunerService tunerService;
    private JTable tunerTable;
    private DefaultTableModel tableModel;

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtPhone;
    private JTextField txtEmail;

    public TunerPanel() {
        this.tunerService = new TunerService();
        setLayout(new BorderLayout());

        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        loadTunersIntoTable();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 5, 5));

        panel.add(new JLabel("First Name:"));
        panel.add(new JLabel("Last Name:"));
        panel.add(new JLabel("Phone:"));
        panel.add(new JLabel("Email:"));

        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();

        panel.add(txtFirstName);
        panel.add(txtLastName);
        panel.add(txtPhone);
        panel.add(txtEmail);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(
            new Object[]{"Tuner ID", "First Name", "Last Name", "Phone", "Email"}, 
            0
        );
        tunerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tunerTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        JButton btnAdd = new JButton("Add Tuner");
        btnAdd.addActionListener(e -> addTuner());

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadTunersIntoTable());

        panel.add(btnAdd);
        panel.add(btnRefresh);
        return panel;
    }

    private void addTuner() {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        // Validate required fields
        if (firstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "First Name is required.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Last Name is required.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Optional phone / email checks
        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Phone is required.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.isEmpty() && !email.contains("@")) {
            JOptionPane.showMessageDialog(this, 
                "Invalid email format (must contain @).", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // If all good -> add tuner
        tunerService.addTuner(0, firstName, lastName, phone, email);

        txtFirstName.setText("");
        txtLastName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");

        loadTunersIntoTable();
    }


    private void loadTunersIntoTable() {
        tableModel.setRowCount(0);
        List<Tuner> tuners = tunerService.getAllTuners();
        for (Tuner t : tuners) {
            tableModel.addRow(new Object[]{
                t.getTunerId(), 
                t.getFirstName(), 
                t.getLastName(), 
                t.getPhone(),
                t.getEmail()
            });
        }
    }
}
