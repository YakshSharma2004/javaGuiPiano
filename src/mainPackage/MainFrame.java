package mainPackage;

import javax.swing.*;

public class MainFrame extends JFrame {

	// Within your MainFrame constructor:
	public MainFrame() {
	    super("Piano Tuning Management");
	    setSize(800, 600);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JTabbedPane tabbedPane = new JTabbedPane();

	    CustomerPanel customerPanel = new CustomerPanel();
	    AppointmentPanel appointmentPanel = new AppointmentPanel();
	    InvoicePanel invoicePanel = new InvoicePanel();

	    // NEW PANELS
	    TunerPanel tunerPanel = new TunerPanel();
	    PaymentPanel paymentPanel = new PaymentPanel();

	    tabbedPane.addTab("Customers", customerPanel);
	    tabbedPane.addTab("Appointments", appointmentPanel);
	    tabbedPane.addTab("Invoices", invoicePanel);

	    tabbedPane.addTab("Tuners", tunerPanel);
	    tabbedPane.addTab("Payments", paymentPanel);

	    add(tabbedPane);
	}



        // Run on Event Dispatch Thread
    	public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                LoginDialog loginDlg = new LoginDialog(null);
                loginDlg.setVisible(true);

                User logged = loginDlg.getAuthenticatedUser();
                if (logged != null) {
                    MainFrame frame = new MainFrame();
                    frame.setTitle("Piano Tuning Management  —  logged in as " + logged.getUsername());
                    frame.setVisible(true);
                } else {
                    // user closed dialog or failed login
                    System.exit(0);
                }
            });
        
    	}}
