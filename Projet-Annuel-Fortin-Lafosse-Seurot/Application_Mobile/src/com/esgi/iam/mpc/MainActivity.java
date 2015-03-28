package com.esgi.iam.mpc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.iam.mpc.basesqlite.DatabaseHelper;

public class MainActivity extends ActionBarActivity {
	DatabaseHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		db = new DatabaseHelper(getApplicationContext());
		//suppression database
		/*this.deleteDatabase("basempc");
		Toast.makeText(this, "Base supprimé", Toast.LENGTH_SHORT).show();*/
		
		//chargement jeu de test
		/*db.addListesTest();
		db.addProduitsTest();
		db.addListeProduitTest();
		Toast.makeText(this, "Données ajoutées", Toast.LENGTH_SHORT).show();*/
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);	 
		return super.onCreateOptionsMenu(menu);
	}

	
	public void onClickBtnMesListes(View view){
	
	    Intent i = new Intent (view.getContext(), MesListeActivity.class);
	        startActivity(i);
	}
	
	public void onClickBtnNewListe(View view){
		
	    Intent i = new Intent (view.getContext(), NewList.class);
	        startActivity(i);
	}
	
	
	public void onClickBtnGeoloc(View view){
		
		Intent i = new Intent (view.getContext(), GeolocActivity.class);
        startActivity(i);

	}
	
	public void onClickBtnMyCards(View view){
		
		Intent i = new Intent (view.getContext(), CardActivity.class);
        startActivity(i);
	}
	
	public void onClickBtnPromo(View view){
		
	    Intent i = new Intent (view.getContext(), PromoActivity.class);
        startActivity(i);
	}
	
	public void onClickPref(View view){
		
	    Intent i = new Intent (view.getContext(), Settings.class);
	        startActivity(i);
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,false);
			
			return rootView;
		}
	}
}
