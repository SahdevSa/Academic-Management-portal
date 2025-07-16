package org.stud.reg.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	public static Connection establishConnection() {
		
		Connection conn = null;
		
		try { 
			conn  = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydatabase","sahdev","Sahdev1115");
			
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return conn;
		
	}
}
