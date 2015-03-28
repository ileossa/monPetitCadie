package testFenetre;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dataBase.Connecteur;
import dataBase.PromotionMethodes;
import dataFactory.Promotion;

public class UpdatePromotion extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Connecteur con = new Connecteur();
	
	public static PromotionMethodes pm = new PromotionMethodes();
	List<Promotion> promoList = pm.listePromotion(con);
	
	public JComboBox<String> searchPromo = new JComboBox<>();
	private JTextField saisieNomMag = new JTextField(10);	
	private JTextField saisieDateDebut = new JTextField(10);
	private JTextField saisieDateFin = new JTextField(10);
	private JTextArea saisieDescription = new JTextArea(2,20);
	
	
	public static void main(String[] args){
		con.close();
	    
	}
	
	
	
	//constructeur de la class
	public UpdatePromotion(){
		searchPromo.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(searchPromo.getSelectedItem().toString().equals("")==false){
		    		
			    	Promotion promotion = pm.getPromotion(con, Integer.parseInt(searchPromo.getSelectedItem().toString())); 
			    	saisieNomMag.setText(promotion.getNom_mag());
			    	saisieDescription.setText(promotion.getDesc_promo());
			    	saisieDateDebut.setText(promotion.getDate_debut());
			    	saisieDateFin.setText(promotion.getDate_fin());
		    	}

		    	
		    }
		});
		JPanel panneauLabels = new JPanel(new GridLayout(5, 2, 5, 5));
		JLabel labelId = new JLabel("Promotion à modifier:");	
		panneauLabels.add(labelId);
		searchPromo.addItem("");
	    for(Promotion promo: promoList){
	    		searchPromo.addItem(Integer.toString(promo.getId_promo()));    	
	    }
	    panneauLabels.add(this.searchPromo);
	    
		JLabel labelMag = new JLabel("Magasin:");	
		panneauLabels.add(labelMag);
		panneauLabels.add(this.saisieNomMag);
		JLabel labelDesc = new JLabel("Description:");
		panneauLabels.add(labelDesc);
		panneauLabels.add(this.saisieDescription);
		JLabel labelDd = new JLabel("Date Debut:");
		panneauLabels.add(labelDd);
		panneauLabels.add(this.saisieDateDebut);
		JLabel labelDf = new JLabel("Date fin:");
		panneauLabels.add(labelDf);
		panneauLabels.add(this.saisieDateFin);


		setLayout(new BorderLayout(5,5));
		add(panneauLabels, BorderLayout.CENTER);
	}
	
	public int getId_promo(){
		int id;
		if (searchPromo.getSelectedItem().toString().equals("")==false){
			id = Integer.parseInt(searchPromo.getSelectedItem().toString()); 
		}else{
			id = 0;
		}
		return id;
	}
	
	public String getNomMag(){
		return this.saisieNomMag.getText();
	}
	
	public String getDesc(){
		return this.saisieDescription.getText();
	}
	
	public String getDateDebut(){
		return this.saisieDateDebut.getText();
	}	
	
	public String getDateFin(){
		return this.saisieDateFin.getText();
	}	

}
