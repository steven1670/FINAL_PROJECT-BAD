import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;



import resources.Connect;



public class UpdateForm extends JInternalFrame implements ActionListener{
	private String _username, _userid, _flightid, _currflightid;
	private String _ticket, _desc;
	private int qty;
	JPanel panel = new JPanel(new GridLayout(4,2));
	JButton update = new JButton("Update");
	
	JLabel lblusername, curruser, lblflightid, lblticket, lbldesc;
	JComboBox<String> cbflight;
	JSpinner spnticket;
	JTextField txtdesc;
	
	Vector<String> vCb;

	JDesktopPane dp;
	
	public UpdateForm(String usrid, String flightid)  {

		
		vCb = new Vector<>();
		vCb.add(flightid);
		_currflightid = flightid;
		_userid = usrid;
//		System.out.println(_userid);
//		System.out.println(_currflightid);
		Connect connect = new Connect();
		String name = null; //=
		connect.executeQuery("SELECT UserName FROM Users WHERE UserID LIKE '"+_userid+"'");
//		System.out.println("before");
		try {
			while(connect.resultSet.next())
			name= connect.resultSet.getObject(1).toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lblusername = new JLabel("User's Name");
		lblusername.setFont(new Font("Comic Sans", Font.PLAIN, 14));
		panel.add(lblusername);
		curruser= new JLabel(name);
		_username = _userid;
		panel.add(curruser);
		
		lblflightid = new JLabel("Flights ID");
		lblflightid.setFont(new Font("Comic Sans", Font.PLAIN, 14));
		panel.add(lblflightid);
		
		
		connect.executeQuery("SELECT FlightID FROM Flights");
		//WHERE FlightID NOT LIKE '"+_currflightid +"'"
		
		try {
			while(connect.resultSet.next())
			vCb.add(connect.resultSet.getObject(1).toString());
			System.out.println("ini update form");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cbflight = new JComboBox<String>(vCb);
		panel.add(cbflight);
		
		lblticket = new JLabel("Ticket Qty");
		panel.add(lblticket);
		
		spnticket = new JSpinner();
		panel.add(spnticket);
		
		lbldesc = new JLabel("Additional Desc");
		panel.add(lbldesc);
		
		txtdesc = new JTextField();
		panel.add(txtdesc);
		
		update.addActionListener(this);
		
		add(panel,BorderLayout.CENTER);
		add(update,BorderLayout.SOUTH);
	
		setVisible(true);
		setSize(500,300);
		setTitle("Update Bookings");
		setResizable(false);
		setClosable(true);
		
		try {
			setClosed(true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private void getData(){
		_userid = _userid;
		_username = _username;
		_flightid = cbflight.getSelectedItem().toString();
		_ticket = spnticket.getValue().toString();
		_desc = txtdesc.getText();
		qty = Integer.parseInt(_ticket);
		
	}

	private boolean valid(){
		if (qty <= 0) {
			return false;
		}
		else return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		getData();
		if(e.getSource() == update && valid()){
			Connect connect = new Connect();
			String query ="UPDATE Bookings SET FlightID = '"+_flightid+"',BookingQty = '"+
		 _ticket+"',"+"AdditionalDescription ='"+_desc +"'"+" WHERE UserID LIKE '"+_userid +"'";
			try {
				connect.executeUpdate(query);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "error");
			}
			JOptionPane.showMessageDialog(this, "Success");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
		}
		
		if(e.getSource() == update && !valid()){
			JOptionPane.showMessageDialog(this, "Qty must be more than 0");
		}
		
		
	}

}
