package testFenetre;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dataBase.Connecteur;

public class AddPromo extends JPanel {
	
	public static Connecteur con = new Connecteur();   

	private JTextField saisieNomMag = new JTextField(10);	
	private JTextField saisieDateDebut = new JTextField(10);
	private JTextField saisieDateFin = new JTextField(10);
	private JTextArea saisieDescription = new JTextArea(2,20);
		
	public static void main(String[] args){

	    
	}
	
	//constructeur de la class
	public AddPromo(){
		JPanel panneauLabels = new JPanel(new GridLayout(4, 1, 5, 5));
		JLabel labelMag = new JLabel("Magasin:");	
		panneauLabels.add(labelMag);
		JLabel labelDd = new JLabel("Date Debut:");
		panneauLabels.add(labelDd);
		JLabel labelDf = new JLabel("Date fin:");
		panneauLabels.add(labelDf);
		JLabel labelDesc = new JLabel("Description:");
		panneauLabels.add(labelDesc);
		//creation d une grille de 4 lignes et 1 colonne avec 5px d espacement
		JPanel panneauSaisie = new JPanel(new GridLayout(4,1,5,5));
		panneauSaisie.add(this.saisieNomMag);
		panneauSaisie.add(this.saisieDateDebut);
		panneauSaisie.add(this.saisieDateFin);
		panneauSaisie.add(this.saisieDescription);
		
		//instance du textArea pour l adresse
		setLayout(new BorderLayout(5,5));
		add(panneauLabels, BorderLayout.WEST);
		add(panneauSaisie, BorderLayout.CENTER);
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
