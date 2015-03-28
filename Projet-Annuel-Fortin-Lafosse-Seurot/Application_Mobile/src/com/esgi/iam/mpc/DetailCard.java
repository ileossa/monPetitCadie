package com.esgi.iam.mpc;

import java.io.File;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.esgi.iam.mpc.basesqlite.Carte;
import com.esgi.iam.mpc.basesqlite.DatabaseHelper;

@SuppressLint("NewApi")
public class DetailCard extends ActionBarActivity {

	static DatabaseHelper db;
	static ActionBar actionBar;
	static ImageView img;
	static int id_carte;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_card);
		
		actionBar = getSupportActionBar();
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case android.R.id.home:
	           	finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
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
			View rootView = inflater.inflate(R.layout.fragment_detail_card,
					container, false);
			
			img = (ImageView) rootView.findViewById(R.id.photo_carte);
						
			db = new DatabaseHelper(getActivity());
			
			Bundle b = getActivity().getIntent().getExtras();
		    int id_carte = b.getInt("id_carte");
		    
		    Carte carte = new Carte();
		    carte = db.getCarte(id_carte);
		    actionBar.setTitle(carte.getNom_carte());
		    File image = new  File(carte.getPhoto()); 
		    BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
 
            final Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(),
                    options);
 
            img.setImageBitmap(bitmap);
			
			return rootView;
		}
	}

}
