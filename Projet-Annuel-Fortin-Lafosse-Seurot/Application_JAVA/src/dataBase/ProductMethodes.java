package dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dataFactory.Product;

public class ProductMethodes extends Product {

	public ProductMethodes(int id_produit, String nom_produit, int parent) {
		super(id_produit,nom_produit, parent);
		// TODO Auto-generated constructor stub
	}
	
	public ProductMethodes() {

	}

	/***
	 * Insert new product in BDD
	 * @param connecteur
	 * @throws SQLException
	 */
	public void insertNewProduct(Connecteur connecteur,int id_produit, String nom_produit, int parent){
		try(PreparedStatement addProduct = connecteur.getConnexion().prepareStatement("INSERT INTO produit "+ 
																				" (id_produit, nom_produit, parent)" + 
																				" VALUES (?,?,?) "))
		{
			addProduct.setInt(1, id_produit);
			addProduct.setString(2, nom_produit);
			addProduct.setInt(3, parent);
			//execute requetes
			addProduct.executeUpdate();
		}//no catch, get exception in  executeUpdate()
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connecteur.close();
		}
	}

	/***
	 * Update product existing in BDD
	 * @param connecteur
	 * @throws SQLException
	 */
	public void updateProduct(Connecteur connecteur, int id, String nom, int parent){
		try(PreparedStatement updateProduct = connecteur.getConnexion().prepareStatement("UPDATE basempc.produit SET nom_produit=?, parent=? WHERE produit.id_produit=? "))
		{
			updateProduct.setString(1, nom);
			updateProduct.setInt(2, parent);
			updateProduct.setInt(3, id);
			//execute requetes
			updateProduct.executeUpdate();
		}//no catch, get exception in  executeUpdate()
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connecteur.close();
		}
	}
	
	
	/***
	 * Delete product in DBB
	 * @param connecteur
	 */
	public void deleteProduct(Connecteur connecteur, int id){
		try(PreparedStatement deleteUser = connecteur.getConnexion().prepareStatement("DELETE FROM basempc.produit WHERE id_produit=?"))
		{
			deleteUser.setInt(1, id);
			//execute requetes
			deleteUser.executeUpdate();
		}//no catch, get exception in  executeUpdate()
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connecteur.close();
		}
	}
	
	
	/***
	 * find all product in BDD
	 * @param connecteur
	 * @return result of SQL request
	 */
	public List<Product> listeProduct(Connecteur connecteur){
		Statement st;
		ResultSet rs;
		List<Product> productList = new ArrayList<Product>();
		
		try {
			st = connecteur.getConnexion().createStatement();
			String req = "SELECT * FROM produit";
			rs = st.executeQuery(req);
			Product product = null;
		     
		    while (rs.next()) {
		      product = new Product();
		      System.out.println("id: "+rs.getInt(1)+" nom: "+rs.getString(2));
		       
		      product.setId_produit(rs.getInt(1) );
		      product.setNom_produit( rs.getString(2) );
		      product.setParent( rs.getInt(3) );
		 
		      productList.add(product);
		    } 
		    return productList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connecteur.close();
		}
		
		
			return null;	
	}
	
	/***
	 * find all product categorie in BDD
	 * @param connecteur
	 * @return result of SQL request
	 */
	public List<Product> listeProductCategrorie(Connecteur connecteur){
	Statement st;
	ResultSet rs;
	List<Product> productList = new ArrayList<Product>();
	
	try {
		st = connecteur.getConnexion().createStatement();
		String req = "SELECT * FROM produit WHERE parent = 0";
		rs = st.executeQuery(req);
		Product product = null;
	     
	    while (rs.next()) {
	      product = new Product();
	      System.out.println("id: "+rs.getInt(1)+" nom: "+rs.getString(2));
	       
	      product.setId_produit(rs.getInt(1) );
	      product.setNom_produit( rs.getString(2) );
	      product.setParent( rs.getInt(3) );
	 
	      productList.add(product);
	    } 
	    return productList;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		connecteur.close();
	}
		return null;	
	}
	
	/***
	 * number of product in categorie
	 * @param connecteur
	 */
	public int numberProductInCategrorie(Connecteur connecteur, int id){
	Statement st;
	ResultSet rs;
	
	try {
		st = connecteur.getConnexion().createStatement();
		String req = "SELECT * FROM produit WHERE parent = " + id;
		rs = st.executeQuery(req);
		int cpt = 0;
	     
	    while (rs.next()) {
	    	cpt++;
	    } 
	    return cpt;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		connecteur.close();
	}
		return 0;	
	}
	
	/***
	 * find specific product in BDD
	 * @param connecteur
	 */
	public Product getProduct(Connecteur connecteur, int id){
	Statement st;
	ResultSet rs;	
	try {
		st = connecteur.getConnexion().createStatement();
		String req = "SELECT * FROM produit WHERE id_produit="+ id ;
		rs = st.executeQuery(req);
		Product product;
	     
		if (rs.next()) {
			 product = new Product();
		       
		     product.setId_produit(rs.getInt(1) );
		     product.setNom_produit( rs.getString(2) );
		     product.setParent( rs.getInt(3) );
		     
		     return product;
	    }else {
	    	return null;
	    }
	    
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		connecteur.close();
	}
		return null;	
	}
	
	
	
	/***
	 * find name of a specific product in BDD
	 * @param connecteur
	 * @return result of SQL request
	 */
	public String nameProduct(Connecteur connecteur, int id){
	Statement st;
	ResultSet rs;	
	try {
		st = connecteur.getConnexion().createStatement();
		String req = "SELECT nom_produit FROM produit WHERE id_produit="+ id ;
		rs = st.executeQuery(req);
		String nom;
	     
		if (rs.next()) {
			 nom = rs.getString(1);
			 return nom;
	    }else {
	    	return null;
	    }
	    
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		connecteur.close();
	}
		return null;	
	}
	
	/***
	 * find id of a specific product by name in BDD
	 * @param connecteur
	 * @return result of SQL request
	 */
	public int idProduct(Connecteur connecteur, String name){
	Statement st;
	ResultSet rs;	
	try {
		st = connecteur.getConnexion().createStatement();
		String req = "SELECT id_produit FROM produit WHERE nom_produit='"+ name +"'";
		rs = st.executeQuery(req);
		int id;
	     
		if (rs.next()) {
			 id = rs.getInt(1);
			 return id;
	    }else {
	    	return 0;
	    }
	    
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		connecteur.close();
	}
		return 0;	
	}
	
	
	
	/***
	 * find only name product specify
	 * @param connecteur
	 * @param nameP
	 * @return result of SQL request
	 * @throws SQLException
	 */
	/*public ResultSet findNameProduct(Connecteur connecteur, String nameP)throws SQLException{
		ResultSet res = null;
		try(PreparedStatement listeProduct = connecteur.getConnexion().prepareStatement("SELECT * FROM produit WHERE nom_produit LIKE ?"))
		{
			listeProduct.setString(1, getNom_produit());
			//execute requetes
			res = listeProduct.executeQuery();
		}//no catch, get exception in  executeUpdate()
		return res;		
	}*/
	
	
	
	
	

}
