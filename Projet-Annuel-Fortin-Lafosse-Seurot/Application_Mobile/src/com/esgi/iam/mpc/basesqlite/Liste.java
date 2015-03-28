package com.esgi.iam.mpc.basesqlite;

public class Liste {
	int id_liste;
	String nom_liste;
	boolean modele;
	String nom_utilisateur;
	int uid;
	

	// constructeur
	public Liste(int id_liste, String nom_liste, String nom_utilisateur) { 
		this.id_liste = id_liste;
		this.nom_liste = nom_liste;
		this.modele = false;
		this.nom_utilisateur = nom_utilisateur;
		this.uid = 0;
	}
		
	public Liste() {

	}

	//getters and setters
	public int getId_liste() {
		return id_liste;
	}

	public void setId_liste(int id_liste) {
		this.id_liste = id_liste;
	}

	public String getNom_liste() {
		return nom_liste;
	}

	public void setNom_liste(String nom_liste) {
		this.nom_liste = nom_liste;
	}

	public boolean isModele() {
		return modele;
	}

	public void setModele(boolean modele) {
		this.modele = modele;
	}
	
	public String getNom_utilisateur() {
		return nom_utilisateur;
	}

	public void setNom_utilisateur(String nom_utilisateur) {
		this.nom_utilisateur = nom_utilisateur;
	}
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "Liste [id_liste=" + id_liste + ", nom_liste=" + nom_liste
				+ ", modele=" + modele + ", nom_utilisateur=" + nom_utilisateur
				+ "]";
	}


		
}