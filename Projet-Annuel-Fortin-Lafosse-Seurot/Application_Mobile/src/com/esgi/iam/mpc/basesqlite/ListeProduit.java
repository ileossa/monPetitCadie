package com.esgi.iam.mpc.basesqlite;

public class ListeProduit {

	int id_liste;
	int id_produit;
	String qte_produit;
	
	//constructor
	public ListeProduit() {

	}
	
	public ListeProduit(int id_liste, int id_produit) {
		super();
		this.id_liste = id_liste;
		this.id_produit = id_produit;
		this.qte_produit = "";
	}

	//getters and setters
	public int getId_liste() {
		return id_liste;
	}


	public void setId_liste(int id_liste) {
		this.id_liste = id_liste;
	}


	public int getId_produit() {
		return id_produit;
	}


	public void setId_produit(int id_produit) {
		this.id_produit = id_produit;
	}


	public String getQte_produit() {
		return qte_produit;
	}


	public void setQte_produit(String qte_produit) {
		this.qte_produit = qte_produit;
	}
	
	
}
