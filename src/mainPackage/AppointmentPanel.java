package mainPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppointmentPanel extends JPanel {

    private AppointmentService appointmentService;

    private JTable appointmentTable;
    private DefaultTableModel tableModel;

    // Fields for the form
    private JTextField txtCustomerId;
    private JTextField txtStartTime;
    private JTextField txtDuration;
    private JTextField txtStatus;

    // SimpleDateFormat for parsing date/time input
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public AppointmentPanel() {
        this.appointmentService = new AppointmentService();

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

        // Load existing appointments into the table
        loadAppointmentsIntoTable();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 5, 5));
        // 2 rows, 4 columns

        // Labels
        panel.add(new JLabel("Customer ID:"));
        panel.add(new JLabel("Start (yyyy-MM-dd HH:mm):"));
        panel.add(new JLabel("Duration (min):"));
        panel.add(new JLabel("Status:"));

        // Text Fields
        txtCustomerId = new JTextField();
        txtStartTime = new JTextField("2025-04-12 14:00"); // example placeholder
        txtDuration = new JTextField("30");
        txtStatus = new JTextField("Scheduled");

        panel.add(txtCustomerId);
        panel.add(txtStartTime);
        panel.add(txtDuration);
        panel.add(txtStatus);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Appt ID", "Customer ID", "Start Time", "Duration", "Status"}, 0);
        appointmentTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        JButton btnAdd = new JButton("Schedule Appointment");
        btnAdd.addActionListener(e -> addAppointment());

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadAppointmentsIntoTable());

        // NEW: Delete button
        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.addActionListener(e -> deleteSelectedAppointment());

        panel.add(btnAdd);
        panel.add(btnRefresh);
        panel.add(btnDelete);

        return panel;
    }

    private void addAppointment() {
        try {
            int customerId = Integer.parseInt(txtCustomerId.getText().trim());
            String startTimeStr = txtStartTime.getText().trim();
            int duration = Integer.parseInt(txtDuration.getText().trim());
            String status = txtStatus.getText().trim();

            // Parse the date/time
            Date startDate;
            try {
                startDate = dateFormat.parse(startTimeStr);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date/time format. Use yyyy-MM-dd HH:mm");
                return;
            }

            // Because appointment_id is auto-increment in the DB, pass 0
            appointmentService.scheduleAppointment(0, customerId, startDate, duration, status);

            // Clear fields if you want
            txtCustomerId.setText("");
            txtStartTime.setText("");
            txtDuration.setText("");
            txtStatus.setText("");

            loadAppointmentsIntoTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Customer ID and Duration.");
        }
    }

    private void deleteSelectedAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to delete.");
            return;
        }
        // Appointment ID is in column 0
        int appointmentId = (int) tableModel.getValueAt(selectedRow, 0);

        // Confirm?
        int choice = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete Appointment ID: " + appointmentId + "?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            appointmentService.deleteAppointment(appointmentId);
            loadAppointmentsIntoTable();
        }
    }

    private void loadAppointmentsIntoTable() {
        tableModel.setRowCount(0); // clear existing rows

        List<Appointment> appointments = appointmentService.getAllAppointments();
        for (Appointment appt : appointments) {
            tableModel.addRow(new Object[]{
                appt.getAppointmentId(),
                appt.getCustomerId(),
                dateFormat.format(appt.getStartTime()),
                appt.getDuration(),
                appt.getStatus()
            });
        }
    }
}
