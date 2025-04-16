package mainPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    
    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments ( customer_id, start_time, duration, status) "
                   + "VALUES ( ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
//            stmt.setInt(1, appointment.getAppointmentId());
            stmt.setInt(1, appointment.getCustomerId());
            // Use Timestamp for DATETIME fields
            stmt.setTimestamp(2, new Timestamp(appointment.getStartTime().getTime()));
            stmt.setInt(3, appointment.getDuration());
            stmt.setString(4, appointment.getStatus());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAppointmentById(int appointmentId) {
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appointmentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT appointment_id, customer_id, start_time, duration, status FROM appointments";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int appointmentId = rs.getInt("appointment_id");
                int customerId = rs.getInt("customer_id");
                Timestamp timestamp = rs.getTimestamp("start_time");
                int duration = rs.getInt("duration");
                String status = rs.getString("status");
                
                Appointment appointment = new Appointment(
                    appointmentId,
                    customerId,
                    new java.util.Date(timestamp.getTime()), 
                    duration, 
                    status
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
}
