package resources;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Connect {
	public ResultSet resultSet;
	Statement statement;
	Connection connection;
	PreparedStatement preparedStatement;
	public ResultSetMetaData resultSetMetaData;

	public Connect() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName("com.mysql.jdbc.Driver");

            // Nanti prk nya diubah sesuai dengan nama db yang mau di connect
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_projectbad","root","");
            statement = connection.createStatement();  
            
//            System.out.println("Connected to the database..");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error Connection");
		}
	}
	
	
	public ResultSet executeQuery(String query){
		try {
			resultSet = statement.executeQuery(query);
			resultSetMetaData = resultSet.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public void executeUpdate(String query){
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 
	public void createNewUser(String userID, String userName,String userDOB, String userPhone, String userGender){
		try {
			//
			preparedStatement = connection.prepareStatement("INSERT INTO Users(UserID,UserName,UserDOB,UserPhone,UserGender) "+
					"VALUES(?,?,?,?,?)");
			
			preparedStatement.setString(1,userID);
			preparedStatement.setString(2,userName);
			preparedStatement.setString(3,userDOB);
			preparedStatement.setString(4,userPhone);
			preparedStatement.setString(5,userGender);

			//jalani insert query
			preparedStatement.execute();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),"Messages",JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		
	}
	
	
	
	
		//Bookings//FlightID 	UserID 	BookingQty 	AdditionalDescription 
	public void insertBooking(String flightID, String userID,String bookingqty, String description){
			try {
				//
				preparedStatement = connection.prepareStatement("INSERT INTO Bookings(FlightID,UserID,BookingQty,AdditionalDescription) "+
						"VALUES(?,?,?,?)");
				
				preparedStatement.setString(1,flightID);
				preparedStatement.setString(2,userID);
				preparedStatement.setString(3,bookingqty);
				preparedStatement.setString(4,description);
	
				//jalani insert query
				preparedStatement.execute();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	
}
