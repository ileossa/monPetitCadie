package testFenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

import dataBase.Connecteur;
import dataBase.PromotionMethodes;
import dataFactory.Promotion;

public class ManagePromotion {
	
	public static Connecteur con = new Connecteur();
	public static PromotionMethodes pm = new PromotionMethodes();
	public static List<Promotion> promoList = new ArrayList<Promotion>();
	
	public ManagePromotion(){
		
	}
		
	public static void managePromotion(){
		// Init the windows
		final JFrame fenetre = new JFrame("Gestion des promotions");
		
		String [] colonnes = {"id produit", "Magasin", "Description", "Date debut", "Date fin"};
	      
		final DefaultTableModel modele = new DefaultTableModel(colonnes, 0);
		JTable tableau = new JTable(modele);
		tableau.setAutoCreateRowSorter(true);
		
		fenetre .add(new JScrollPane(tableau));
		
		int toucheRaccourcis = java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		JMenuItem menuNouveau = new JMenuItem("Nouveau");
		menuNouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, toucheRaccourcis));
			
		setData(modele);
		
		menuNouveau.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				AddPromo ap = new AddPromo();
				int reponse = JOptionPane.showConfirmDialog(fenetre, ap, "Nouvelle Promotion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(reponse == JOptionPane.OK_OPTION){
					pm.insertNewPromotion(con, ap.getNomMag(), ap.getDesc(),ap.getDateDebut(), ap.getDateFin());
					JOptionPane.showMessageDialog(fenetre, "Promotion ajouté");
					setData(modele);
					
				}
			}
			
		});
		
		JMenuItem menuDelete = new JMenuItem("Supprimer");
		menuDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, toucheRaccourcis));
		
		menuDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				DeletePromotion dp = new DeletePromotion();
				int reponse = JOptionPane.showConfirmDialog(fenetre, dp, "Supprimer une Promotion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(reponse == JOptionPane.OK_OPTION){
					if(dp.getIdPromo()!=0){
						pm.deletePromotion(con, dp.getIdPromo());
						JOptionPane.showMessageDialog(fenetre, "Promotion supprimé");
						setData(modele);
					}else{
						JOptionPane.showMessageDialog(fenetre, "Vous n'avez selectionné aucune promotion");
					}

				}
			}	
		});
		
		JMenuItem menuUpdate = new JMenuItem("Modifier");
		menuUpdate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, toucheRaccourcis));
		
		menuUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				UpdatePromotion up = new UpdatePromotion();
				int reponse = JOptionPane.showConfirmDialog(fenetre, up, "Modifier un Produit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(reponse == JOptionPane.OK_OPTION){
					if(reponse == JOptionPane.OK_OPTION){
						if(up.getId_promo()!=0){
							pm.updatePromotion(con, up.getId_promo(), up.getNomMag(), up.getDesc(), up.getDateDebut(), up.getDateFin());
							JOptionPane.showMessageDialog(fenetre, "Promotion mise à jour");
							setData(modele);
						}else{
							JOptionPane.showMessageDialog(fenetre, "Vous n'avez selectionné aucune promotion");
						}

					}
				}
			}
		});
		
		
		JMenuItem menuQuitter = new JMenuItem("Quitter");
		menuQuitter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ev) {
				// TODO Auto-generated method stub
				if(JOptionPane.showConfirmDialog(fenetre, "Voulez-vous quitter ?", "Quitter", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		
		
		JMenuBar  barreMenu = new JMenuBar();
		fenetre.setJMenuBar(barreMenu);
		JMenu menuFichier = new JMenu("Fichier");
		barreMenu.add(menuFichier);
		menuFichier.add(menuNouveau);
		menuFichier.add(menuDelete);
		menuFichier.add(menuUpdate);
		menuFichier.add(menuQuitter);
		
		fenetre.setSize(1600, 900);
		fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		fenetre.setVisible(true);
		
		con.close();
		


	}
	
	public static void setData(DefaultTableModel modele){
		if (modele.getRowCount() > 0) {
		    for (int i = modele.getRowCount() - 1; i > -1; i--) {
		        modele.removeRow(i);
		    }
		}
		promoList = pm.listePromotion(con);
		for(Promotion promo:promoList){
			modele.addRow(new String[] {Integer.toString(promo.getId_promo()), promo.getNom_mag(), promo.getDesc_promo(), promo.getDate_debut(), promo.getDate_fin()});
		}
	}

}
