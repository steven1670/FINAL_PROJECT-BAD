package manageTab;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.DataTruncation;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import resources.Connect;

public class ManageCustomer extends JInternalFrame implements ActionListener{

	//id, name, dob, gender, phone number - >ID GENERATED RANDOMLY
	//output customer data -> DB (projectBAD)  ---done
	private String num = "\\d+";
	String id = " ", name = "",dob="" ,gender="", phone_number="";
	Random rng = new Random();
	
	
	JLabel lblId, lblName, lblDOB, lblGender, lblPhoneNumber, lblGeneratedID;
	JTextField txtName, txtPhoneNumber;
	JComboBox<String> cbmonth; JComboBox<Integer> cbday, cbyear;
	Integer day[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
	String month[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	Integer year[] = {1990,1991,1992,1993,1994,1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019};
	JRadioButton rbMale, rbFemale;
	ButtonGroup rbGender = new ButtonGroup(); 
	JButton btnAddCustomer = new JButton("Add Customer");
	
	JPanel panel = new JPanel(new GridLayout(5,2,1,1));
	JPanel panelDOB = new JPanel();
	JPanel panelGender = new JPanel();
	JPanel panelButton = new JPanel();
	
	public ManageCustomer() {
		
		setVisible(true);
		setClosable(true);
		try {
			setClosed(true);
		} catch (PropertyVetoException e) {
			
			e.printStackTrace();
		}
		setResizable(false);
		setTitle("Insert New Customer");
		setSize(475,500);
		setLayout(new BorderLayout(1,1));
		
		//
		id = generateId();
		lblGeneratedID = new JLabel(id);
		lblId = new JLabel("ID");
		
		lblName = new JLabel("Name");
		txtName = new JTextField();
		
		lblDOB = new JLabel("Date of Birth");
		cbday = new JComboBox<>(day); cbmonth = new JComboBox<>(month); cbyear = new JComboBox<>(year);
		panelDOB.add(cbday);panelDOB.add(cbmonth);panelDOB.add(cbyear);
		
		lblGender = new JLabel("Gender");
		rbMale= new JRadioButton("Male"); rbFemale = new JRadioButton("Female");
		rbGender.add(rbMale); rbGender.add(rbFemale);
		panelGender.add(rbMale); panelGender.add(rbFemale);
		
		lblPhoneNumber = new JLabel("Phone Number");
		txtPhoneNumber = new JTextField(20);
		
		
		btnAddCustomer.addActionListener(this);
		panelButton.add(btnAddCustomer);
		
		
		//adding to panel form
		panel.add(lblId); panel.add(lblGeneratedID);
		panel.add(lblName); panel.add(txtName);
		panel.add(lblDOB); panel.add(panelDOB);
		panel.add(lblGender); panel.add(panelGender);
		panel.add(lblPhoneNumber); panel.add(txtPhoneNumber);
		
		add(panel,BorderLayout.CENTER);
		add(panelButton, BorderLayout.SOUTH);
		
	}
	//id, name, dob, gender, phone number - >ID GENERATED RANDOMLY
	private String generateId(){
		String id = "US";
		int num1= rng.nextInt(10) , num2 = rng.nextInt(10),num3 = rng.nextInt(10);
		id = id + num1 + num2+ num3;
		return id;
	}
	
	private void getData(){
		id = id;
		name = txtName.getText();
		dob = cbyear.getSelectedItem().toString() + "-" + (cbmonth.getSelectedIndex()+1) + "-" + cbday.getSelectedItem().toString();
		phone_number = txtPhoneNumber.getText();
		
		if(rbMale.isSelected()) gender = "Male";
		else if(rbFemale.isSelected()) gender = "Female";
		else gender = "not choosen";
		
//		gender = rbMale.isSelected() ? "Male"  :	"Female";
	}
	
	private String EmptyField(){
		if(name.isEmpty() && phone_number.isEmpty()){return "User name and phone number";}
		else if(name.isEmpty()){ return "User name";}
		else if(gender.equals("not choosen")) return "Gender";
		else if(!phone_number.matches(num)){return "Numbers only (Phone Number)";}
		else {return "User phone number";} 
	}
		
	private boolean isEmpty(){
		if(name.isEmpty() || phone_number.isEmpty() || !phone_number.matches(num) || gender.equals("not choosen")){
			return true;
		}
		else return false;
	}
	
	private void clearField(){
		txtName.setText("");
		dob = cbyear.getSelectedItem().toString() + "-" + cbmonth.getSelectedIndex()+1 + "-" + cbday.getSelectedItem().toString();
		txtPhoneNumber.setText("");
		gender = "not choosen";
		
	}
	

	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == btnAddCustomer){
			getData(); 
				if(isEmpty()) {
				String message = EmptyField();
				JOptionPane.showMessageDialog(this, message + " must be filled!");
				}// is empty
				
				else {
				//import data to db (yyyy-mm-dd) ---done
					//output customer data -> db_projectBAD

					Connect connect = new Connect();
					
					try {
						connect.createNewUser(id, name, dob, phone_number, gender);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(this, "Data truncation: Incorrect date value: "+dob);
					}
					
					System.out.println("Sent DAta");
					
					lblGeneratedID.setText(generateId());
					clearField();
				}
				
		}//button action
		
	}

}
