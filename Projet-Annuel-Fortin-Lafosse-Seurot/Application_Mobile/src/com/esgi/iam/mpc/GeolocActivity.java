package com.esgi.iam.mpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import com.esgi.iam.mpc.googlemap.PlaceJSONParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class GeolocActivity extends FragmentActivity implements
		LocationListener {

	GoogleMap mGoogleMap;
	Spinner mSprPlaceType;

	String[] mPlaceType = null;
	String[] mPlaceTypeName = null;

	double mLatitude = 0;
	double mLongitude = 0;

	HashMap<String, String> mMarkerPlaceLink = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geoloc);

		ConnectivityManager cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobileNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if ((wifiNetwork != null && wifiNetwork.isConnected())
				|| (mobileNetwork != null && mobileNetwork.isConnected())) {
			// Getting Google Play availability status
			int status = GooglePlayServicesUtil
					.isGooglePlayServicesAvailable(getBaseContext());
			if (status != ConnectionResult.SUCCESS) { // Google Play Services
														// are not available

				int requestCode = 10;
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,
						this, requestCode);
				dialog.show();
			} else { // Google Play Services are available

				// Getting reference to the SupportMapFragment
				// SupportMapFragment fragment = ( SupportMapFragment)
				// getSupportFragmentManager().findFragmentById(R.id.map);

				// Getting Google Map
				mGoogleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();

				// Enabling MyLocation in Google Map
				mGoogleMap.setMyLocationEnabled(true);

				// Getting LocationManager object from System Service
				// LOCATION_SERVICE
				LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

				// Creating a criteria object to retrieve provider
				Criteria criteria = new Criteria();

				// Getting the name of the best provider
				String provider = locationManager.getBestProvider(criteria,
						true);

				// Getting Current Location From GPS
				Location location = locationManager
						.getLastKnownLocation(provider);

				if (location != null) {
					onLocationChanged(location);
				}

				locationManager
						.requestLocationUpdates(provider, 20000, 0, this);

				// Creating a new non-ui thread task to download Google place
				// json data
				PlacesTask placesTask = new PlacesTask();

				// Invokes the "doInBackground()" method of the class PlaceTask
				placesTask
						.execute("https://maps.googleapis.com/maps/api/place/search/json?keyword=supermarket&location="
								+ location.getLatitude()
								+ ","
								+ location.getLongitude()
								+ "&radius=5000&sensor=false&key=AIzaSyBp3Qsiygj9lfWrvgYnpF8FU94IUbmeUL4");

			}

		} else {

			Toast.makeText(getApplicationContext(), "Pas connection internet",
					Toast.LENGTH_SHORT).show();
			finish();

		}

	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	/** A class, to download Google Places */
	private class PlacesTask extends AsyncTask<String, Integer, String> {

		String data = null;

		// Invoked by execute() method of this object
		@Override
		protected String doInBackground(String... url) {
			try {
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(String result) {
			ParserTask parserTask = new ParserTask();

			// Start parsing the Google places in JSON format
			// Invokes the "doInBackground()" method of the class ParseTask
			parserTask.execute(result);
		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<HashMap<String, String>>> {

		JSONObject jObject;

		// Invoked by execute() method of this object
		@Override
		protected List<HashMap<String, String>> doInBackground(
				String... jsonData) {

			List<HashMap<String, String>> places = null;
			PlaceJSONParser placeJsonParser = new PlaceJSONParser();

			try {
				jObject = new JSONObject(jsonData[0]);

				/** Getting the parsed data as a List construct */
				places = placeJsonParser.parse(jObject);

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return places;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(List<HashMap<String, String>> list) {

			if (list != null) {
				// Clears all the existing markers
				mGoogleMap.clear();

				for (int i = 0; i < list.size(); i++) {

					// Creating a marker
					MarkerOptions markerOptions = new MarkerOptions();

					// Getting a place from the places list
					HashMap<String, String> hmPlace = list.get(i);

					// Getting latitude of the place
					double lat = Double.parseDouble(hmPlace.get("lat"));

					// Getting longitude of the place
					double lng = Double.parseDouble(hmPlace.get("lng"));

					// Getting name
					String name = hmPlace.get("place_name");

					// Getting vicinity
					// String vicinity = hmPlace.get("vicinity");

					LatLng latLng = new LatLng(lat, lng);

					// Setting the position for the marker
					markerOptions.position(latLng);

					// Setting the title for the marker.
					// This will be displayed on taping the marker
					// markerOptions.title(name + " : " + vicinity);
					markerOptions.title(name);
					// Placing a marker on the touched position
					Marker m = mGoogleMap.addMarker(markerOptions);

					// Linking Marker id and place reference
					mMarkerPlaceLink.put(m.getId(), hmPlace.get("reference"));

				}

			} else {
				Toast.makeText(getApplicationContext(),
						"Erreur: pas de connection internet",
						Toast.LENGTH_SHORT).show();

			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (item.getItemId()) {

		case android.R.id.home:
			// startActivity(new Intent(TabNavigation.this,MainActivity.class));
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		mLatitude = location.getLatitude();
		mLongitude = location.getLongitude();
		LatLng latLng = new LatLng(mLatitude, mLongitude);

		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

}