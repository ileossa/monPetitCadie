package testFenetre;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dataBase.Connecteur;
import dataBase.ProductMethodes;
import dataBase.UserMethodes;
import dataFactory.Product;
import dataFactory.User;

public class ResetPassword extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Connecteur con = new Connecteur();
	
	public static UserMethodes um = new UserMethodes();
	List<User> userList = um.listeUser(con);
	
	public JComboBox<String> searchUser = new JComboBox<>();
	
	
	public static void main(String[] args){
		
	    
	}
	
	
	
	//constructeur de la class
	public ResetPassword(){
		JPanel panneauLabels = new JPanel(new GridLayout(2, 1, 5, 5));
		JLabel labelId = new JLabel("Réinitialiser le mot de passe de l'utilisateur: ");	
		panneauLabels.add(labelId);
		searchUser.addItem("");
	    for(User user: userList){
	    		searchUser.addItem(Integer.toString(user.getId_utilisateur()));    	
	    }
	    

	    panneauLabels.add(this.searchUser);

		setLayout(new BorderLayout(5,5));
		add(panneauLabels, BorderLayout.CENTER);
		
	}
	
	public User getUser(){
		User user = um.getUser(con, Integer.parseInt(this.searchUser.getItemAt(this.searchUser.getSelectedIndex())));
		return user;
	}
	
}
