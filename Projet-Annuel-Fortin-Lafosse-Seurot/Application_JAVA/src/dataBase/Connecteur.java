package dataBase;

import java.sql.*;

public class Connecteur {
	
	Connection con;
	
	private String driver = "com.mysql.jdbc.Driver";
	//private String url = "jdbc:mysql://192.168.1.44:3306/basempc";
	private String url = "jdbc:mysql://127.0.0.1/basempc";
	private String login = "root";
	private String password = "";
	
	
	
	public void setDriver(String driver){
		this.driver = driver;
		close();
	}
	
	
	public void setLogin(String login){
		this.login = login;
		close();
	}
	
	public void setPassword(String password){
		this.password = password;
		close();
	}
	
	public void close(){
		try {
			if(this.con != null && !this.con.isClosed())
				this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Connection getConnexion() {
		Connection con;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, login, password);
			return con;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
}
