package com.esgi.iam.mpc;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.basesqlite.Produit;
import com.esgi.iam.mpc.webservice.WebService;


public class LoadingScreen extends Activity {

	ProgressDialog mProgressDialog;
	private static int SPLASH_TIME_OUT = 3000;
	DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        
	    mProgressDialog = new ProgressDialog(this);
	    mProgressDialog.setIndeterminate(false);
	    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    mProgressDialog.setMessage("Mise à jour des produits, veuillez patientez");
        
        db = new DatabaseHelper(getApplicationContext());
        
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        int a = 2;
        if (db.getUtilisateurRowCount()==0) {
        	if (wifiNetwork != null && wifiNetwork.isConnected())
	        {
        		Intent z = new Intent(LoadingScreen.this, LoginActivity.class);
        		startActivity(z);
        		finish();
	        }
        	else if (mobileNetwork != null && mobileNetwork.isConnected())
	        {
        		Intent z = new Intent(LoadingScreen.this, LoginActivity.class);
        		startActivity(z);
        		finish();
	        }else{
	        	if (db.getNumberRowProduit()==0){
	        		Toast.makeText(getApplicationContext(), "Product Added", Toast.LENGTH_SHORT).show();
	        		db.addProduitsTest();
	        	}
				Intent i = new Intent(LoadingScreen.this, MainActivity.class);
				startActivity(i);		 
				finish();
	        }

		}else {
	        if (wifiNetwork != null && wifiNetwork.isConnected())
	        {
	        	getData();
	        }else if (mobileNetwork != null && mobileNetwork.isConnected()) {
	        	getData();
	        }else {
	        	if (db.getNumberRowProduit()==0){
	        		Toast.makeText(getApplicationContext(), "Product Added", Toast.LENGTH_SHORT).show();
	        		db.addProduitsTest();
	    			Intent i = new Intent(LoadingScreen.this, MainActivity.class);
	    			startActivity(i);
	    	 
	    			finish();
	        	}else {
	      			Intent i = new Intent(LoadingScreen.this, MainActivity.class);
	    			startActivity(i);
	    	 
	    			finish();
	        	}
	        }
		}      

    }
    
	private void getData() {
		new AsyncTask<Void, Void, List<Produit>>() {
			
		protected void onPreExecute() {
			mProgressDialog.show();
		};
		
		@Override
		protected List<Produit> doInBackground(Void... params) {
			WebService webService = new WebService();
			
			List<Produit> liste = webService.getProduit();
			
			if (liste != null) {
				return liste;
			}
			
			return new ArrayList<Produit>();
		};
		
		protected void onPostExecute(java.util.List<Produit> result) {
	        db = new DatabaseHelper(getApplicationContext());
	        
	        if (result.size()==db.getNumberRowProduit()) {
	        	Toast.makeText(getApplicationContext(), "La base est deja à jour", Toast.LENGTH_SHORT).show();
			}else if(result.size()>db.getNumberRowProduit()) {
				
				if (db.getNumberRowProduit()>0) 
				{
					db.deleteAllProduit();
				}				
				for (Produit prod: result) 
				{
					db.createProduit(prod);
				}
				Toast.makeText(getApplicationContext(), "Base mise à jour", Toast.LENGTH_SHORT).show();
			}
	        mProgressDialog.dismiss();

			Intent i = new Intent(LoadingScreen.this, MainActivity.class);
			startActivity(i);
	 
			finish();
		};
		
		}.execute();
	}
    
}
