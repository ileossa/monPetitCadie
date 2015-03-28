package com.esgi.iam.mpc.fragments;

import com.esgi.iam.mpc.R;
import com.esgi.iam.mpc.TabNavigation;
import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.basesqlite.ListeProduit;
import com.esgi.iam.mpc.basesqlite.Produit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FavoriesFragment extends Fragment {
	ListView lv;
	TextView txt;
	DatabaseHelper db;
	int id_liste;
	private SimpleCursorAdapter dataAdapter;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_favories, container, false);
        lv = (ListView) rootView.findViewById(R.id.favori_product);
        txt = (TextView) rootView.findViewById(R.id.label_favori);
        
        id_liste =  (int)((TabNavigation) getActivity()).getIdListe();
        
    	db = new DatabaseHelper(getActivity());    	
        
    	updateListView(rootView);
    	    	
    	registerForContextMenu(lv);
    	
    	lv.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view,
    				int position, long id) {

    			String key = ((TextView) view.findViewById(R.id.code_produit)).getText().toString();
    			int code = Integer.parseInt(key);
    			
    				if (db.isInListe(id_liste, code)) {
    					Toast.makeText(getActivity(), "Le produit est déjà dans la liste!", Toast.LENGTH_SHORT).show();
    				}else{
    					ListeProduit listeproduit = new ListeProduit(id_liste, code);
    					db.createListeProduit(listeproduit);
    					Toast.makeText(getActivity(), "Produit ajouté a la liste", Toast.LENGTH_SHORT).show();
    				}
    		}
    	});
    				

         
        return rootView;
    }
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
		if (getUserVisibleHint()) {
		  if (v.getId()==R.id.favori_product) {
		    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		    
	        String key = ((TextView) info.targetView.findViewById(R.id.code_produit)).getText().toString();
	        int code = Integer.parseInt(key); 
	        Produit produit = db.getProduit(code);
	        
		    menu.setHeaderTitle(produit.getNom_produit());
		    String[] menuItems = {"Supprimer des favoris"};
		    	menu.add(1, 1, 0, menuItems[0]);      

		  }
		}
	}
	
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	if (getUserVisibleHint()) {
	        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
	                .getMenuInfo();
	        String key = ((TextView) info.targetView.findViewById(R.id.code_produit)).getText().toString();
	        int code = Integer.parseInt(key); 
	        Produit produit = db.getProduit(code);
	       
	        switch (item.getItemId()) {
	        case 1:
	        	db.produitIsFavori(code, false);
	        	Toast.makeText(getActivity(), "Produit supprimé des favoris", Toast.LENGTH_SHORT).show();
	        	updateListView(this.getView());
	        }
    	}
        return (super.onOptionsItemSelected(item));
    }
    
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    // Make sure that we are currently visible
	    if (this.isVisible()) {
	        // If we are becoming invisible, then...
	        if (isVisibleToUser) {
	        	this.updateListView(getActivity().findViewById(R.id.favori_product));
	        }
	    }
	}
    
    public void updateListView(View view) {

		lv = (ListView) view.findViewById(R.id.favori_product);
		
		Cursor cursor = db.getAllProduitFavori();
		if (cursor.moveToFirst()){

			// The desired columns to be bound
			  String[] columns = new String[] {
			    "_id",
			    "nom_produit",
			  };
			 
			  // the XML defined views which the data will be bound to
			  int[] to = new int[] {
			    R.id.code_produit,
			    R.id.nom_produit,
			  };
			  
			  // create the adapter using the cursor pointing to the desired data
			  //as well as the layout information
			  dataAdapter = new SimpleCursorAdapter(
			    view.getContext(), R.layout.liste_produit_info,
			    cursor,
			    columns,
			    to,
			    0);

			  lv.setAdapter(dataAdapter);
			  
			  txt.setVisibility(txt.INVISIBLE);
			  lv.setVisibility(lv.VISIBLE);

		}else {
			lv.setVisibility(lv.INVISIBLE);
			txt.setVisibility(txt.VISIBLE);
		}
		
		/*if (lv.getCount() > 0){
			txt.setVisibility(txt.INVISIBLE);
			lv.setVisibility(lv.VISIBLE);
		}else{
			lv.setVisibility(lv.INVISIBLE);
			txt.setVisibility(txt.VISIBLE);
		}*/

	}

}
