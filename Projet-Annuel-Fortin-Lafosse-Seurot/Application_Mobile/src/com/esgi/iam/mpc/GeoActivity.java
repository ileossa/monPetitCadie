package com.esgi.iam.mpc;

import android.app.ActionBar;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeoActivity extends ActionBarActivity {

	// Google Map
    private GoogleMap googleMap;
    private ActionBar actionBar;
    private Marker marker;
    LatLng myPosition;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);
        
		actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
 
        try {
            // Loading map
            initilizeMap(); 
            
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {

        	googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        	googleMap.setMyLocationEnabled(true);
        	
        	// Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);

            if(location!=null){
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

             myPosition = new LatLng(latitude, longitude);
             float zoom = (float) 17.0;
             

            googleMap.addMarker(new MarkerOptions().position(myPosition).title("Vous êtes ici"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition,
                    zoom));
            
            String stringUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=supermarket+in+"+location+"&sensor=true&key=AIzaSyB9c-135hhPP7_bplmUpDxWND6dbJ1_hv4;";

 
	            // check if map is created successfully or not
	            if (googleMap == null) {
	                Toast.makeText(getApplicationContext(),
	                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
	                        .show();
	            }
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.geo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
        switch (item.getItemId()) {
        
        case android.R.id.home:
            //startActivity(new Intent(TabNavigation.this,MainActivity.class)); 
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
			View rootView = inflater.inflate(R.layout.fragment_geo, container,
					false);
			return rootView;
		}
	}

}
