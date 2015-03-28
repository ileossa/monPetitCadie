package com.esgi.iam.mpc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.basesqlite.Utilisateur;

@SuppressLint("NewApi")
public class Settings extends ActionBarActivity {

	DatabaseHelper db;
	TextView uservalue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		 db = new DatabaseHelper(this);
		 
		 uservalue = (TextView) findViewById(R.id.valueUser);
		 if (db.getUtilisateurRowCount()>0) {
			 Utilisateur user = db.getUtilisateur();
			 uservalue.setText(user.getName());
		 }else {
			 uservalue.setText("non conecté");
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
	
	@Override
	protected void onResume() {
		uservalue = (TextView) findViewById(R.id.valueUser);
		 if (db.getUtilisateurRowCount()>0) {
			 Utilisateur user = db.getUtilisateur();
			 uservalue.setText(user.getName());
		 }else {
			 uservalue.setText("non conecté");
		 }
		super.onResume();
	}
	
	public void onClickChangeUser (View v) {
	    Intent i = new Intent (this, LoginBisActivity.class);
	    startActivity(i);
	}
	
	public void onCLickLogout (View v) {
		db.resetTableUtilisateur();
		uservalue = (TextView) findViewById(R.id.valueUser);
		 if (db.getUtilisateurRowCount()>0) {
			 Utilisateur user = db.getUtilisateur();
			 uservalue.setText(user.getName());
		 }else {
			 uservalue.setText("non conecté");
		 }
			 

	}
}	
