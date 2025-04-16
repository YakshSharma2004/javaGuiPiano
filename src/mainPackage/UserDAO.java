package mainPackage;

import java.sql.*;

public class UserDAO {

    /** Returns the matching user or null if not found */
    public User findByUsername(String username) {
        String sql = "SELECT user_id, username, password_hash, role FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
