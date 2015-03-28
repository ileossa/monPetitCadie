package com.esgi.iam.mpc;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.iam.mpc.adapter.TabsPagerAdapter;
import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.basesqlite.Liste;



public class TabNavigation extends FragmentActivity implements TabListener{

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    DatabaseHelper db;
    

    // Tab titles
    private String[] tabs = { "Ma liste", "Produits", "Favoris" };

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		
		db = new DatabaseHelper(getApplicationContext());
		
		actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    Bundle b = getIntent().getExtras();
	    int id_liste = b.getInt("id_liste");
	    
	    Liste liste = new Liste();
	    //Toast.makeText(getApplicationContext(), "Liste n° "+id_liste, Toast.LENGTH_SHORT).show();
	    liste = db.getListe(id_liste);
	    actionBar.setTitle(liste.getNom_liste());
	    
	    
		
		 // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        	 
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
                
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            	
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    
	}
	
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

	
	public boolean onCreateOptionMenu (Menu menu){
		getMenuInflater().inflate(R.menu.main, menu);
		return true;	
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
			View rootView = inflater.inflate(R.layout.fragment_list, container,
					false);
			return rootView;
		}
	}
	
	public int getIdListe(){
	    Bundle b = getIntent().getExtras();
	    int id_liste = b.getInt("id_liste");
		return id_liste;
	}

}
