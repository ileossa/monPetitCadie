package com.esgi.iam.mpc.basesqlite;

public class Carte {
	
	int id_carte;
	String nom_carte;
	String photo;
	
	
	public Carte(int id_carte, String nom_carte, String photo) {
		super();
		this.id_carte = id_carte;
		this.nom_carte = nom_carte;
		this.photo = photo;
	}
	
	public Carte() {

	}

	public int getId_carte() {
		return id_carte;
	}

	public void setId_carte(int id_carte) {
		this.id_carte = id_carte;
	}

	public String getNom_carte() {
		return nom_carte;
	}

	public void setNom_carte(String nom_carte) {
		this.nom_carte = nom_carte;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "Carte [id_carte=" + id_carte + ", nom_carte=" + nom_carte
				+ ", photo=" + photo + "]";
	}
	
	

}
