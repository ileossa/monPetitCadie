package testFenetre;

import javax.swing.JOptionPane;
import testFenetre.ManageProduct;

public class Saisie {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AddProduct panneau = new AddProduct();
		int reponse = JOptionPane.showConfirmDialog(null, panneau, "Nouveau Produit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if(reponse == JOptionPane.OK_OPTION){
			JOptionPane.showMessageDialog(null, "contact :\n" + 
												panneau.getId() + " " +
												panneau.getNom() + "\n" +
												panneau.getCategorie());
		}
	}
}
