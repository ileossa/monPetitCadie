package com.esgi.iam.mpc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.basesqlite.ListeProduit;
import com.esgi.iam.mpc.basesqlite.Produit;

@SuppressLint("NewApi")
public class DetailActivity extends ActionBarActivity {
	
	static int id_liste;
	static int id_produit;
	static DatabaseHelper db;
	static Produit produit;
	static TextView qteProduit;
	static Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		db = new DatabaseHelper(this);
		
		Bundle b = getIntent().getExtras();
	    id_liste = b.getInt("id_liste");
	    id_produit = b.getInt("id_produit");
	    
	    produit = db.getProduit(id_produit); 
	    

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		return super.onCreateOptionsMenu(menu);
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
	
	public void onClickBtnDelete (View v) {
     	db.deleteListeProduit(id_liste, id_produit);
     	finish();
	}
	
	public void onClickBtnSave (View v) {
		db = new DatabaseHelper(v.getContext());
		ListeProduit listeproduit = db.getListeProduit(id_liste, id_produit);
		if (qteProduit.getText().toString().equals("")) {
			listeproduit.setQte_produit("");
		}else {
			StringBuilder value = new StringBuilder();
			value.append(qteProduit.getText().toString());
			value.append(" ");
			value.append(spinner.getSelectedItem().toString());		
	     	String qte = value.toString();
	     	listeproduit.setQte_produit(qte);
		}
     	db.updateListeProduit(listeproduit);
     	finish();
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
			View rootView = inflater.inflate(R.layout.fragment_detail,
					container, false);
		    
		    spinner = (Spinner) rootView.findViewById(R.id.qteSpinner);
		    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.array_spinner, android.R.layout.simple_spinner_item);
		    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spinner.setAdapter(adapter);

		    ListeProduit listeproduit = db.getListeProduit(id_liste, id_produit);
		    String qte = listeproduit.getQte_produit();
		    
		    if (qte.equals("")==false) {
			    String[] parts = qte.split(" ");
			    String part1 = parts[0]; 
			    String part2 = parts[1];    
				
				qteProduit = (TextView) rootView.findViewById(R.id.qteProduit);
				qteProduit.setText(part1);
				int position = adapter.getPosition(part2);

				spinner.setSelection(position);
			}else {
				qteProduit = (TextView) rootView.findViewById(R.id.qteProduit);
				qteProduit.setText("");
				spinner.setSelection(0);
			}

		    
		    TextView nomProduit = (TextView) rootView.findViewById(R.id.labeNomProduit);
		    nomProduit.setText(produit.getNom_produit());

			return rootView;
		}
		
		
	}
	

}
