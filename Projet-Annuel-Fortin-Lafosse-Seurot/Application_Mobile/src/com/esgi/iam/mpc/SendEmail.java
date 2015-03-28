package com.esgi.iam.mpc;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.basesqlite.Liste;
import com.esgi.iam.mpc.basesqlite.ListeProduit;

@SuppressLint("NewApi")
public class SendEmail extends ActionBarActivity {

	private static EditText recipient;
	private static String subject;
	private static EditText body;
	static Button sendBtn;
	static DatabaseHelper db;
	
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_send_email);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	    
   }

   protected  void sendEmail() {

      String[] recipients = {recipient.getText().toString()};
      Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
      // prompts email clients only
      email.setType("message/rfc822");

      email.putExtra(Intent.EXTRA_EMAIL, recipients);
      email.putExtra(Intent.EXTRA_SUBJECT, subject);
      email.putExtra(Intent.EXTRA_TEXT, body.getText().toString());

      try {
	    // the user can choose the email client
         startActivity(Intent.createChooser(email, "Choose an email client from..."));
     
      } catch (android.content.ActivityNotFoundException ex) {
         Toast.makeText(SendEmail.this, "No email client installed.",
        		 Toast.LENGTH_LONG).show();
      }
      
   }
   

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu); 
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
			View rootView = inflater.inflate(R.layout.fragment_send_email,
					container, false);
			
			body = (EditText) rootView.findViewById(R.id.editMessage);
			sendBtn = (Button) rootView.findViewById(R.id.btn_envoyer);
		    recipient = (EditText) rootView.findViewById(R.id.editAdress);
		    subject = "Partage de liste de course";
		    
		    Bundle b = getActivity().getIntent().getExtras();
		    int id_liste = b.getInt("id_liste");
		    
		    db = new DatabaseHelper(getActivity());
		    Liste liste = db.getListe(id_liste);
		    String nomListe = liste.getNom_liste();
		    List<ListeProduit> produits = db.getAllListeProduit(id_liste);
		    String Newligne=System.getProperty("line.separator"); 
		    String tempbody = nomListe + ":" + Newligne + Newligne;	    
		    
		    for(ListeProduit prods: produits){
		    	tempbody += db.getProduitName(prods.getId_produit()) + " " + prods.getQte_produit() + Newligne;
		    }
		    
		    tempbody += Newligne + " - Envoyé depuis l'application MonPetitCaddie";
		    body.setText(tempbody);
		    
		    return rootView;
		}
	}
	
	public void onClickBtnEnvoyer(View v){ 
        sendEmail();
        // after sending the email, clear the fields
        recipient.setText("");
        body.setText("");
	}

}
