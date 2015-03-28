package dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dataFactory.User;

public class UserMethodes extends User {

	public UserMethodes(int id_user, String nom, String email) {
		super(id_user, nom, email);
		// TODO Auto-generated constructor stub
	}
	
	public UserMethodes() {

	}
	
	/***
	 * find all user in BDD
	 * @param connecteur
	 */
	public List<User> listeUser(Connecteur connecteur){
		Statement st;
		ResultSet rs;
		List<User> userList = new ArrayList<User>();
		
		try {
			st = connecteur.getConnexion().createStatement();
			String req = "SELECT * FROM utilisateur";
			rs = st.executeQuery(req);
			User user = null;
		     
		    while (rs.next()) {
		      user = new User();
		       
		      user.setId_utilisateur(rs.getInt(1));
		      user.setName(rs.getString(2));
		      user.setEmail(rs.getString(3));

		      userList.add(user);
		    } 
		    return userList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			return null;	
	}
	
	/***
	 * find user in BDD
	 * @param connecteur
	 */
	public User getUser(Connecteur connecteur, int id){
		Statement st;
		ResultSet rs;
		
		try {
			st = connecteur.getConnexion().createStatement();
			String req = "SELECT * FROM utilisateur WHERE uid = " +id ;
			rs = st.executeQuery(req);
			User user = null;
		     
		    if (rs.next()) {
		      user = new User();
		       
		      user.setId_utilisateur(rs.getInt(1));
		      user.setName(rs.getString(2));
		      user.setEmail(rs.getString(3));

		    } 
		    return user;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			return null;	
	}
	
}
