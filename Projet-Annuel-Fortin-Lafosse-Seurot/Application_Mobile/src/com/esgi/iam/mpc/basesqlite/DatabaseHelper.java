package com.esgi.iam.mpc.basesqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	 
    // Logcat tag
    private static final String LOG = DatabaseHelper.class.getName();
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "basempc";
 
    // Table Names
    private static final String TABLE_LISTE = "liste";
    private static final String TABLE_PRODUIT = "produit";
    private static final String TABLE_LISTE_PRODUIT = "liste_produit";
    private static final String TABLE_UTILISATEUR = "utilisateur";
    private static final String TABLE_CARTE = "carte";
 
    // LISTE Table - column names
    private static final String KEY_ID_LISTE = "id_liste";
    private static final String KEY_NOM_LISTE = "nom_liste";
    private static final String KEY_MODELE = "modele";
    private static final String KEY_NOM_UTILISATEUR = "nom_utilisateur";
    private static final String KEY_UID = "uid";
    
    // PRODUIT Table - column names
    private static final String KEY_ID_PRODUIT = "id_produit";
    private static final String KEY_NOM_PRODUIT = "nom_produit";
    private static final String KEY_PARENT = "parent";
    private static final String KEY_FAVORI = "favori";

 
    // LISTE_PRODUIT Table - column names
    private static final String KEY_ID_LISTE_PRODUIT = "id_liste_produit";
    private static final String KEY_QTE_PRODUIT = "quantite";
    // KEY_ID_LISTE
    // KEY_ID_PRODUIT
    
    // CARTE Table - column names
    private static final String KEY_ID_CARTE = "id_carte";
    private static final String KEY_NOM_CARTE = "nom";
    private static final String KEY_PHOTO = "photo";
    
    // UTILISATEUR Table - column names
    private static final String KEY_ID_UTILISATEUR = "id_utilisateur";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    
 
    // LISTE Table CREATE
    private static final String CREATE_TABLE_LISTE = "CREATE TABLE "
            + TABLE_LISTE + "(" + KEY_ID_LISTE + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NOM_LISTE 
            + " TEXT," + KEY_NOM_UTILISATEUR + " TEXT," + KEY_MODELE + " BOOLEAN," + KEY_UID + " INTEGER" + ")";
 
    // PRODUIT Table CREATE
    private static final String CREATE_TABLE_PRODUIT = "CREATE TABLE "
            + TABLE_PRODUIT + "(" + KEY_ID_PRODUIT + " INTEGER PRIMARY KEY," + KEY_NOM_PRODUIT
            + " TEXT," + KEY_PARENT + " INTEGER," + KEY_FAVORI + " BOOLEAN" + ")";
 
    // LISTE_PRODUIT table create statement
    private static final String CREATE_TABLE_LISTE_PRODUIT = "CREATE TABLE "
            + TABLE_LISTE_PRODUIT + "(" + KEY_ID_LISTE + " INTEGER," + KEY_ID_PRODUIT + " INTEGER," + KEY_QTE_PRODUIT + " TEXT" + ")";
    
    // UTILISATEUR Table CREATE
    private static final String CREATE_TABLE_UTILISATEUR = "CREATE TABLE "
            + TABLE_UTILISATEUR + "(" + KEY_ID_UTILISATEUR + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_EMAIL
            + " TEXT" + ")";
    
    // CARTE Table CREATE
    private static final String CREATE_TABLE_CARTE = "CREATE TABLE "
            + TABLE_CARTE + "(" + KEY_ID_CARTE + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NOM_CARTE
            + " TEXT," + KEY_PHOTO + " TEXT" + ")";
 
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // drop all tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTE_PRODUIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARTE);
        // creation des tables
        db.execSQL(CREATE_TABLE_LISTE);
        db.execSQL(CREATE_TABLE_PRODUIT);
        db.execSQL(CREATE_TABLE_LISTE_PRODUIT);
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_CARTE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTE_PRODUIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
 
        // create new tables
        onCreate(db);
    }
       
    
    // ------------------------ "liste" table methods ----------------//
       
    /**
     * Creer une liste
     */
    public long createListe(Liste liste) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NOM_LISTE, liste.getNom_liste());
        values.put(KEY_NOM_UTILISATEUR, liste.getNom_utilisateur());
        values.put(KEY_MODELE, liste.isModele());
        values.put(KEY_UID, liste.getUid());
 
        long liste_id = db.insert(TABLE_LISTE, null, values);
  
        return liste_id;
    }
    
    /**
     * Retourne une liste grâce a son id
     */
    public Liste getListe(long id_liste) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        String selectQuery = "SELECT  * FROM " + TABLE_LISTE + " WHERE "
                + KEY_ID_LISTE + " = " + id_liste;
 
        Log.e(LOG, selectQuery);
 
        Cursor c = db.rawQuery(selectQuery, null);
 
        if (c != null)
            c.moveToFirst();
 
        Liste lst = new Liste();
        lst.setId_liste((c.getInt((c.getColumnIndex(KEY_ID_LISTE)))));
        lst.setNom_liste((c.getString((c.getColumnIndex(KEY_NOM_LISTE)))));
        lst.setNom_utilisateur((c.getString((c.getColumnIndex(KEY_NOM_UTILISATEUR)))));
        lst.setModele((c.getInt(c.getColumnIndex(KEY_MODELE))>0));
        lst.setUid((c.getInt(c.getColumnIndex(KEY_UID))));
 
        return lst;
    }
    
    public Liste getListeByName (String nom) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        String selectQuery = "SELECT  * FROM " + TABLE_LISTE + " WHERE "
                + KEY_NOM_LISTE + " = '" + nom + "'";
 
        Log.e(LOG, selectQuery);
 
        Cursor c = db.rawQuery(selectQuery, null);
 
        if (c != null)
            c.moveToFirst();
 
        Liste lst = new Liste();
        lst.setId_liste((c.getInt((c.getColumnIndex(KEY_ID_LISTE)))));
        lst.setNom_liste((c.getString((c.getColumnIndex(KEY_NOM_LISTE)))));
        lst.setNom_utilisateur((c.getString((c.getColumnIndex(KEY_NOM_UTILISATEUR)))));
        lst.setModele((c.getInt(c.getColumnIndex(KEY_MODELE))>0));
        lst.setUid((c.getInt(c.getColumnIndex(KEY_UID))));
 
        return lst;
    }
    
    /**
     *Retourne toutes les listes
     */
    public List<Liste> getAllListe() {
    	List<Liste> listes = new ArrayList<Liste>();
        String selectQuery = "SELECT  * FROM " + TABLE_LISTE;
 
        Log.e(LOG, selectQuery);
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Liste lst = new Liste();
                lst.setId_liste((c.getInt((c.getColumnIndex(KEY_ID_LISTE)))));
                lst.setNom_liste((c.getString((c.getColumnIndex(KEY_NOM_LISTE)))));
                lst.setNom_utilisateur((c.getString((c.getColumnIndex(KEY_NOM_UTILISATEUR)))));
                lst.setModele((c.getInt(c.getColumnIndex(KEY_MODELE))>0));
                lst.setUid((c.getInt(c.getColumnIndex(KEY_UID))));
 
                // adding to liste list
                listes.add(lst);
            } while (c.moveToNext());
        }
 
        return listes;
    }
    
    /**
     *Retourne toutes les listes
     */
    public List<Liste> getListeByNameUser(String nom_utilisateur) {
    	List<Liste> listes = new ArrayList<Liste>();
        String selectQuery = "SELECT  * FROM " + TABLE_LISTE + " WHERE " + KEY_NOM_UTILISATEUR + " = '" + nom_utilisateur + "'";
 
        Log.e(LOG, selectQuery);
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Liste lst = new Liste();
                lst.setId_liste((c.getInt((c.getColumnIndex(KEY_ID_LISTE)))));
                lst.setNom_liste((c.getString((c.getColumnIndex(KEY_NOM_LISTE)))));
                lst.setNom_utilisateur((c.getString((c.getColumnIndex(KEY_NOM_UTILISATEUR)))));
                lst.setModele((c.getInt(c.getColumnIndex(KEY_MODELE))>0));
                lst.setUid((c.getInt(c.getColumnIndex(KEY_UID))));
 
                // adding to liste list
                listes.add(lst);
            } while (c.moveToNext());
        }
 
        return listes;
    }
    
    /**
     *Retourne le nombre de liste
     */
    public int getCountListe() {
    	int cpt=0;
        String selectQuery = "SELECT  * FROM " + TABLE_LISTE;
       
        Log.e(LOG, selectQuery);
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                cpt++;                
            } while (c.moveToNext());
        }
 
        return cpt;
    }
    
    public String[] getAllListeName() {
    	String[] names = new String[getCountListe()];
    	int cpt=0;
        String selectQuery = "SELECT  * FROM " + TABLE_LISTE;
       
        Log.e(LOG, selectQuery);
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                names[cpt] = ((c.getString((c.getColumnIndex(KEY_NOM_LISTE)))));
                cpt++;
                
            } while (c.moveToNext());
        }
 
        return names;
    }
    
    public Cursor getAllListeCursor(String nom_utilisateur) {
        String selectQuery = "SELECT  id_liste as _id, nom_liste, nom_utilisateur FROM " + TABLE_LISTE + " WHERE " + KEY_NOM_UTILISATEUR + " = '" + nom_utilisateur + "'";
       
        Log.e(LOG, selectQuery);
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
  
        return c;
    }
    
    /**
     * Modifier une liste
     */
    public int updateListe(Liste liste) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NOM_LISTE, liste.getNom_liste());
        values.put(KEY_NOM_UTILISATEUR, liste.getNom_utilisateur());
        values.put(KEY_MODELE, liste.isModele());
        values.put(KEY_UID, liste.getUid());
 
        // updating row
        return db.update(TABLE_LISTE, values, KEY_ID_LISTE + " = ?",
                new String[] { String.valueOf(liste.getId_liste()) });
    }
    
    /**
     * Mettre une liste en modele
     */
    public int listeIsModele(int id, boolean is) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_MODELE, is);
 
        // updating row
        return db.update(TABLE_LISTE, values, KEY_ID_LISTE + " = ?",
                new String[] { String.valueOf(id) });
    }
    
    public List<Liste> getListeModel() {
    	List<Liste> listes = new ArrayList<Liste>();
        SQLiteDatabase db = this.getReadableDatabase();
 
        String selectQuery = "SELECT  * FROM " + TABLE_LISTE + " WHERE "
                + KEY_MODELE + " = 1";
 
        Log.e(LOG, selectQuery);
 
        Cursor c = db.rawQuery(selectQuery, null);
 
        if (c.moveToFirst()) {
            do {
                Liste lst = new Liste();
                lst.setId_liste((c.getInt((c.getColumnIndex(KEY_ID_LISTE)))));
                lst.setNom_liste((c.getString((c.getColumnIndex(KEY_NOM_LISTE)))));
                lst.setNom_utilisateur((c.getString((c.getColumnIndex(KEY_NOM_UTILISATEUR)))));
                lst.setModele((c.getInt(c.getColumnIndex(KEY_MODELE))>0));
                lst.setUid((c.getInt(c.getColumnIndex(KEY_UID))));
 
                // adding to liste list
                listes.add(lst);
            } while (c.moveToNext());
        }
 
        return listes;
    }
    
    /**
     * Supprimer tous les liens pour un id_liste donné
     */
    public void deleteAllListeProduit(int id_liste) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LISTE_PRODUIT, KEY_ID_LISTE + " = " + id_liste, null);
    }
    
    /**
     * Supprimer une liste
     */
    public void deleteListe(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteAllListeProduit(id);        
        db.delete(TABLE_LISTE, KEY_ID_LISTE + " = ?",
                new String[] { String.valueOf(id) });
    }
    // ------------------------ "produit" table methods ----------------//
    
    public void addProduitsTest () {
 	   Produit produit1 = new Produit(1, "Boissons", 0);
 	   createProduit(produit1);
 	   Produit produit2 = new Produit(2, "Fruits", 0);
 	   createProduit(produit2);
 	   Produit produit3 = new Produit(3, "Legumes", 0);
 	   createProduit(produit3);
 	   
 	   Produit boisson1 = new Produit(110, "Eau", 1);
 	   createProduit(boisson1);
 	   Produit boisson2 = new Produit(111, "Soda", 1);
 	   createProduit(boisson2);
 	   Produit boisson3 = new Produit(112, "Jus de Fruits", 1);
 	   createProduit(boisson3);
 	   Produit boisson4 = new Produit(113, "Alcool", 1);
 	   createProduit(boisson4);
 	    	   
 	   Produit fruit1 = new Produit(120, "Pomme", 2);
 	   createProduit(fruit1);
 	   Produit fruit2 = new Produit(121, "Poires", 2);
 	   createProduit(fruit2);
 	   Produit fruit3 = new Produit(122, "Fraises", 2);
 	   createProduit(fruit3);
 	   Produit fruit4 = new Produit(123, "Annanas", 2);
 	   createProduit(fruit4);
 	   
 	   Produit legume1 = new Produit(130, "Carrottes", 3);
 	   createProduit(legume1);
 	   Produit legume2 = new Produit(131, "Tomates", 3);
 	   createProduit(legume2);
 	   Produit legume3 = new Produit(132, "Haricots verts", 3);
 	   createProduit(legume3);
 	   Produit legume4 = new Produit(133, "Salade", 3);
 	   createProduit(legume4);

    }
    
    /**
    * Creer un produit
    */
   public long createProduit(Produit produit) {
       SQLiteDatabase db = this.getWritableDatabase();

       ContentValues values = new ContentValues();
       values.put(KEY_ID_PRODUIT, produit.getId_produit());
       values.put(KEY_NOM_PRODUIT, produit.getNom_produit());
       values.put(KEY_PARENT, produit.getParent());
       values.put(KEY_FAVORI, false);
      

       long produit_id = db.insert(TABLE_PRODUIT, null, values);
 
       return produit_id;
   }

   /**
    * Recuperer un produit
    */
   public Produit getProduit(int id_produit) {
       SQLiteDatabase db = this.getReadableDatabase();

       String selectQuery = "SELECT  * FROM " + TABLE_PRODUIT + " WHERE "
               + KEY_ID_PRODUIT + " = " + id_produit;

       Log.e(LOG, selectQuery);

       Cursor c = db.rawQuery(selectQuery, null);

       if (c != null)
           c.moveToFirst();

       Produit prod = new Produit();
       prod.setId_produit((c.getInt((c.getColumnIndex(KEY_ID_PRODUIT)))));
       prod.setNom_produit((c.getString((c.getColumnIndex(KEY_NOM_PRODUIT)))));
       prod.setParent(c.getInt(c.getColumnIndex(KEY_PARENT)));
       prod.setFavori((c.getInt(c.getColumnIndex(KEY_FAVORI))>0));


       return prod;
   }
   
   public String getProduitName(long id_produit) {
       SQLiteDatabase db = this.getReadableDatabase();
       String name = new String();

       String selectQuery = "SELECT "+KEY_NOM_PRODUIT+" FROM " + TABLE_PRODUIT + " WHERE "
               + KEY_ID_PRODUIT + " = " + id_produit;

       Log.e(LOG, selectQuery);

       Cursor c = db.rawQuery(selectQuery, null);

       if (c != null){
    	   	c.moveToFirst();
   	   		name = (String)c.getString(c.getColumnIndex(KEY_NOM_PRODUIT));
       }
    	        
       return name;
   }

   /**
    *Retourne tous les produits
    * */
   public List<Produit> getAllProduit() {
   	List<Produit> produits = new ArrayList<Produit>();
       String selectQuery = "SELECT  * FROM " + TABLE_PRODUIT;

       Log.e(LOG, selectQuery);

       SQLiteDatabase db = this.getReadableDatabase();
       Cursor c = db.rawQuery(selectQuery, null);

       // looping through all rows and adding to list
       if (c.moveToFirst()) {
           do {
               Produit prod = new Produit();
               prod.setId_produit((c.getInt((c.getColumnIndex(KEY_ID_PRODUIT)))));
               prod.setNom_produit((c.getString((c.getColumnIndex(KEY_NOM_PRODUIT)))));
               prod.setParent(c.getInt(c.getColumnIndex(KEY_PARENT)));
               prod.setFavori((c.getInt(c.getColumnIndex(KEY_FAVORI))>0));

               // adding to liste list
               produits.add(prod);
           } while (c.moveToNext());
       }

       return produits;
   }
   
   public Cursor getAllProduitByParent(int id_parent) {
	       String selectQuery = "SELECT  id_produit AS _id, nom_produit FROM " + TABLE_PRODUIT + " WHERE " + KEY_PARENT + " = " + id_parent;

	       Log.e(LOG, selectQuery);

	       SQLiteDatabase db = this.getReadableDatabase();
	       Cursor c = db.rawQuery(selectQuery, null);

	       return c;
	   }
   public int getProduitParent(int id_produit){
	   	String selectQuery = "SELECT  * FROM " + TABLE_PRODUIT + " WHERE " + KEY_ID_PRODUIT + " = " + id_produit;
	   	
	   	Log.e(LOG, selectQuery);
	   	
	   	SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	    int id_parent = 0;
	    
	       if (c != null){
	    	   	c.moveToFirst();
	    	   id_parent = (int) c.getInt(c.getColumnIndex(KEY_PARENT));
	       }  
	    
	    return id_parent;
   }
   
   public void displayProduit(){
	   String selectQuery = "SELECT  * FROM " + TABLE_PRODUIT;

       Log.e(LOG, selectQuery);

       SQLiteDatabase db = this.getReadableDatabase();
       Cursor c = db.rawQuery(selectQuery, null);
       
       if (c.moveToFirst()) {
           do {
        	   Log.e(LOG, (c.getString((c.getColumnIndex(KEY_ID_PRODUIT)))));
        	   Log.e(LOG, (c.getString((c.getColumnIndex(KEY_NOM_PRODUIT)))));
           } while (c.moveToNext());
       }
	   
   }
   
   public int getNumberRowProduit(){
	   
	   int cpt=0;
	   String selectQuery = "SELECT  * FROM " + TABLE_PRODUIT;

       Log.e(LOG, selectQuery);

       SQLiteDatabase db = this.getReadableDatabase();
       Cursor c = db.rawQuery(selectQuery, null);
       
       if (c.moveToFirst()) {
           do {
        	  cpt++;
           } while (c.moveToNext());
       }
       
       return cpt;
   }
   
   /**
    * Modifier un produit
    */
   public int updateProduit(Produit produit) {
       SQLiteDatabase db = this.getWritableDatabase();
       
       ContentValues values = new ContentValues();
       values.put(KEY_NOM_PRODUIT, produit.getNom_produit());
       values.put(KEY_PARENT, produit.getParent());

       // updating row
       return db.update(TABLE_PRODUIT, values, KEY_ID_PRODUIT + " = ?",
               new String[] { String.valueOf(produit.getId_produit()) });
   }
   
   /**
    * Mettre un produit en favori
    */
   public int produitIsFavori(int id, boolean is) {
       SQLiteDatabase db = this.getWritableDatabase();

       ContentValues values = new ContentValues();
       values.put(KEY_FAVORI, is);

       // updating row
       return db.update(TABLE_PRODUIT, values, KEY_ID_PRODUIT + " = ?",
               new String[] { String.valueOf(id) });
   }
   
   public Cursor getAllProduitFavori() {
       String selectQuery = "SELECT id_produit AS _id, nom_produit FROM " + TABLE_PRODUIT + " WHERE " + KEY_FAVORI + " = 1";

       Log.e(LOG, selectQuery);

       SQLiteDatabase db = this.getReadableDatabase();
       Cursor c = db.rawQuery(selectQuery, null);

       return c;
   }
   

   /**
    * Supprimer un produit
    */
   public void deleteProduit(int id) {
       SQLiteDatabase db = this.getWritableDatabase();
       
       db.delete(TABLE_PRODUIT, KEY_ID_PRODUIT + " = ?",
               new String[] { String.valueOf(id) });
   }
   
   public void deleteAllProduit() {
       SQLiteDatabase db = this.getWritableDatabase();
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUIT);
       db.execSQL(CREATE_TABLE_PRODUIT);
       db.close();
   }
    
    // ------------------------ "liste_produit" table methods ----------------//
   public void addListeProduitTest() {
	   ListeProduit listeproduit = new ListeProduit(1, 4);
	   createListeProduit(listeproduit);
	   ListeProduit listeproduit2 = new ListeProduit(1, 8);
	   createListeProduit(listeproduit2);
	   ListeProduit listeproduit3 = new ListeProduit(1, 11);
	   createListeProduit(listeproduit3);
   }
    /**
     * Lie un produit a une liste
     */
    public long createListeProduit(ListeProduit liste_produit) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID_LISTE, liste_produit.getId_liste());
        values.put(KEY_ID_PRODUIT, liste_produit.getId_produit());
        values.put(KEY_QTE_PRODUIT, liste_produit.getQte_produit());
 
        long id = db.insert(TABLE_LISTE_PRODUIT, null, values);
 
        return id;
    } 
    
    public List<ListeProduit> getAllListeProduit(int id_liste) {
 	   
 	   List<ListeProduit> listeproduits = new ArrayList<ListeProduit>();
 	   String selectQuery = "SELECT  * FROM " + TABLE_LISTE_PRODUIT+" WHERE " + KEY_ID_LISTE + " = " + id_liste;
 	
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        
 	   if (c.moveToFirst()) {
 	       do {
 	    	  ListeProduit listeprod = new ListeProduit();
 	    	  listeprod.setId_liste((c.getInt(c.getColumnIndex(KEY_ID_LISTE))));
 	    	  listeprod.setId_produit((c.getInt(c.getColumnIndex(KEY_ID_PRODUIT))));
 	    	  listeprod.setQte_produit(c.getString((c.getColumnIndex(KEY_QTE_PRODUIT))));
 	
 	          listeproduits.add(listeprod);
 	       } while (c.moveToNext());
 	   }

 	   return listeproduits;
    }
    
    public List<Integer> getAllListeProduitId(int id_liste) {
  	   
  	   List<Integer> listeIdProduits = new ArrayList<Integer>();
  	   String selectQuery = "SELECT  " + KEY_ID_PRODUIT + " FROM " + TABLE_LISTE_PRODUIT+" WHERE " + KEY_ID_LISTE + " = " + id_liste;
  	
         Log.e(LOG, selectQuery);

         SQLiteDatabase db = this.getReadableDatabase();
         Cursor c = db.rawQuery(selectQuery, null);
         
  	   if (c.moveToFirst()) {
  	       do {
  	
  	          listeIdProduits.add(c.getInt(c.getColumnIndex(KEY_ID_PRODUIT)));
  	       } while (c.moveToNext());
  	   }

  	   return listeIdProduits;
     }
    
    public ListeProduit getListeProduit(int id_liste, int id_produit) {
    	ListeProduit listeprod = new ListeProduit();
    	String selectQuery = "SELECT  * FROM " + TABLE_LISTE_PRODUIT + " WHERE " + KEY_ID_LISTE + " = " + id_liste + " AND " + KEY_ID_PRODUIT + " = " + id_produit;
     	
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        
 	   if (c.moveToFirst()) {
 		   
    	  listeprod.setId_liste((c.getInt(c.getColumnIndex(KEY_ID_LISTE))));
    	  listeprod.setId_produit((c.getInt(c.getColumnIndex(KEY_ID_PRODUIT))));
    	  listeprod.setQte_produit(c.getString((c.getColumnIndex(KEY_QTE_PRODUIT))));
    	  
 	   }

 	   return listeprod;
    }
    
    public void updateListeProduit(ListeProduit listeproduit) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        String updateQuery = "UPDATE " +TABLE_LISTE_PRODUIT + " SET " + KEY_QTE_PRODUIT + " ='" + listeproduit.getQte_produit() +
        		"' WHERE " + KEY_ID_LISTE + " = " + listeproduit.getId_liste() + " AND " + KEY_ID_PRODUIT + " = " + listeproduit.getId_produit();
        Log.e(LOG, updateQuery);

        db.execSQL(updateQuery);
    }
    
    public boolean isInListe(int id_liste, int id_produit){
    	boolean find = false;
 	    String selectQuery = "SELECT  * FROM " + TABLE_LISTE_PRODUIT + " WHERE " + KEY_ID_LISTE +" = " + id_liste +" AND " + KEY_ID_PRODUIT + " = " + id_produit;
 	   
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        
        if (c.moveToFirst()) {
			find = true;
		}
       
       return find;
 	   
    }
    
 
    /**
     * Supprimer un lien
     */
    public void deleteListeProduit(long id_liste, long id_produit) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LISTE_PRODUIT, KEY_ID_LISTE + " = " + id_liste + " and " + KEY_ID_PRODUIT + " = " + id_produit, null);
    }
    
    
    // ------------------------ "carte" table methods ----------------//
    /**
    * Creer une carte
    */
   public long createCarte(Carte carte) {
       SQLiteDatabase db = this.getWritableDatabase();

       ContentValues values = new ContentValues();
       values.put(KEY_NOM_CARTE, carte.getNom_carte());
       values.put(KEY_PHOTO, carte.getPhoto());
      

       long carte_id = db.insert(TABLE_CARTE, null, values);
 
       return carte_id;
   }
    
   /**
    *Retourne toutes les cartes
    * */
   public List<Carte> getAllCarte() {
   	List<Carte> cartes = new ArrayList<Carte>();
       String selectQuery = "SELECT  * FROM " + TABLE_CARTE;

       Log.e(LOG, selectQuery);

       SQLiteDatabase db = this.getReadableDatabase();
       Cursor c = db.rawQuery(selectQuery, null);

       // looping through all rows and adding to list
       if (c.moveToFirst()) {
           do {
               Carte carte = new Carte();
               carte.setId_carte((c.getInt((c.getColumnIndex(KEY_ID_CARTE)))));
               carte.setNom_carte((c.getString((c.getColumnIndex(KEY_NOM_CARTE)))));
               carte.setPhoto((c.getString((c.getColumnIndex(KEY_PHOTO)))));

               cartes.add(carte);
           } while (c.moveToNext());
       }

       return cartes;
   }
   
   /**
    * Recuperer une carte
    */
   public Carte getCarte(int id_carte) {
       SQLiteDatabase db = this.getReadableDatabase();

       String selectQuery = "SELECT  * FROM " + TABLE_CARTE + " WHERE "
               + KEY_ID_CARTE + " = " + id_carte;

       Log.e(LOG, selectQuery);

       Cursor c = db.rawQuery(selectQuery, null);

       if (c != null)
           c.moveToFirst();

       Carte carte = new Carte();
       carte.setId_carte((c.getInt((c.getColumnIndex(KEY_ID_CARTE)))));
       carte.setNom_carte((c.getString((c.getColumnIndex(KEY_NOM_CARTE)))));
       carte.setPhoto((c.getString((c.getColumnIndex(KEY_PHOTO)))));


       return carte;
   }
   
   /**
    * Supprimer une carte
    */
   public void deleteCarte(int id) {
       SQLiteDatabase db = this.getWritableDatabase();
       
       db.delete(TABLE_CARTE, KEY_ID_CARTE + " = ?",
               new String[] { String.valueOf(id) });
   }
    
    // ------------------------ "utilisateur" table methods ----------------//
   /**
   * Creer un utilisateur
   */
   public void createUser(String name, String email) {
       SQLiteDatabase db = this.getWritableDatabase();

       ContentValues values = new ContentValues();
       values.put(KEY_NAME, name); // Name
       values.put(KEY_EMAIL, email); // Email

       // Inserting Row
       db.insert(TABLE_UTILISATEUR, null, values);
       db.close(); // Closing database connection
   }
    
   /**
    * Recuperer un utilisateur
    */
   public Utilisateur getUtilisateur() {
       SQLiteDatabase db = this.getReadableDatabase();

       String selectQuery = "SELECT  * FROM " + TABLE_UTILISATEUR;

       Log.e(LOG, selectQuery);

       Cursor c = db.rawQuery(selectQuery, null);

       if (c != null)
           c.moveToFirst();

       Utilisateur u = new Utilisateur();
       u.setId_utilisateur((c.getInt((c.getColumnIndex(KEY_ID_UTILISATEUR)))));
       u.setName((c.getString((c.getColumnIndex(KEY_NAME)))));
       u.setEmail((c.getString((c.getColumnIndex(KEY_EMAIL)))));


       return u;
   }
   
   /**
    * Recuperer le nom de l'utilisateur
    */
   public String getUtilisateurName() {
       SQLiteDatabase db = this.getReadableDatabase();

       String selectQuery = "SELECT  * FROM " + TABLE_UTILISATEUR;

       Cursor c = db.rawQuery(selectQuery, null);

       if (c != null)
           c.moveToFirst();

       String nom = c.getString((c.getColumnIndex(KEY_NAME)));

       return nom;
   }

   /**
    * statut utilisateur
    * retourne vraie si il y des lignes dans la table
    * */
   public int getUtilisateurRowCount() {
       String countQuery = "SELECT  * FROM " + TABLE_UTILISATEUR;
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(countQuery, null);
       int rowCount = cursor.getCount();
       db.close();
       cursor.close();
        
       // return row count
       return rowCount;
   }
    
   /**
    * Recree la table utilisateur
    * */
   public void resetTableUtilisateur(){
       SQLiteDatabase db = this.getWritableDatabase();
	   db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
       db.execSQL(CREATE_TABLE_UTILISATEUR);
       db.close();
   }

    
    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
 
}