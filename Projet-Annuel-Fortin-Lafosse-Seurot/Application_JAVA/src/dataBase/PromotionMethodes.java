package dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dataFactory.Product;
import dataFactory.Promotion;

public class PromotionMethodes extends Product {

	public PromotionMethodes(int id_produit, String nom_produit, int parent) {
		super(id_produit,nom_produit, parent);
		// TODO Auto-generated constructor stub
	}
	
	public PromotionMethodes() {

	}

	/***
	 * Insert new promotion in BDD
	 * @param connecteur
	 */
	public void insertNewPromotion(Connecteur connecteur, String nom_mag, String desc_promo, String date_debut, String date_fin){
		try(PreparedStatement addPromo = connecteur.getConnexion().prepareStatement("INSERT INTO promotion "+ 
																				" (nom_mag, desc_promo, date_debut, date_fin)" + 
																				" VALUES (?,?,?,?) "))
		{
			addPromo.setString(1, nom_mag);
			addPromo.setString(2, desc_promo);
			addPromo.setString(3, date_debut);
			addPromo.setString(4, date_fin);
			//execute requetes
			addPromo.executeUpdate();
		}//no catch, get exception in  executeUpdate()
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***
	 * Update promotion existing in BDD
	 * @param connecteur
	 */
	public void updatePromotion(Connecteur connecteur, int id, String nom_mag, String desc, String date_debut, String date_fin){
		try(PreparedStatement updatePromotion = connecteur.getConnexion().prepareStatement("UPDATE basempc.promotion SET nom_mag=?, desc_promo=?, date_debut=?, date_fin=? WHERE promotion.id_promo=? "))
		{
			updatePromotion.setString(1, nom_mag);
			updatePromotion.setString(2, desc);
			updatePromotion.setString(3, date_debut);
			updatePromotion.setString(4, date_fin);
			updatePromotion.setInt(5, id);
			//execute requetes
			updatePromotion.executeUpdate();
		}//no catch, get exception in  executeUpdate()
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/***
	 * find all promotion in BDD
	 * @param connecteur
	 * @return result of SQL request
	 */
	public List<Promotion> listePromotion(Connecteur connecteur){
		Statement st;
		ResultSet rs;
		List<Promotion> promoList = new ArrayList<Promotion>();
		
		try {
			st = connecteur.getConnexion().createStatement();
			String req = "SELECT * FROM promotion";
			rs = st.executeQuery(req);
			Promotion promo = null;
		     
		    while (rs.next()) {
		      promo = new Promotion();
		       
		      promo.setId_promo(rs.getInt(1));
		      promo.setNom_mag(rs.getString(2));
		      promo.setDesc_promo(rs.getString(3));
		      promo.setDate_debut(rs.getString(4));
		      promo.setDate_fin(rs.getString(5));
		 
		      promoList.add(promo);
		    } 
		    return promoList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			return null;	
	}
	
	/***
	 * find specific promotion in BDD
	 * @param connecteur
	 */
	public Promotion getPromotion(Connecteur connecteur, int id){
		Statement st;
		ResultSet rs;
		
		try {
			st = connecteur.getConnexion().createStatement();
			String req = "SELECT * FROM promotion WHERE id_promo = "+ id;
			rs = st.executeQuery(req);
			Promotion promo = null;
		     
		    if (rs.next()) {
		      promo = new Promotion();
		       
		      promo.setId_promo(rs.getInt(1));
		      promo.setNom_mag(rs.getString(2));
		      promo.setDesc_promo(rs.getString(3));
		      promo.setDate_debut(rs.getString(4));
		      promo.setDate_fin(rs.getString(5));
		      return promo;
		    }else{
		    	return null;
		    }
		    
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			return null;	
	}

	/***
	 * Delete promotion in DBB
	 * @param connecteur
	 */
	public void deletePromotion(Connecteur connecteur, int id){
		try(PreparedStatement deleteUser = connecteur.getConnexion().prepareStatement("DELETE FROM basempc.promotion WHERE id_promo=?"))
		{
			deleteUser.setInt(1, id);
			//execute requetes
			deleteUser.executeUpdate();
		}//no catch, get exception in  executeUpdate()
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
