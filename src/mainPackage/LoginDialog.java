package mainPackage;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private final AuthService authService = new AuthService();
    private User authenticatedUser;

    public LoginDialog(Frame owner) {
        super(owner, "Login", true);
        buildUI();
    }

    // ----------------------------------------------------------- UI helpers
    private void buildUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        // ---------- center panel with fields
        JPanel fields = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblUser = new JLabel("Username:");
        JLabel lblPass = new JLabel("Password:");
        txtUsername = new JTextField(18);
        txtPassword = new JPasswordField(18);

        gbc.gridx = 0; gbc.gridy = 0; fields.add(lblUser, gbc);
        gbc.gridx = 1; fields.add(txtUsername, gbc);
        gbc.gridx = 0; gbc.gridy = 1; fields.add(lblPass, gbc);
        gbc.gridx = 1; fields.add(txtPassword, gbc);

        add(fields, BorderLayout.CENTER);

        // ---------- south panel with buttons
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> handleLogin());

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        btnPanel.add(btnCancel);
        btnPanel.add(btnLogin);
        add(btnPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnLogin);

        // ---------- set a fixed preferred size for a larger dialog
        setPreferredSize(new Dimension(420, 220));
        pack();
        setLocationRelativeTo(getOwner());
    }


    private void handleLogin() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both username and password.",
                    "Validation", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User logged = authService.login(user, pass);
        if (logged != null) {
            authenticatedUser = logged;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.",
                    "Login failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }
}
