package com.esgi.iam.mpc.fragments;

import java.util.List;

import com.esgi.iam.mpc.DetailActivity;
import com.esgi.iam.mpc.MesListeActivity;
import com.esgi.iam.mpc.R;
import com.esgi.iam.mpc.TabNavigation;
import com.esgi.iam.mpc.adapter.ListProductAdapter;
import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.basesqlite.ListeProduit;
import com.esgi.iam.mpc.basesqlite.Produit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.webkit.WebView.FindListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;

public class MyListFragment extends Fragment{
	
	ListView lv;
	TextView txt;
	DatabaseHelper db;
	List<ListeProduit> listeproduits;
	CheckBox cb;
	int id_liste;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
    	db = new DatabaseHelper(getActivity());
    	
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        lv = (ListView) rootView.findViewById(R.id.product_list);
    	id_liste =  (int)((TabNavigation) getActivity()).getIdListe();
    	
    	updateListView(rootView); 
    	    	    	
        return rootView;
    }
    
    
	public void updateListView(View view) {

		lv = (ListView) view.findViewById(R.id.produits_list);
		txt = (TextView) view.findViewById(R.id.txtview_produit_liste);
		listeproduits = db.getAllListeProduit(id_liste);
		
		ListProductAdapter dataAdapter = new ListProductAdapter(getActivity(),listeproduits);
			  			  
		lv.setAdapter(dataAdapter);
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
	        	String key = ((TextView) v.findViewById(R.id.code_produit)).getText().toString();
	        	int id_produit = Integer.parseInt(key);
	    	    Intent i = new Intent (getActivity(), DetailActivity.class);
	    	    ListeProduit listeproduit = db.getListeProduit(id_liste, id_produit);
	    	    i.putExtra("id_liste", id_liste);
	    	    i.putExtra("id_produit", id_produit);	    	    
		        startActivity(i);
	        }
	    });
		
		if (listeproduits.isEmpty()){
			lv.setVisibility(lv.INVISIBLE);
			txt.setVisibility(txt.VISIBLE);
		}else{
			txt.setVisibility(txt.INVISIBLE);
			lv.setVisibility(lv.VISIBLE);
		}

	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    // Make sure that we are currently visible
	    if (this.isVisible()) {
	        // If we are becoming invisible, then...
	        if (isVisibleToUser) {
	        	id_liste =  (int)((TabNavigation) getActivity()).getIdListe();
	        	this.updateListView(getActivity().findViewById(R.id.fragment_list));
	        }
	    }
	}
	
	@Override
	public void onResume() {
		this.updateListView(getActivity().findViewById(R.id.fragment_list));
		super.onResume();
	}


	
}