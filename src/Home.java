

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


import manageTab.ManageCustomer;
import manageTab.ManageFlight;
import manageTab.NewBooking;

public class Home extends JFrame implements ActionListener {
	JMenuBar menuBar;
	JMenu menuView, menuManage;
	JMenuItem miViewallBookings,miNextFlights,miPreviousFlight,miNewBookings,miManageCustomer,miManageFlights;
	public JDesktopPane dp;
	
	public Home() {
		dp = new JDesktopPane();
		setContentPane(dp);
		
		
		ImageIcon img = new ImageIcon("planess.jpg");
		JLabel background = new JLabel("",img,JLabel.CENTER);
		background.setBounds(0,0,1980-45, 1080-5);
		add(background);
		
		//JLabel picLabel = new JLabel(new ImageIc);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menuView = new JMenu("View");
		menuManage = new JMenu("Manage");
		

		miViewallBookings = new JMenuItem("View All Bookings");
		miViewallBookings.addActionListener(this);
		miNextFlights = new JMenuItem("Next Flights");
		miNextFlights.addActionListener(this);
		miPreviousFlight = new JMenuItem("Previous Flights");
		miPreviousFlight.addActionListener(this);
		miNewBookings = new JMenuItem("New Bookings");
		miNewBookings.addActionListener(this);
		miManageCustomer = new JMenuItem("Manage Customer");
		miManageCustomer.addActionListener(this);
		miManageFlights = new JMenuItem("Manage Flights");
		miManageFlights.addActionListener(this);
		
		menuView.add(miViewallBookings);
		menuView.add(miNextFlights);
		menuView.add(miPreviousFlight);
		menuManage.add(miNewBookings);
		menuManage.add(miManageCustomer);
		menuManage.add(miManageFlights);
		
		
		
		
		menuBar.add(menuView);
		menuBar.add(menuManage);

		setVisible(true);
		setTitle("Levi's Airplanes");
		setExtendedState(MAXIMIZED_BOTH);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == miViewallBookings) {
			ViewAllBookings viewallbookings = new ViewAllBookings();
			dp.add(viewallbookings);
			viewallbookings.show();
		}
		if(arg0.getSource() == miNextFlights)
		{
			NextAvailableFlight nextavailableflight = new NextAvailableFlight();
			dp.add(nextavailableflight);
			nextavailableflight.show();
			
		}
		if(arg0.getSource() == miPreviousFlight)
		{
			PreviousFlight previousflight = new PreviousFlight();
			dp.add(previousflight);
			previousflight.show();
		}
		
		if (arg0.getSource() == miNewBookings) {
			NewBooking newbooking = new NewBooking();
			dp.add(newbooking);
			newbooking.show();
		}
		
		if(arg0.getSource() == miManageCustomer){
			ManageCustomer mcustomer = new ManageCustomer();
			dp.add(mcustomer);
			mcustomer.show();
		}
		
		if(arg0.getSource() == miManageFlights){
			ManageFlight mflight = new ManageFlight();
			dp.add(mflight);
			mflight.show();
		}
		
		
	}

}
