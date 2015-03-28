package com.esgi.iam.mpc;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.webservice.WebService;

public class LoginBisActivity extends Activity {
	
	private EditText inputName;
	private EditText inputPassword;
	ProgressDialog mProgressDialog;
	WebService webservice;
	String name;
	String password;
	
	// JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_login_bis);
		
		mProgressDialog = new ProgressDialog(this);
	    mProgressDialog.setIndeterminate(false);
	    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    mProgressDialog.setMessage("Connexion, veuillez patientez");
	    
	    webservice = new WebService();

		inputName = (EditText) findViewById(R.id.nameValue);
        inputPassword = (EditText) findViewById(R.id.passwordValue);
        
	}
	
	
	public void onClickConnexion (View v){
        
        new AsyncTask<Void, Void, JSONObject>() {
			
    		protected void onPreExecute() {
    			mProgressDialog.show();
    			name = inputName.getText().toString();
    	        password = inputPassword.getText().toString();  
    		};
    		
    		@Override
    		protected JSONObject doInBackground(Void... params) {
  	            JSONObject json = webservice.loginUser(name, password);
    	        if (json != null) {
					return json;
				}
    			return new JSONObject();
    		};
    		
    		protected void onPostExecute(JSONObject json) {
    	        // check for login response
    	        try {
    	            if (json.getString(KEY_SUCCESS) != null) {
    	                String res = json.getString(KEY_SUCCESS);
    	                if(Integer.parseInt(res) == 1){
    	                    // user successfully logged in
    	                    // Store user details in SQLite Database
    	                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
    	                    JSONObject json_user = json.getJSONObject("user");
    	                     
    	                    // Clear all previous data in database
    	                    webservice.logoutUser(getApplicationContext());
    	                    db.createUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL));                      
    	                    Toast.makeText(getApplicationContext(), "Connexion réussi", Toast.LENGTH_SHORT).show(); 
    	                    // Launch activity	
    	                    finish();
    	                }else{
    	                    // Error in login
    	                    Toast.makeText(getApplicationContext(), "Erreur, pseudo ou mot de passe incorrect!", Toast.LENGTH_SHORT).show();
    	                }
    	            }
    	        } catch (JSONException e) {
    	            e.printStackTrace();
    	        }  
    	        mProgressDialog.dismiss();
    		};
    		
    		}.execute();

	}
	
	public void onClickCancel (View v) {
		finish();
	}

}
