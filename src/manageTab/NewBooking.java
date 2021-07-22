package manageTab;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import resources.Connect;


public class NewBooking extends JInternalFrame implements ActionListener{

	//input: flight data & user data
	//output: booking data
	
	//getting time
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-mm-dd");
	LocalDateTime get_date;
	//
	boolean checked = false;
	private String qty, userid, flightid,notes;
	private int qyt ; 
	private Integer price;
	
	//lbl input muncul pas button di click
	private String xusername = "" , xairplane = "", xdeparture="", xdestination = "";
	private String xdate = "", xticket = "", xtotalprice ="", xdescription ="";

	//gak muncul (tampungan)
	private String Destination, Departure;
	
	JButton buttonTop, buttonBot;
	JSpinner txtQyt;
	JTextArea txtNotes;
	JComboBox<String> cbflightId ,cbuserId; 
	
	JLabel username, airplanename,departure,destination,date,ticketqty,totalprice,description;
	JLabel Ausername, Aairplanename,Adeparture,Adestination,Adate,Aticketqty,Atotalprice,Adescription;
//	JLabel strip = new JLabel("\t-");
	
	
	
	public NewBooking() {
		
		JPanel panelA = new JPanel(new BorderLayout());
		JPanel panelB = new JPanel(new BorderLayout());
		JPanel panelTop = new JPanel(new GridLayout(4,2,5,5));
		JPanel panelMid = new JPanel(new GridLayout(8,2));
		JPanel panelBot = new JPanel(new BorderLayout());
		JPanel panelButton1 = new JPanel();
		JPanel panelButton2 = new JPanel();
		Vector<String> userId_array = new Vector<>();
		Vector<String> flightId_array = new Vector<>();
		
		setVisible(true);
		setClosable(true);
		setResizable(false);
		setTitle("Insert New Booking");
		setSize(500,600);
		setLayout(new BorderLayout(3,3));
		
		//TOP( USERID,FLIGHTID,QYT,NOTES)
		Connect connect = new Connect();
		
		JLabel lblUserId = new JLabel("User Id");
		lblUserId.setFont(new Font("Comic Sans", Font.PLAIN, 18));
		panelTop.add(lblUserId);
		
		// array commbo box ---done
		Vector<String> arr_userid = new Vector<>();
		Vector<String> arr_flightid = new Vector<>();
		
			connect.resultSet = connect.executeQuery("SELECT UserID, UserName FROM Users usr");
					
				try {
				while(connect.resultSet.next()){
						arr_userid.add(connect.resultSet.getObject(1).toString()+" - "+connect.resultSet.getObject(2));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			cbuserId = new JComboBox<String>(arr_userid);
		
			
			
			connect.resultSet = connect.executeQuery("SELECT FlightID, cityFrom.CityName DepartureCity, cityTo.CityName DestinationCity	 FROM Flights fly INNER JOIN Cities cityFrom ON "
					+ "fly.DepartureCityID = cityFrom.CityName INNER JOIN Cities cityTo ON fly.DestinationCityID = cityTo.CityName");
					
				try {
				while(connect.resultSet.next()){
						arr_flightid.add(connect.resultSet.getObject(1).toString()+" - from "+connect.resultSet.getObject(2).toString()+ " to "+connect.resultSet.getObject(3).toString());
						Departure = connect.resultSet.getObject(2).toString();
						Destination = connect.resultSet.getObject(3).toString();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			cbflightId = new JComboBox<String>(arr_flightid);
		//
		
		
		panelTop.add(cbuserId);
		
		//
		JLabel lblFlightId = new JLabel("Flight Id");
		lblFlightId.setFont(new Font("Comic Sans", Font.PLAIN, 18));
		panelTop.add(lblFlightId);
		
		
		panelTop.add(cbflightId);
		
		//
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Comic Sans", Font.PLAIN, 18));
		panelTop.add(lblQuantity);
		
		txtQyt = new JSpinner();
		panelTop.add(txtQyt);
		
		//
		JLabel lblAdditionalNotes = new JLabel("Additional Notes");
		lblAdditionalNotes.setFont(new Font("Comic Sans", Font.PLAIN, 18));
		panelTop.add(lblAdditionalNotes);
		
		 txtNotes = new JTextArea();
		panelTop.add(txtNotes);
		//

		buttonTop = new JButton("Show Detailed Information");
		buttonTop.addActionListener(this);
		panelButton1.add(buttonTop);
		panelA.add(panelButton1, BorderLayout.SOUTH);
		panelA.add(panelTop, BorderLayout.CENTER);
		
		
		
		
		// ShowData (panelMId , panelB)
		panelB.setLayout(new BorderLayout(3,3));
		
		username = new JLabel("User's Name				");
		Ausername = new JLabel(xusername);
		
		airplanename = new JLabel("Airplane's Name		");
		Aairplanename = new JLabel(xairplane);
		
		departure = new JLabel("Departure				");
		Adeparture = new JLabel(xdeparture);
		
		destination = new JLabel("Destination			");
		Adestination = new JLabel(xdestination);
		
		date = new JLabel("Date							");
		Adate = new JLabel(xdate);
		
		ticketqty = new JLabel("Ticket's Qty			");
		Aticketqty = new JLabel(xticket);
		
		totalprice = new JLabel("Total Price			");
		Atotalprice = new JLabel(xtotalprice);
		
		description = new JLabel("Additional Description");
		Adescription = new JLabel(xdescription);
		
		panelMid.add(username);panelMid.add(Ausername);
		panelMid.add(airplanename);panelMid.add(Aairplanename);
		panelMid.add(departure);panelMid.add(Adeparture);
		panelMid.add(destination);panelMid.add(Adestination);
		panelMid.add(date);panelMid.add(Adate);
		panelMid.add(ticketqty);panelMid.add(Aticketqty);
		panelMid.add(totalprice);panelMid.add(Atotalprice);
		panelMid.add(description);panelMid.add(Adescription);
		
		buttonBot = new JButton("Create Booking");
		buttonBot.addActionListener(this);
		
		
		panelB.add(panelMid, BorderLayout.CENTER);
		panelButton2.add(buttonBot);
		panelB.add(panelButton2, BorderLayout.SOUTH);
		
		//Copyright
		JLabel copyright = new JLabel("©Lave's airline");
		copyright.setFont(new Font("Comic Sans", Font.BOLD, 16));
		panelBot.add(copyright, BorderLayout.EAST);
		
		try {
			setClosed(true);
		} catch (PropertyVetoException e) {
			
			e.printStackTrace();
		}
		
		add(panelA,BorderLayout.NORTH);
		add(panelB,BorderLayout.CENTER);
		add(panelBot,BorderLayout.SOUTH);
	}
	
	private String reverseString(String source){
		String reverse = "";
		
		if(source.isEmpty() || source == null) return source;
		
		for (int i = source.length()-1; i >= 0; i--) {
			reverse = reverse + source.charAt(i);
		}
			return reverse;
	}
	
	private void getData(){
		Connect connect = new Connect();
		String temp;
		
		userid= cbuserId.getSelectedItem().toString();
		temp = reverseString(userid);
		xusername = temp.substring(0,temp.indexOf(' '));
		userid = userid.substring(0,userid.indexOf(' '));
	
		flightid = cbflightId.getSelectedItem().toString();
		flightid= flightid.substring(0,flightid.indexOf(' '));
		
		try {
			connect.executeQuery("SELECT f.AirplaneID Airplane FROM Flights f INNER JOIN Ariplanes a ON f.AirplaneID = a.AirplaneID");
			while(connect.resultSet.next()){
				xairplane = connect.resultSet.getObject(1).toString();
				xdeparture = Departure;
				xdestination = Destination;
			}
			
			connect.executeQuery("SELECT FlightDate, FlightPrice FROM Flights");
			while(connect.resultSet.next()){
				xdate = connect.resultSet.getObject(1).toString();
				xtotalprice = connect.resultSet.getObject(2).toString() ;
				qyt = (int) txtQyt.getValue();
				price =  Integer.parseInt(xtotalprice) * qyt;
				xtotalprice = price.toString();
			}
				
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		
		
		qty = txtQyt.getValue().toString();
		notes = txtNotes.getText();
		
		
	}
	
	private boolean valid(){
		if(qyt > 0) return true;
		else return false;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		getData();
		if(e.getSource() == buttonTop && !valid() ){
			JOptionPane.showMessageDialog(this, "Quantity must be more than zero!");
		}
		
		if(e.getSource() == buttonTop && valid()){
			Ausername.setText(xusername);
			Aairplanename.setText(xairplane);
			Adeparture.setText(xdeparture);
			Adestination.setText(xdestination);
			Adate.setText(xdate);
			Aticketqty.setText(qty);
			Atotalprice.setText(xtotalprice);
			Adescription.setText(notes);
			checked = true;
		}
		
		if(checked == true && e.getSource() == buttonBot){
			Connect connect = new Connect();
			try {
				//Bookings//FlightID 	UserID 	BookingQty 	AdditionalDescription 
				connect.insertBooking(flightid, userid, qty, notes);
				checked = false;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(this, "Booking success!\n Thank you for choosing Levis's Airline®");
		}
		
		if(checked == false && e.getSource() == buttonBot){
			JOptionPane.showMessageDialog(this, "Please reinput Bookings!");
		}
		
		
		
	}

	
	
	

}
