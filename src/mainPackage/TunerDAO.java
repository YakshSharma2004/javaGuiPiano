package mainPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TunerDAO {

    public void addTuner(Tuner tuner) {
        String sql = "INSERT INTO tuners (first_name, last_name, phone, email) "
                   + "VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tuner.getFirstName());
            stmt.setString(2, tuner.getLastName());
            stmt.setString(3, tuner.getPhone());
            stmt.setString(4, tuner.getEmail());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Tuner> getAllTuners() {
        List<Tuner> tuners = new ArrayList<>();
        String sql = "SELECT tuner_id, first_name, last_name, phone, email FROM tuners";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int tunerId = rs.getInt("tuner_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                Tuner tuner = new Tuner(tunerId, firstName, lastName, phone, email);
                tuners.add(tuner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tuners;
    }

    // Optionally, you can add more methods like getTunerById, updateTuner, deleteTuner, etc.
}
