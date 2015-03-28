package com.esgi.iam.mpc.webservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.basesqlite.Liste;
import com.esgi.iam.mpc.basesqlite.ListeProduit;
import com.esgi.iam.mpc.basesqlite.Produit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WebService {

    //private final String URL = "http://192.168.1.44/projects/MPC/";
    private final String URL = "http://10.66.126.147/projects/MPC/";
    
    Gson gson;
 
    public WebService() {
        gson = new Gson();
    }
    
    /**
     * function make Login Request
     * @param name
     * @param password
     * */
    public JSONObject loginUser(String name, String password){    	
    	// Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(URL);
        try {
            // Building Parameters    
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", "login"));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("password", password));
            httppost.setEntity(new UrlEncodedFormEntity(params));
            
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String json_string = EntityUtils.toString(response.getEntity());
            Log.e("Webservice", json_string);
            // getting JSON Object
            JSONObject json;
			try {
				json = new JSONObject(json_string);
				// return json
				return json;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}            
			return null;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }        
        return null;
    }
     
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String name, String email, String password){
    	// Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(URL);
        try {
            // Building Parameters    
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", "register"));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            httppost.setEntity(new UrlEncodedFormEntity(params));
            
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String json_string = EntityUtils.toString(response.getEntity());
             
            // getting JSON Object
            JSONObject json;
			try {
				json = new JSONObject(json_string);
				// return json
				return json;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}            
			return null;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }        
        return null;
    }
     
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHelper db = new DatabaseHelper(context);
        int count = db.getUtilisateurRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
    	DatabaseHelper db = new DatabaseHelper(context);
        db.resetTableUtilisateur();
        return true;
    }
    
    /**
     * Function to get Promotion
     * Return List of Promotion
     * */    
	@SuppressWarnings("unchecked")
	public List<Promotion> getPromo() {
    	// Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(URL);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("tag", "promotion"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String json_string = EntityUtils.toString(response.getEntity());
            return (List<Promotion>) gson.fromJson(json_string, new TypeToken<List<Promotion>>(){}.getType());

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return null;
        
        
    }
	
    /**
     * Function to get Produit
     * Return List of Produit
     * */ 
	@SuppressWarnings("unchecked")
	public List<Produit> getProduit() {
    	// Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(URL);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("tag", "produit"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            String json_string = EntityUtils.toString(response.getEntity());
            return (List<Produit>) gson.fromJson(json_string, new TypeToken<List<Produit>>(){}.getType());

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return null;
        
        
    }
	
    /**
     * Function to save List
     * */
	public void saveList(Context context, String name) {
    	// Create a new HttpClient and Post Header
		DatabaseHelper db = new DatabaseHelper(context);
		
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(URL);
        List<Liste> listes = db.getListeByNameUser(name);
        
        for(Liste liste:listes){
        	if (liste.getUid()==0) {
            	String jsonListe = new Gson().toJson(liste);
            	List<ListeProduit> links = db.getAllListeProduit(liste.getId_liste());
            	String jsonLinks = new Gson().toJson(links);
            	
                try {
                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                    nameValuePairs.add(new BasicNameValuePair("tag", "saveList"));
                    nameValuePairs.add(new BasicNameValuePair("list", jsonListe));
                    nameValuePairs.add(new BasicNameValuePair("links", jsonLinks));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);
                    String json_string = EntityUtils.toString(response.getEntity());
                    Log.e("Response", json_string);
                    try {
						JSONObject json = new JSONObject(json_string);
						int uid = Integer.parseInt(json.getString("uid"));
						Log.e("UID",Integer.toString(uid));
						liste.setUid(uid);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }   
			}
        }
        
        

     
        
    }
 
}