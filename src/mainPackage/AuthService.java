package mainPackage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/**
 * Simple authentication helper.
 * <p>
 * Usage example:
 * <pre>
 *     AuthService auth = new AuthService();
 *     User u = auth.login("admin", "admin123");
 *     if (u != null) {
 *         // success
 *     }
 * </pre>
 * <p>
 * ⚠️  In production prefer BCrypt/Argon2 over SHA‑256.
 */
public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Attempts to authenticate a user.
     *
     * @param username      case‑sensitive username
     * @param plainPassword raw password the user typed
     * @return the matching {@link User} on success, or {@code null} on failure
     */
    public User login(String username, String plainPassword) {
        if (username == null || plainPassword == null) {
            return null;
        }

        User user = userDAO.findByUsername(username.trim());
        if (user == null) {
            return null; // user not found
        }

        String hashedInput = sha256(plainPassword);
        return hashedInput.equals(user.getPasswordHash()) ? user : null;
    }

    // ---------------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------------

    /**
     * Creates a SHA‑256 hex string for the given text.
     * Replace with BCrypt/SCrypt for better security in real apps.
     */
    private String sha256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(text.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA‑256 not supported", e);
        }
    }
}
