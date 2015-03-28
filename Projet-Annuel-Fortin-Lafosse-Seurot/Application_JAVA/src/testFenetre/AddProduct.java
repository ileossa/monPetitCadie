package testFenetre;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dataBase.Connecteur;
import dataBase.ProductMethodes;
import dataFactory.Product;

public class AddProduct extends JPanel {
	
	public static Connecteur con = new Connecteur();
	
	public static ProductMethodes pm = new ProductMethodes();
	List<Product> productList = pm.listeProductCategrorie(con);


    

	public JComboBox<String> saisieCategorie = new JComboBox<>();
	private JTextField saisieNom = new JTextField(10);
	private JTextField saisieId = new JTextField(10);
	
	
	
	/*for(Product prod: productList){
		i++;
	}*/
	
	
	public static void main(String[] args){
		con.close();
	    
	}
	
	
	
	//constructeur de la class
	public AddProduct(){
		JPanel panneauLabels = new JPanel(new GridLayout(4, 1, 5, 5));
		JLabel labelId = new JLabel("Id:");	
		panneauLabels.add(labelId);
		JLabel labelNom = new JLabel("Nom:");
		panneauLabels.add(labelNom);
		JLabel labelCategorie = new JLabel("Categorie:");
		panneauLabels.add(labelCategorie);
		List<Product> productList = pm.listeProductCategrorie(con);
		saisieCategorie.addItem("");
	    for(Product prod: productList){
	    	saisieCategorie.addItem(prod.getNom_produit());
	    }		
		//creation d une grille de 4 lignes et 1 colonne avec 5px d espacement
		JPanel panneauSaisie = new JPanel(new GridLayout(3,1,5,5));
		panneauSaisie.add(this.saisieId);
		panneauSaisie.add(this.saisieNom);
		panneauSaisie.add(this.saisieCategorie);
		
		//instance du textArea pour l adresse
		setLayout(new BorderLayout(5,5));
		add(panneauLabels, BorderLayout.WEST);
		add(panneauSaisie, BorderLayout.CENTER);
	}
	
	public int getId(){
		return Integer.parseInt(this.saisieId.getText());
	}
	
	public String getNom(){
		return this.saisieNom.getText();
	}
	
	public int getCategorie(){
		int id = pm.idProduct(con, this.saisieCategorie.getItemAt(this.saisieCategorie.getSelectedIndex()));
		return id;
	}		
	

	

	

}
