package testFenetre;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dataBase.Connecteur;
import dataBase.ProductMethodes;
import dataFactory.Product;

public class DeleteProduct extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Connecteur con = new Connecteur();
	
	public static ProductMethodes pm = new ProductMethodes();
	List<Product> productList = pm.listeProduct(con);
	
	public JComboBox<String> searchProduct = new JComboBox<>();
	
	
	public static void main(String[] args){
		
	    
	}
	
	
	
	//constructeur de la class
	public DeleteProduct(){
		JPanel panneauLabels = new JPanel(new GridLayout(2, 1, 5, 5));
		JLabel labelId = new JLabel("Produit à supprimer:");	
		panneauLabels.add(labelId);
		List<Product> productList = pm.listeProduct(con);
		searchProduct.addItem("");
	    for(Product prod: productList){
	    	if(pm.numberProductInCategrorie(con, prod.getId_produit())==0){
	    		searchProduct.addItem(prod.getNom_produit());
	    	}	    	
	    }

	    panneauLabels.add(this.searchProduct);

		setLayout(new BorderLayout(5,5));
		add(panneauLabels, BorderLayout.CENTER);
		
	}
	
	public int getIdProduct(){
		int id = pm.idProduct(con, this.searchProduct.getItemAt(this.searchProduct.getSelectedIndex()));
		return id;
	}

}
