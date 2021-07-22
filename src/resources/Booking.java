package resources;
import javax.swing.JInternalFrame;

public class Booking {

	private String userName;
	private String flightID;
	private String airplane;
	private String departure;
	private String destination;
	private int day;
	private String month;
	private int year;
	private int ticketQty;
	private String description;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFlightID() {
		return flightID;
	}
	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}
	public String getAirplane() {
		return airplane;
	}
	public void setAirplane(String airplane) {
		this.airplane = airplane;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getTicketQty() {
		return ticketQty;
	}
	public void setTicketQty(int ticketQty) {
		this.ticketQty = ticketQty;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Booking(String userName, String flightID, String airplane, String departure, String destination, int day,
			String month, int year, int ticketQty, String description) {
		super();
		this.userName = userName;
		this.flightID = flightID;
		this.airplane = airplane;
		this.departure = departure;
		this.destination = destination;
		this.day = day;
		this.month = month;
		this.year = year;
		this.ticketQty = ticketQty;
		this.description = description;
	}
	
	

}
