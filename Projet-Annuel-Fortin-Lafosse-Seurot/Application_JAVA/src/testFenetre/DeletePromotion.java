package testFenetre;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dataBase.Connecteur;
import dataBase.PromotionMethodes;
import dataFactory.Promotion;

public class DeletePromotion extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Connecteur con = new Connecteur();
	
	public static PromotionMethodes pm = new PromotionMethodes();
	List<Promotion> promoList = pm.listePromotion(con);
	
	public JComboBox<String> searchPromo = new JComboBox<>();
	
	
	public static void main(String[] args){
		con.close();
	    
	}
	
	
	
	//constructeur de la class
	public DeletePromotion(){
		JPanel panneauLabels = new JPanel(new GridLayout(2, 1, 5, 5));
		JLabel labelId = new JLabel("Promotion à supprimer:");	
		panneauLabels.add(labelId);
		searchPromo.addItem("");
	    for(Promotion promo: promoList){
	    		searchPromo.addItem(Integer.toString(promo.getId_promo()));    	
	    }

	    panneauLabels.add(this.searchPromo);

		setLayout(new BorderLayout(5,5));
		add(panneauLabels, BorderLayout.CENTER);
	}
	
	public int getIdPromo(){
		int id;
		if(this.searchPromo.getItemAt(this.searchPromo.getSelectedIndex()).equals("")==false){
			id = Integer.parseInt(this.searchPromo.getItemAt(this.searchPromo.getSelectedIndex()));
		}else{
			id = 0;
		}
		return id;
	}

}
