package com.esgi.iam.mpc;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.basesqlite.Liste;
import com.esgi.iam.mpc.basesqlite.ListeProduit;

@SuppressLint("NewApi")
public class NewList extends ActionBarActivity {

	static Spinner spinner;
	static DatabaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_list);

		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
				
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_list, menu);	 
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	public void onClickButton(View view){
		EditText nomliste = (EditText)this.findViewById(R.id.editNomListe);
		DatabaseHelper db;
		if (nomliste.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(), "Erreur: champ nom vide", Toast.LENGTH_SHORT).show();
		}else {
			db = new DatabaseHelper(getApplicationContext());
			String nom;
			if(db.getUtilisateurRowCount()>0){
				nom = db.getUtilisateurName();
			}else {
				nom = "local";
			}
			Liste liste = new Liste(0, nomliste.getText().toString(), nom);
			Liste listeModel;
			db.createListe(liste);
			liste = db.getListeByName(nomliste.getText().toString());
			String nom_model = spinner.getSelectedItem().toString();
			if (nom_model!="") {
				listeModel = db.getListeByName(nom_model);
				List<ListeProduit> lstp = db.getAllListeProduit(listeModel.getId_liste());
				for(ListeProduit lst:lstp){
					int id = lst.getId_produit();
					String qte = lst.getQte_produit();
					ListeProduit lstprod = new ListeProduit(liste.getId_liste(),id);
					lstprod.setQte_produit(qte);
					db.createListeProduit(lstprod);
				}
			}
			finish();
		}
		
	}
	
	public static void populateSpinner(View v){
		
		db = new DatabaseHelper(v.getContext());
		spinner = (Spinner) v.findViewById(R.id.modelSpinner);
		List<Liste> listes = db.getListeModel();
		
		List<String> listesNames = new ArrayList<String>();
		listesNames.add("");
		for (Liste lst:listes) {
			listesNames.add(lst.getNom_liste());
		}
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(v.getContext(),
			android.R.layout.simple_spinner_item, listesNames);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
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
			View rootView = inflater.inflate(R.layout.fragment_new_list,
					container, false);

			populateSpinner(rootView);
			return rootView;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
        
        case android.R.id.home:
        	finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
	}
	
	

}
