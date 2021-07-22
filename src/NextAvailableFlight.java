import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import resources.Connect;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class NextAvailableFlight extends JInternalFrame{
	
	LocalDateTime getdate= LocalDateTime.now();
	DateTimeFormatter dtf;
	
	JLabel lblTitle;
	DefaultTableModel dtm;
	JTable table;
	JScrollPane scroll;
	JPanel panelTable, panelTitle;
	Vector<Object> vHeader, vRow;
	//id, airplane, departure, destination, date,duration, price
	public NextAvailableFlight(){
		
		setResizable(false);
		setClosable(true);
		setSize(600, 420);
		setTitle("Flight Details");
		setLayout(new BorderLayout(5,2));
		setVisible(true);
		try {
			setClosed(true);
		} catch (PropertyVetoException e) {
			
			e.printStackTrace();
		}
		
		panelTitle = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 10));
		panelTable = new JPanel(new BorderLayout(0,15));
		
		lblTitle = new JLabel("Next Available Flights");
		lblTitle.setFont(new Font("Comic Sans", Font.BOLD, 18));
		
		vHeader = new Vector<>();
		vHeader.add("ID");
		vHeader.add("Airplane Name");
		vHeader.add("Departure");
		vHeader.add("Destination");
		vHeader.add("Date");
		vHeader.add("Duration");
		vHeader.add("Price");
		
		Connect connect = new Connect();
		dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String curr_date = dtf.format(getdate);
		dtm = new DefaultTableModel(vHeader,0);
		table = new JTable(dtm);
		scroll = new JScrollPane(table);
		connect.executeQuery("SELECT * FROM Flights WHERE FlightDate >= '" +curr_date+"'");
		try {
			while(connect.resultSet.next()){
				vRow= new Vector<>();
				for (int i = 1; i <= connect.resultSetMetaData.getColumnCount(); i++) {
					vRow.add(connect.resultSet.getObject(i));
				}
				dtm.addRow(vRow);
			}
			table.setModel(dtm);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		panelTable.add(lblTitle,BorderLayout.NORTH);
		panelTable.add(scroll,BorderLayout.CENTER);
		
		
		
		
		
		add(panelTable,BorderLayout.CENTER);
	}
}
