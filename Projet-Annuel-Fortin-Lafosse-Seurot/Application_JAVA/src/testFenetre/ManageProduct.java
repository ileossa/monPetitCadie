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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

import dataBase.Connecteur;
import dataBase.ProductMethodes;
import dataFactory.Product;

public class ManageProduct extends JPanel{
	
	public static Connecteur con = new Connecteur();
	public static ProductMethodes pm = new ProductMethodes();
	public static List<Product> productList = new ArrayList<Product>();

	public ManageProduct(){
		
	}
	
	public static void manageProduct() {
		// Init the windows
		final JFrame fenetre = new JFrame("Gestion des Produits");
		
		String [] colonnes = {"id produit", "Nom", "Catégorie"};
	      
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
				AddProduct ap = new AddProduct();
				int reponse = JOptionPane.showConfirmDialog(fenetre, ap, "Nouveau Produit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(reponse == JOptionPane.OK_OPTION){
					pm.insertNewProduct(con, ap.getId(), ap.getNom(), ap.getCategorie());
					JOptionPane.showMessageDialog(fenetre, "Produit ajouté");
					setData(modele);
					
				}
			}
			
		});
		
		JMenuItem menuDelete = new JMenuItem("Supprimer");
		menuDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, toucheRaccourcis));
		
		menuDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				DeleteProduct dp = new DeleteProduct();
				int reponse = JOptionPane.showConfirmDialog(fenetre, dp, "Supprimer un Produit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(reponse == JOptionPane.OK_OPTION){
					if(dp.getIdProduct()!=0){
						pm.deleteProduct(con, dp.getIdProduct());
						JOptionPane.showMessageDialog(fenetre, "Produit supprimé");
						setData(modele);
					}else{
						JOptionPane.showMessageDialog(fenetre, "Vous n'avez selectionné aucun produit");
					}

				}
			}	
		});
		
		JMenuItem menuUpdate = new JMenuItem("Modifier");
		menuUpdate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, toucheRaccourcis));
		
		menuUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				UpdateProduct up = new UpdateProduct();
				int reponse = JOptionPane.showConfirmDialog(fenetre, up, "Modifier un Produit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(reponse == JOptionPane.OK_OPTION){
					if(reponse == JOptionPane.OK_OPTION){
						if(up.getId()!=0){
							pm.updateProduct(con, up.getId(), up.getNom(), up.getCategorie());
							JOptionPane.showMessageDialog(fenetre, "Produit mis à jour");
							setData(modele);
						}else{
							JOptionPane.showMessageDialog(fenetre, "Vous n'avez selectionné aucun produit");
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
		productList = pm.listeProduct(con);
		for(Product prod:productList){
			String nameParent;
			if(prod.getParent()!=0){
				nameParent = pm.nameProduct(con, prod.getParent());
			}else{
				nameParent = "-";
			}			
			modele.addRow(new String[] {Integer.toString(prod.getId_produit()), prod.getNom_produit(), nameParent});
		}
	}

}
