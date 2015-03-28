package com.esgi.iam.mpc.basesqlite;

public class Utilisateur {

	int id_utilisateur;
	String name;
	String email;


	//constructeur
	public Utilisateur(int id_utilisateur, String name, String email) {
		this.id_utilisateur = id_utilisateur;
		this.name = name;
		this.email = email;

	}
	
	public Utilisateur() {
		
	}

	//getters and setters
	public int getId_utilisateur() {
		return id_utilisateur;
	}

	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Utilisateur [id_utilisateur=" + id_utilisateur + ", name="
				+ name + ", email=" + email + "]";
	}	

}
