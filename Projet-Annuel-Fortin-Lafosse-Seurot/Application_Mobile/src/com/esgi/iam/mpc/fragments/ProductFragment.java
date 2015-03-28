package com.esgi.iam.mpc.fragments;

import com.esgi.iam.mpc.R;
import com.esgi.iam.mpc.TabNavigation;
import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.basesqlite.ListeProduit;
import com.esgi.iam.mpc.basesqlite.Produit;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ProductFragment extends Fragment {

	ListView lv;
	Button btn;
	TextView txt,txt_back;
	private SimpleCursorAdapter dataAdapter;
	DatabaseHelper db;
	int id_parent, id_liste;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

	db = new DatabaseHelper(getActivity());
	
	View rootView = inflater.inflate(R.layout.fragment_product, container, false);
    lv = (ListView) rootView.findViewById(R.id.product_list);
    btn = (Button) rootView.findViewById(R.id.back_btn);
    
    btn.setOnClickListener(new View.OnClickListener() {                       
        @Override
        public void onClick(View v) {
    		int code = db.getProduitParent(id_parent);
    		
    		Cursor cursor = db.getAllProduitByParent(code);
    		if (cursor.moveToFirst()){
    			
    		    Fragment newFragment = newInstance(code);
    	
    		    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    	
    		    transaction.replace(R.id.fragment_product, newFragment);
    		    transaction.addToBackStack(null);
    	
    		    transaction.commit();
    		}
        }

    });
    
    id_liste =  (int)((TabNavigation) getActivity()).getIdListe();
    
    try{
    	id_parent =  this.getArguments().getInt("id_parent");	
    }catch( NullPointerException exception){
    	id_parent = 0;
    }
    
    if (id_parent == 0) {
    	if(btn.getVisibility() == View.VISIBLE){
    		btn.setVisibility(View.INVISIBLE);
    	}
	}else{
		btn.setVisibility(View.VISIBLE);
	}
    
	
	updateListView(id_parent, rootView);
	
	lv.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {

			String key = ((TextView) view.findViewById(R.id.code_produit)).getText().toString();
			int code = Integer.parseInt(key);
			
			Cursor cursor = db.getAllProduitByParent(code);
			if (cursor.moveToFirst()){
			
		    Fragment newFragment = newInstance(code);

		    FragmentTransaction transaction = getFragmentManager().beginTransaction();

		    transaction.replace(R.id.fragment_product, newFragment);
		    transaction.addToBackStack(null);

		    transaction.commit();
		    			  
			}
			else{
				if (db.isInListe(id_liste, code)) {
					Toast.makeText(getActivity(), "Le produit est déjà dans la liste!", Toast.LENGTH_SHORT).show();
				}else{
					ListeProduit listeproduit = new ListeProduit(id_liste, code);
					db.createListeProduit(listeproduit);
					Toast.makeText(getActivity(), "Produit ajouté a la liste", Toast.LENGTH_SHORT).show();
				}

			}
				

		}
	});
	
	registerForContextMenu(lv);
	
    return rootView;
  }
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
		if (getUserVisibleHint()) {
		  if (v.getId()==R.id.product_list) {
		    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		    
	        String key = ((TextView) info.targetView.findViewById(R.id.code_produit)).getText().toString();
	        int code = Integer.parseInt(key); 
	        Produit produit = db.getProduit(code);
	        
		    menu.setHeaderTitle(produit.getNom_produit());
		    String[] menuItems = {"Ajouter aux favoris"};
	        if (db.getProduitParent(code)!=0) {
	        	menu.add(1, 0, 0, menuItems[0]);
			}	      

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
	        case 0:

	        	db.produitIsFavori(code, true);
	        	Toast.makeText(getActivity(), "Produit ajouté aux favoris", Toast.LENGTH_SHORT).show();
	        }
    	}
        return (super.onOptionsItemSelected(item));
    }
    
    public static ProductFragment newInstance(int id_parent) {
    	ProductFragment fragment = new ProductFragment();
    	Bundle args = new Bundle();
    	args.putInt("id_parent", id_parent);
    	fragment.setArguments(args);
    	return fragment;
    }
    
    public void updateListView(int id_parent, View view) {

		lv = (ListView) view.findViewById(R.id.product_list);
		
		Cursor cursor = db.getAllProduitByParent(id_parent);
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
		}

	}
            
} 
