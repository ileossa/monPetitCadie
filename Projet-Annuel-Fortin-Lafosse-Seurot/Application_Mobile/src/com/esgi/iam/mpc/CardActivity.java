package com.esgi.iam.mpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.iam.mpc.basesqlite.Carte;
import com.esgi.iam.mpc.basesqlite.DatabaseHelper;

@SuppressLint("NewApi")
public class CardActivity extends ActionBarActivity {

	static ListView listeCarte;
	static DatabaseHelper db;
	static HashMap<String, String> map;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
		
		db = new DatabaseHelper(this);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		updateListView();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.card, menu);	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.button1:
	        	startActivity(new Intent(this,NewCard.class)); 
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
	  if (v.getId()==R.id.listCard) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        String key = ((TextView) info.targetView.findViewById(R.id.code_carte)).getText().toString();
        int code = Integer.parseInt(key); 
        Carte carte = db.getCarte(code);
        
	    menu.setHeaderTitle(carte.getNom_carte());
	    String[] menuItems = {"Supprimer"};
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
	  }
	}
	
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        String key = ((TextView) info.targetView.findViewById(R.id.code_carte)).getText().toString();
        int code = Integer.parseInt(key); 
       
        switch (item.getItemId()) {
        case 0:
        	db.deleteCarte(code);
        	Toast.makeText(getApplicationContext(), "Carte n° "+code+" supprimée", Toast.LENGTH_SHORT).show();
        	updateListView();
            return (true);
        }    
        return (super.onOptionsItemSelected(item));

    }
    
    public void updateListView() {
    	db = new DatabaseHelper(this);
		listeCarte = (ListView) this.findViewById(R.id.listCard);
		
		List<Carte> listeCartes = db.getAllCarte();
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
	    
	    for(Carte cartes: listeCartes){
	    	map = new HashMap<String, String>();
	    	
	    	map.put("code_carte", Integer.toString(cartes.getId_carte()));
	        map.put("nom_carte", cartes.getNom_carte());

	        
	        listItem.add(map);
	    }
	    
	    SimpleAdapter adapter = new SimpleAdapter (this, listItem, R.layout.liste_carte_info,
	               new String[] {"code_carte", "nom_carte"}, new int[] {R.id.code_carte, R.id.nom_carte});

	    listeCarte.setAdapter(adapter);
	    

		
	    listeCarte.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String key = ((TextView) view.findViewById(R.id.code_carte)).getText().toString();
				int code = Integer.parseInt(key);
				
			    Intent i = new Intent (parent.getContext(), DetailCard.class);
			    i.putExtra("id_carte", code);
      	        startActivity(i);
			}
		});


	    
	    registerForContextMenu(listeCarte);
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
			View rootView = inflater.inflate(R.layout.fragment_card, container,
					false);
						
			return rootView;
		}
	}
	
	

}
