package com.esgi.iam.mpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.esgi.iam.mpc.webservice.Promotion;
import com.esgi.iam.mpc.webservice.WebService;

@SuppressLint("NewApi")
public class PromoActivity extends ActionBarActivity {
	
	static ListView list;
	HashMap<String, String> map;
	ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_promo);
		
	    mProgressDialog = new ProgressDialog(this);
	    mProgressDialog.setIndeterminate(false);
	    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    mProgressDialog.setMessage("Chargement, veuillez patientez");
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiNetwork != null && wifiNetwork.isConnected())
        {
        	//Toast.makeText(getApplicationContext(), "Wifi", Toast.LENGTH_SHORT).show();
        	getData();
        }else if (mobileNetwork != null && mobileNetwork.isConnected()) {
        	//Toast.makeText(getApplicationContext(), "Mobile Data", Toast.LENGTH_SHORT).show();
        	getData();
        }else {
   
        		Toast.makeText(getApplicationContext(), "Pas connection internet", Toast.LENGTH_SHORT).show();
    			finish();

        }
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.promo, menu);
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
            //startActivity(new Intent(TabNavigation.this,MainActivity.class)); 
        	finish();
            return true;
        case R.id.btn_synchro:
            ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetwork != null && wifiNetwork.isConnected())
            {
            	//Toast.makeText(getApplicationContext(), "Wifi", Toast.LENGTH_SHORT).show();
            	getData();
            }else if (mobileNetwork != null && mobileNetwork.isConnected()) {
            	//Toast.makeText(getApplicationContext(), "Mobile Data", Toast.LENGTH_SHORT).show();
            	getData();
            }else {
       
            		Toast.makeText(getApplicationContext(), "Pas connection internet", Toast.LENGTH_SHORT).show();
        			finish();

            }
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
			View rootView = inflater.inflate(R.layout.fragment_promo,
					container, false);
			list = (ListView) rootView.findViewById(R.id.listPromo);

			return rootView;
		}
	}
	
	private void getData() {
		new AsyncTask<Void, Void, List<Promotion>>() {
		
		protected void onPreExecute() {
			mProgressDialog.show();
		};
		
		@Override
		protected List<Promotion> doInBackground(Void... params) {
			WebService webService = new WebService();
			
			List<Promotion> promo = webService.getPromo();
			
			if (promo != null) {
				return promo;
			}
			
			return new ArrayList<Promotion>();
		};
		
		protected void onPostExecute(java.util.List<Promotion> result) {
			
			
		    ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		    
		    for(Promotion promos: result){
		    	map = new HashMap<String, String>();
		    	
		    	map.put("id_promo", Integer.toString(promos.getId_promo()));
		    	map.put("nom_mag", promos.getNom_mag());
		        map.put("desc_promo", promos.getDesc_promo());		        
		        /*Date date_debut = promos.getDate_debut();
		        SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy");
		        String dateDebutFormatee = formatDateJour.format(date_debut); 
		        map.put("date_debut", dateDebutFormatee);*/
		        map.put("date_debut", promos.getDate_debut());
		        map.put("date_fin", promos.getDate_fin());
		        /*Date date_fin = promos.getDate_fin();
		        String dateFinFormatee = formatDateJour.format(date_fin); 
		        map.put("date_fin", dateFinFormatee);*/
		        
		        listItem.add(map);
		    }
		    
		    SimpleAdapter adapter = new SimpleAdapter (getApplicationContext(), listItem, R.layout.liste_promo_info,
		               new String[] {"id_promo", "nom_mag", "desc_promo", "date_debut", "date_fin"}, new int[] {R.id.code_promo, R.id.nom_mag, R.id.desc_promo, R.id.date_debut, R.id.date_fin});

		    list.setAdapter(adapter);
		    mProgressDialog.dismiss();

		};
		
		}.execute();
	}
	
	

}
