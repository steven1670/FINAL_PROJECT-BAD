import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import resources.Booking;
import resources.Connect;

public class PreviousFlight extends JInternalFrame implements ActionListener{
	JPanel pnlTop, pnlmid;
	JLabel lblTitle;
	JButton btnSubmit;
	JTable table;
	DefaultTableModel dtm;
	JScrollPane sp;
	Vector<Object> vHeader, vRow;
	Vector<Booking> bookings;
	Vector<Vector<Object>> vContent;

	
	public PreviousFlight() {
		Connect connect = new Connect();
		vHeader = new Vector<>();
		vContent = new Vector<>();
		
		vHeader.add("ID");
		vHeader.add("Airplane");
		vHeader.add("Departure");
		vHeader.add("Destination");
		vHeader.add("Date");
		vHeader.add("Duration");
		vHeader.add("Price");
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now;

		now = LocalDateTime.now();
		String curr_date = dtf.format(now);

		connect.executeQuery("SELECT * FROM Flights WHERE FlightDate < '"+ curr_date +"'");
		
		try {
			while(connect.resultSet.next()){
				vRow = new Vector<>();
				for (int i = 1; i <= connect.resultSetMetaData.getColumnCount(); i++) {
				vRow.add(connect.resultSet.getObject(i).toString());
				}
				vContent.add(vRow);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		pnlTop = new JPanel();
		pnlmid = new JPanel();
		
		dtm = new DefaultTableModel(vContent,vHeader);
		table = new JTable(dtm);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(700, 420));
		pnlmid.add(sp);
		
	
		lblTitle = new JLabel("Previous Flight ");
		lblTitle.setFont(new Font("Comic Sans", Font.BOLD, 18));
		pnlTop.add(lblTitle);
		
		add(pnlTop, BorderLayout.NORTH);
		add(pnlmid, BorderLayout.CENTER);
		
		
		
		setVisible(true);
		setSize(720,450);
		setClosable(true);
		try {
			setClosed(true);
		
		} catch (PropertyVetoException e) {
			
			e.printStackTrace();
		}
		setTitle("Flights Detail");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

}
