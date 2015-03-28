package dataFactory;

public class Product {
	
	int id_produit;
	String nom_produit;
	int parent;
	
	//constructor
	public Product(int id_produit, String nom_produit, int parent) {
		this.id_produit = id_produit;
		this.nom_produit = nom_produit;
		this.parent = parent;
	}
	
	public Product() {

	}

	//getters and setters
	public int getId_produit() {
		return id_produit;
	}

	public void setId_produit(int id_produit) {
		this.id_produit = id_produit;
	}

	public String getNom_produit() {
		return nom_produit;
	}

	public void setNom_produit(String nom_produit) {
		this.nom_produit = nom_produit;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}


	@Override
	public String toString() {
		return "Produit [id_produit=" + id_produit + ", nom_produit="
				+ nom_produit + ", parent=" + parent + "]";
	}	
	
}
