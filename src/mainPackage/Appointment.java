package mainPackage;
import java.util.Date;

public class Appointment {
	private int appointmentId;
	private int customerId;
    private Date startTime;
    private int duration;
    private String status;
    
    public Appointment(int appointmentId, int customerId, Date startTime, int duration, String status) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.startTime = startTime;
        this.duration = duration;
        this.status = status;}
    
    public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
