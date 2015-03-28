package dataFactory;

public class Promotion {
	
	int id_promo;
	String nom_mag; 	
	String desc_promo; 	
	String date_debut; 	
	String date_fin;
	
	public Promotion(int id_promo, String nom_mag, String desc_promo,
			String date_debut, String date_fin) {
		super();
		this.id_promo = id_promo;
		this.nom_mag = nom_mag;
		this.desc_promo = desc_promo;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
	}
	
	public Promotion() {

	}

	public int getId_promo() {
		return id_promo;
	}

	public void setId_promo(int id_promo) {
		this.id_promo = id_promo;
	}

	public String getNom_mag() {
		return nom_mag;
	}

	public void setNom_mag(String nom_mag) {
		this.nom_mag = nom_mag;
	}

	public String getDesc_promo() {
		return desc_promo;
	}

	public void setDesc_promo(String desc_promo) {
		this.desc_promo = desc_promo;
	}

	public String getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(String date_debut) {
		this.date_debut = date_debut;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}

	@Override
	public String toString() {
		return "Promotion [id_promo=" + id_promo + ", nom_mag=" + nom_mag
				+ ", desc_promo=" + desc_promo + ", date_debut=" + date_debut
				+ ", date_fin=" + date_fin + "]";
	}

}
