package com.esgi.iam.mpc;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.basesqlite.Liste;
import com.esgi.iam.mpc.webservice.WebService;

@SuppressLint("NewApi")
public class MesListeActivity extends ActionBarActivity {
	
	ListView lv;
	DatabaseHelper db;
	WebService webservice;
	private String[] nom_liste;
	private SimpleCursorAdapter dataAdapter;
	ProgressDialog saveProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mes_liste);
		
		saveProgressDialog = new ProgressDialog(this);
	    saveProgressDialog.setIndeterminate(false);
	    saveProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    saveProgressDialog.setMessage("Sauvegarde des listes, veuillez patientez");
		webservice = new WebService();
		
		db = new DatabaseHelper(getApplicationContext());
		lv = (ListView)findViewById(R.id.caddie_list);
	
		updateListView();
		
		nom_liste = db.getAllListeName();
             
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String key = ((TextView) view.findViewById(R.id.code)).getText().toString();
				int code = Integer.parseInt(key);
				
			    Intent i = new Intent (parent.getContext(), TabNavigation.class);
			    i.putExtra("id_liste", code);
      	        startActivity(i);
			}
		});
		
		registerForContextMenu(lv);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mes_liste, menu);	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.button1:
	        	startActivity(new Intent(MesListeActivity.this,NewList.class)); 
	            return true;
	        case R.id.saveListe:
	        	new AsyncTask<Void, Void, Void>() {
	    			
	        		protected void onPreExecute() {
	        			saveProgressDialog.show();
	        		};
	        		
	        		@Override
	        		protected Void doInBackground(Void... params) {
	        			webservice.saveList(getApplicationContext(), db.getUtilisateurName());
	        			onPostExecute();
						return null;
	        		};
	        		
	        		protected void onPostExecute() {
	        			saveProgressDialog.dismiss();
	        		};
	        		
	        		}.execute();
	        	
	        	//TODO
	            return true;
	        case R.id.getListe:
	        	//TODO
	            return true;
	       
	        case android.R.id.home:
	           	finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.caddie_list) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    
	    menu.setHeaderTitle(nom_liste[info.position]);
	    String[] menuItems = {"Supprimer","Définir comme modele","Partager"};
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
	  }
	}
	
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        String key = ((TextView) info.targetView.findViewById(R.id.code)).getText().toString();
        int code = Integer.parseInt(key); 
       
        switch (item.getItemId()) {
        case 0:
        	db.deleteListe(code);
        	Toast.makeText(getApplicationContext(), "Liste n° "+code+" supprimée", Toast.LENGTH_SHORT).show();
        	updateListView();
            return (true);
        case 1:
        	Liste lst = db.getListe(code);
        	db.listeIsModele(code, true);
        	Toast.makeText(getApplicationContext(), lst.getNom_liste()+" ajouté comme modèle", Toast.LENGTH_SHORT).show();
            return (true);
            
        case 2:
		    Intent i = new Intent (this, SendEmail.class);
		    i.putExtra("id_liste", code);
  	        startActivity(i);
  	        finish();        	
            return (true);
        }

        return (super.onOptionsItemSelected(item));
    }
	
	public void updateListView() {
		String nom;
		if (db.getUtilisateurRowCount()>0) {
			nom = db.getUtilisateurName();
		}else {
			nom = "local";
		}
		Cursor cursor = db.getAllListeCursor(nom);
		
		  // The desired columns to be bound
		  String[] columns = new String[] {
		    "_id",
		    "nom_liste",
		  };
		 
		  // the XML defined views which the data will be bound to
		  int[] to = new int[] {
		    R.id.code,
		    R.id.name,
		  };
		  
		  // create the adapter using the cursor pointing to the desired data
		  //as well as the layout information
		  dataAdapter = new SimpleCursorAdapter(
		    this, R.layout.liste_info,
		    cursor,
		    columns,
		    to,
		    0);
		  		  
		  lv.setAdapter(dataAdapter);
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
			View rootView = inflater.inflate(R.layout.fragment_mes_liste, container,false);
			
			return rootView;
		}
	}
	
	@Override
	protected void onResume() {
		
		updateListView();
		super.onResume();
	}
}
