
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import resources.Booking;
import resources.Connect;

public class ViewAllBookings extends JInternalFrame implements ActionListener, ListSelectionListener{
	JPanel pnlTop, pnlmid;
	JLabel lblTitle;
	JButton btnSubmit;
	JTable table;
	DefaultTableModel dtm;
	JScrollPane sp;
	Vector<Object> vHeader, vRow;
	Vector<String> vUser;
	Vector<Booking> bookings;
	Vector<Vector<Object>> vContent;
	
	private boolean checked = false;
	public ViewAllBookings() {
		vHeader = new Vector<>();
		vContent = new Vector<>();
		vUser = new Vector<>();
		
		vHeader.add("User's Name");
		vHeader.add("FlightID");
		vHeader.add("Airplane");
		vHeader.add("Departure");
		vHeader.add("Destination");
		vHeader.add("Date");
		vHeader.add("Ticket's Qty");
		vHeader.add("Description");
		vHeader.add("");
		
		Connect connect= new Connect();
		String query = "SELECT UserName,b.FlightID,AirplaneName, DepartureCityID,DestinationCityID,FlightDate,BookingQty,AdditionalDescription,u.UserID FROM users u,flights f,cities c,bookings b, ariplanes a WHERE"
			+" a.AirplaneID= f.AirplaneID AND f.FlightID = b.FlightID AND b.UserID= u.UserID AND f.DestinationCityID= c.CityID";			
		
		connect.executeQuery(query);
		// "SELECT u.UserName AS [User's Name],f.FlightID,AirplaneName AS [Airplane's Name], 
		// fromm.CityName Departure, to.CityName Destination, FlightDate AS Date, BookingQty AS [Ticket Quantity], AdditionalDescription AS [Descriptions]
		// FROM Bookings b INNER JOIN Users u ON b.UserID = u.UserID 
		// INNER JOIN Flight f ON f.FlightID = b.FlightID
		// INNER JOIN Ariplane a ON a.AirplaneID = f.AirplaneID
		// INNER JOIN Cities fromm ON fromm.CityID = f.CityID
		// INNER JOIN Cities to ON to.CityID = f.CityID"
		//
		
		
		// username, flightid, airplanename, departure, destination, date, ticket qty, desc
		try {
			while(connect.resultSet.next()){
				vRow = new Vector<>();
				for (int i = 1; i <= 9; i++) {
					vRow.add(connect.resultSet.getObject(i).toString());
					
				}
//					//--ini bingung query salah
//					//ke 9-UserID
//					vUser.add(connect.resultSet.getObject(9).toString());
				vContent.add(vRow);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		pnlTop = new JPanel(new GridLayout(1,2));
		pnlmid = new JPanel();
		
		dtm = new DefaultTableModel(vContent,vHeader);
		table = new JTable(dtm);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.getColumnModel().getColumn(5).setPreferredWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(250);
		table.getColumnModel().getColumn(8).setPreferredWidth(1);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(8));
		table.getSelectionModel().addListSelectionListener(this);
		sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(1000, 420));
		pnlmid.add(sp);
		
	
		lblTitle = new JLabel("Booking Details");
		pnlTop.add(lblTitle);
		
		btnSubmit = new JButton("Update Bookings");
		btnSubmit.addActionListener(this);
		pnlTop.add(btnSubmit);
		
		
		
		add(pnlTop, BorderLayout.NORTH);
		add(pnlmid, BorderLayout.CENTER);
		
		setVisible(true);
		setSize(1100,500);
		setClosable(true);
		setResizable(false);
		try {
			setClosed(true);
		} catch (PropertyVetoException e) {
			
			e.printStackTrace();
		}
		setTitle("Course");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == btnSubmit && checked == true){
			String flyid = table.getValueAt(table.getSelectedRow(), 2).toString();
			int index = table.getSelectedRow()+1;
//			String bla = "SELECT UserID From users where UserName = '" + table.getValueAt(index, 1).toString() +"'";
//			Connect connecto= new Connect();
//			connecto.resultSet=connecto.executeQuery(bla);
//			try {
//				while(connecto.resultSet.next()){
//					vRow = new Vector<>();
//					System.out.println(connecto.resultSet.getObject(1).toString());
//					}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			System.out.println(index);
			String usrid = table.getModel().getValueAt(index-1, 8).toString();
			
//			System.out.println("Tes");
			JDesktopPane dp = getDesktopPane();
			UpdateForm form = new UpdateForm(usrid,flyid);
			//--ini bingung (kyknya gabisa muncul)
			this.dispose();
			dp.add(form);
			form.show();
		}
		
		if(arg0.getSource() == btnSubmit && checked ==false){
			JOptionPane.showMessageDialog(this, "Pick any booking first ");
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		e.getValueIsAdjusting();
		
		checked =true;
	}

}
