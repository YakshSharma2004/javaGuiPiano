package mainPackage;
import java.util.*;

public class AppointmentService {
private AppointmentDAO appointmentDAO = new AppointmentDAO();
    
    public void scheduleAppointment(int id, int customerId, Date startTime, int duration, String status) {
        Appointment appointment = new Appointment(id, customerId, startTime, duration, status);
        appointmentDAO.addAppointment(appointment);
    }
    public void deleteAppointment(int appointmentId) {
        appointmentDAO.deleteAppointmentById(appointmentId);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }
}
