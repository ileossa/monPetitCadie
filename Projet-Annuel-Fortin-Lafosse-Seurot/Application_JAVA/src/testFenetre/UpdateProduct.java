package testFenetre;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dataBase.Connecteur;
import dataBase.ProductMethodes;
import dataFactory.Product;

public class UpdateProduct extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Connecteur con = new Connecteur();
	
	public static ProductMethodes pm = new ProductMethodes();
	List<Product> productList = pm.listeProduct(con);
	Product product = new Product();
	
	public JComboBox<String> searchProduct = new JComboBox<>();
	public JComboBox<String> saisieCategorie = new JComboBox<>();
	private JTextField saisieNom = new JTextField(10);
	private JTextField saisieId = new JTextField(10);
	
	
	public static void main(String[] args){
		
		con.close();
	    
	}
	
	
	
	//constructeur de la class
	public UpdateProduct(){
		saisieId.setEditable(false);
		JPanel panneauLabels = new JPanel(new GridLayout(4, 2, 5, 5));
		
		searchProduct.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(searchProduct.getSelectedItem().toString().equals("")==false){
		    		
			    	int id = pm.idProduct(con, searchProduct.getSelectedItem().toString()); 
			    	product = pm.getProduct(con, id );
			    	saisieId.setText(Integer.toString(product.getId_produit()));
			    	saisieNom.setText(product.getNom_produit());
			    	saisieCategorie.setSelectedItem(pm.nameProduct(con, product.getParent()));
		    	}

		    	
		    }
		});
		
		JLabel labelProduct = new JLabel("Produit à modifier:");	
		panneauLabels.add(labelProduct);
		List<Product> productList = pm.listeProduct(con);
		searchProduct.addItem("");
	    for(Product prod: productList){
	    	if(pm.numberProductInCategrorie(con, prod.getId_produit())==0){
	    		searchProduct.addItem(prod.getNom_produit());
	    	}	    	
	    }
	    List<Product> productListCategorie = pm.listeProductCategrorie(con);
		searchProduct.addItem("");
		saisieCategorie.addItem("");
	    for(Product prod: productListCategorie){
	    	saisieCategorie.addItem(prod.getNom_produit());
	    }	
	    panneauLabels.add(this.searchProduct);
	    
		JLabel labelId = new JLabel("Id:");	
		panneauLabels.add(labelId);
		panneauLabels.add(this.saisieId);
		JLabel labelNom = new JLabel("Nom:");
		panneauLabels.add(labelNom);
		panneauLabels.add(this.saisieNom);
		JLabel labelCategorie = new JLabel("Categorie:");
		panneauLabels.add(labelCategorie);
		panneauLabels.add(this.saisieCategorie);


		setLayout(new BorderLayout(5,5));
		add(panneauLabels, BorderLayout.CENTER);
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

