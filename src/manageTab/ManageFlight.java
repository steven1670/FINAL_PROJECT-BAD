package manageTab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import resources.Connect;

import java.time.format.DateTimeFormatter;

public class ManageFlight extends JInternalFrame implements ActionListener,ListSelectionListener{
//--date mesti +7 -belom
	Connect connect = new Connect();
	
	JPanel frameTable, frameForm , panelButton;
	JLabel lbltableTitle, lblflightID,lblairplaneName, lbldepartureCity, lbldestinationCity, lbldate, lblduration,lblPrice;
	JTable flight_list;
	DefaultTableModel dtm;
	JTextField txtFlightID, txtPrice;
	JComboBox<String> cbAirplane, cbCitiesFrom, cbCitiesTo, cbMonth,cbYear;
	JComboBox<String> cbDay;
	JSpinner spnDay, spnMonth, spnYear, spnDuration;
	JButton submit, delete;
	JDesktopPane dp;
	
	Vector<Object> vHeader, vRow;
	Vector<Vector<Object>> vContent;
	Vector<String> vyear;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
	LocalDateTime getDate = LocalDateTime.now();
	String arr_day[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	String arr_month[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	private final int yearRange = 5;
	private int baseYear;
	private int tempday,tempmonth,tempyear;
	Random rng = new Random();
	private String flightid, airplanename, departure, destination, date;
	private int duration,price;

	boolean checked = false;
	private String	error_message ="";
	
	Vector<String> vPlaneTemp, vCityTemp;
	private int citySelectionTo,citySelectionFrom, planeSelection;
	
	public ManageFlight() {
		//
		vyear = new Vector<>();
		vPlaneTemp = new Vector<>(); vCityTemp = new Vector<>();
		//
		frameTable = new JPanel();
		frameTable.setLayout(new BorderLayout());
		//
		vHeader = new Vector<>();
		vHeader.add("Flight ID");
		vHeader.add("Airplane Name");
		vHeader.add("Departure");
		vHeader.add("Destination");
		vHeader.add("Date");
		vHeader.add("Duration");
		vHeader.add("Price");
		//
		//import data
			Connect connect = new Connect();
			dtm = new DefaultTableModel(vHeader,0);
			
			lbltableTitle = new JLabel("Manage Airplane");
			lbltableTitle.setFont(new Font("Comic Sans",Font.BOLD,20));
			flight_list = new JTable(dtm);
			flight_list.setSize(new Dimension(220,370));
			
			connect.resultSet = connect.executeQuery("SELECT * FROM Flights");
			try {
				while(connect.resultSet.next()){
					vRow = new Vector<>();
					for (int i = 1; i <= connect.resultSetMetaData.getColumnCount(); i++) {
						//masukin dara ke-i kedalam vRow
						//pakai .toString() supaya data jadi String
						vRow.add(connect.resultSet.getObject(i).toString());
					}
					dtm.addRow(vRow);	
				}
	
				flight_list.setModel(dtm);
				
			} catch (SQLException eSQL) {
				eSQL.printStackTrace();
			}
			
			JScrollPane scpane = new JScrollPane(flight_list);
			frameTable.add(flight_list.getTableHeader(),BorderLayout.NORTH);
			frameTable.add(scpane,BorderLayout.CENTER);
		
		flight_list.getSelectionModel().addListSelectionListener(this);
		
		frameTable.setSize(new Dimension(380,400));
		frameTable.add(lbltableTitle, BorderLayout.NORTH);
		frameTable.add(flight_list, BorderLayout.CENTER);
		
		///////////frameFOrm
		frameForm = new JPanel();
		frameForm.setLayout(new GridLayout(7,2,3,3));
		
		lblflightID = new JLabel("Flight ID");
		lblflightID.setFont(new Font("Comic Sans",Font.BOLD, 16));
		
		txtFlightID = new JTextField(generateId());
		txtFlightID.setDisabledTextColor(getBackground());
//		txtFlightID.setB
		txtFlightID.disable();
		
		
		lblairplaneName = new JLabel("Airplane Name");
		lblairplaneName.setFont(new Font("Comic Sans",Font.BOLD, 16));
		
		connect.resultSet = connect.executeQuery("SELECT AirplaneName,AirplaneID FROM Ariplanes");
		Vector<String> vPlaneList = new Vector<>();
		Vector<String> vCityList = new Vector<>();
		vCityList.add("--Cities--");
		vPlaneList.add("--Airplanes--");
		try {
			while(connect.resultSet.next()){
				vPlaneList.add(connect.resultSet.getObject(1).toString());
				vPlaneTemp.add(connect.resultSet.getObject(2).toString());
			}
			cbAirplane = new JComboBox<String>(vPlaneList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connect.resultSet = connect.executeQuery("SELECT CityName,CityID FROM Cities");
		try {
			while(connect.resultSet.next()){
				vCityList.add(connect.resultSet.getObject(1).toString());
				vCityTemp.add(connect.resultSet.getObject(2).toString());
			}
			cbCitiesFrom = new JComboBox<String>(vCityList);
			cbCitiesTo = new JComboBox<String>(vCityList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lbldepartureCity = new JLabel("Departure");
		lbldepartureCity.setFont(new Font("Comic Sans",Font.BOLD, 16));
		lbldestinationCity = new JLabel("Destination");
		lbldestinationCity.setFont(new Font("Comic Sans",Font.BOLD, 16));
		
		lbldate = new JLabel("Date");
		lbldate.setFont(new Font("Comic Sans",Font.BOLD, 16));
		
		JPanel panelDate = new JPanel();
		
		JSpinner spntemp = new JSpinner();
		int w = spntemp.getWidth(); int h= spntemp.getHeight();
		Dimension d = new Dimension(w * 2, h);
		
		settingYear(); cbYear = new JComboBox<String>(vyear);
		spnDay = new JSpinner();	spnMonth = new JSpinner();		spnYear = new JSpinner();
		spnDay.setValue(1);			spnMonth.setValue(1);			spnYear.setValue(baseYear);
		spnDay.setPreferredSize(new Dimension(100,50)); 		spnMonth.setPreferredSize(new Dimension(100,50)); 		spnYear.setPreferredSize(new Dimension(100,50)); 
		
		
		panelDate.add(spnDay); panelDate.add(spnMonth); panelDate.add(spnYear);
		panelDate.setPreferredSize(new Dimension(400,100)); 
		
		lblduration = new JLabel("Duration");
		lblduration.setFont(new Font("Comic Sans",Font.BOLD, 16));
		
		spnDuration = new JSpinner();
		spnDuration.setValue(60);
		
		lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Comic Sans",Font.BOLD, 16));
		
		txtPrice = new JTextField();
		
		//
		panelButton = new JPanel();
		submit = new JButton("Submit");
		submit.addActionListener(this);
		delete = new JButton("Delete");
		delete.addActionListener(this);
		delete.setEnabled(false);
		panelButton.add(submit); panelButton.add(delete);
		
		
		
		frameForm.add(lblflightID); frameForm.add(txtFlightID);
		frameForm.add(lblairplaneName); frameForm.add(cbAirplane);
		frameForm.add(lbldepartureCity); frameForm.add(cbCitiesFrom);
		frameForm.add(lbldestinationCity); frameForm.add(cbCitiesTo);
		frameForm.add(lbldate); frameForm.add(panelDate);
		frameForm.add(lblduration); frameForm.add(spnDuration);
		frameForm.add(lblPrice); frameForm.add(txtPrice);
		
		//insert to internal frame
		
		setLayout(new BorderLayout(10,10));
		setSize(900,720);
		setTitle("Manage Flights");
		setResizable(false);
		setClosable(true);
		setVisible(true);
		try {
			setClosed(true);
		} catch (PropertyVetoException e) {
			
			e.printStackTrace();
		}
		
		
		add(frameTable, BorderLayout.NORTH);
		add(frameForm, BorderLayout.CENTER);
		add(panelButton,BorderLayout.SOUTH);
		
	}

	private void settingYear(){
		int maxYear = baseYear+yearRange;
		String iyear;
		vyear.clear();
		baseYear = Integer.parseInt(dtf.format(getDate));
		for (int i = baseYear; i <= maxYear; i++) {
			iyear = Integer.toString(i);
			vyear.add(iyear);
		}
		
	}
	
	private String generateId(){
		String id = "FLIG";
		int num1= rng.nextInt(10) , num2 = rng.nextInt(10),num3 = rng.nextInt(10);
		id = id + num1 + num2+ num3;
		flightid = id;
		return id;
	}
	
	private void getData(){
		flightid = flightid;
		airplanename=cbAirplane.getSelectedItem().toString();
		departure = cbCitiesFrom.getSelectedItem().toString();
		destination = cbCitiesTo.getSelectedItem().toString();
		date = spnYear.getValue().toString() +"-"+ spnMonth.getValue().toString()+"-" +spnDay.getValue().toString();
		tempday = Integer.parseInt(spnDay.getValue().toString());
		tempmonth = Integer.parseInt(spnMonth.getValue().toString());
		tempyear = Integer.parseInt(spnYear.getValue().toString());
		String temp = spnDuration.getValue().toString();
		duration = Integer.parseInt(temp);
		temp = txtPrice.getText();
		try {
			price = Integer.parseInt(temp);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			
		}
		
		citySelectionFrom = cbCitiesFrom.getSelectedIndex()-1;
		citySelectionTo = cbCitiesTo.getSelectedIndex()-1;
		planeSelection = cbAirplane.getSelectedIndex()-1;
	}
	
	private boolean logicDayTrueorNot(){
//		final int minimumGap = 7;
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		Date input_date =null;
		try {
			input_date = (Date) sdformat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LocalDateTime now = LocalDateTime.now().plusWeeks(1);
		Date curr_date = null;
		try {
			curr_date = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(input_date.after(curr_date))
			return true;
		
		return false;
	}
	
	private boolean valid(){
		if(airplanename.equals("--Airplanes--") ||departure.equals("--Cities--") ||destination.equals("--Cities--")){
			
			error_message = "Please choose Airplane Name, Departure and Destination";
			return false;
		}else if(!logicDayTrueorNot()){
			error_message = "Must be after 1 week";
			return false;
		}else if(duration < 60 || duration > 900){
			error_message = "Duration must be between 60 - 900";
			return false;
		}else if(txtPrice.getText().isEmpty()|| price%750000 !=0){
			error_message = "Price must be filled & multiple of 750000";
			return false;
		}
		
		
		error_message="";
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		getData();
		if(e.getSource() == submit && valid()){
			String Planeid = vPlaneTemp.get(planeSelection);
			String fromId = vCityTemp.get(citySelectionFrom);
			String toId = vCityTemp.get(citySelectionTo);
			connect.executeUpdate("INSERT INTO Flights VALUES '"+
				flightid+"','"+Planeid+"','"+fromId+"','"+toId+"','"+date+"','"+duration+"','"+price+"'");
		}
		if(e.getSource() == submit && !valid()){
			JOptionPane.showMessageDialog(this, error_message);
		}
		
		if(e.getSource() == delete && checked == true){
			int row = flight_list.getSelectedRow();
			String deleteid = flight_list.getValueAt(flight_list.getSelectedRow(), 0).toString();
			delete.setEnabled(false);
			
			connect.executeUpdate("DELETE FROM Flights WHERE FlightID LIKE '"+deleteid+"'");
			
			checked =false;
			JOptionPane.showMessageDialog(this, "Successfully deleted");
			dtm.fireTableRowsDeleted(row, row);
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		e.getValueIsAdjusting();
	
		checked = true;
		delete.setEnabled(true);
	
	}
	
}
