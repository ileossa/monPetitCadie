package testFenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import dataBase.Connecteur;
import dataBase.UserMethodes;
import dataFactory.Mail;
import dataFactory.User;

public class ManageUser {
	
	public static Connecteur con = new Connecteur();
	public static UserMethodes pm = new UserMethodes();
	public static List<User> userList = new ArrayList<User>();
	public static String URL = "http://10.66.126.147/projects/MPC/";

	public ManageUser(){
		
	}
	
	public static void manageUser() {
		// Init the windows
		final JFrame fenetre = new JFrame("Gestion des Utilisateurs");
		
		String [] colonnes = {"id user", "Nom", "email"};
	      
		final DefaultTableModel modele = new DefaultTableModel(colonnes, 0);
		JTable tableau = new JTable(modele);
		tableau.setAutoCreateRowSorter(true);
		
		fenetre .add(new JScrollPane(tableau));
		
		int toucheRaccourcis = java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
			
		setData(modele);
		
		JMenuItem menuReset = new JMenuItem("Réinitialiser mot de passe");
		menuReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ev) {
				ResetPassword rp = new ResetPassword();
				int reponse = JOptionPane.showConfirmDialog(fenetre, rp, "Mot de passe réinitialiser", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(reponse == JOptionPane.OK_OPTION){
					User user = rp.getUser();
					JOptionPane.showMessageDialog(fenetre, "Password reset for user: "+user.getName());
					JSONObject json = resetPassword(user.getName());
					
					try {
						String password = json.getString("newpassword");
						Mail mail = new Mail();
						mail.sendMail(user.getName(), user.getEmail(), password);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

				}
			}
		});
		
		JMenuItem menuSend = new JMenuItem("Envoyer mail");
		menuSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ev) {
				ResetPassword rp = new ResetPassword();
				int reponse = JOptionPane.showConfirmDialog(fenetre, rp, "Envoyer un mail", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(reponse == JOptionPane.OK_OPTION){
					User user = rp.getUser();
					JOptionPane.showMessageDialog(fenetre, "Password reset for user: "+user.getName());
					JSONObject json = resetPassword(user.getName());
					
					try {
						String password = json.getString("newpassword");
						Mail mail = new Mail();
						mail.sendMail(user.getName(), user.getEmail(), password);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

				}
			}
		});
		
		JMenuItem menuQuitter = new JMenuItem("Quitter");
		menuQuitter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ev) {
				// TODO Auto-generated method stub
				if(JOptionPane.showConfirmDialog(fenetre, "Voulez-vous quitter ?", "Quitter", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		
		
		JMenuBar  barreMenu = new JMenuBar();
		fenetre.setJMenuBar(barreMenu);
		JMenu menuFichier = new JMenu("Fichier");
		barreMenu.add(menuFichier);
		menuFichier.add(menuReset);
		menuFichier.add(menuQuitter);
		
		fenetre.setSize(1600, 900);
		fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		fenetre.setVisible(true);
		
		con.close();

	}
	
	public static void setData(DefaultTableModel modele){
		if (modele.getRowCount() > 0) {
		    for (int i = modele.getRowCount() - 1; i > -1; i--) {
		        modele.removeRow(i);
		    }
		}
		userList = pm.listeUser(con);
		for(User user:userList){	
			modele.addRow(new String[] {Integer.toString(user.getId_utilisateur()), user.getName(), user.getEmail()});
		}
	}
	
	public static JSONObject resetPassword(String name){    	
    	// Create a new HttpClient and Post Header
		HttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost(URL);
        try {
            // Building Parameters    
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("tag", "reset"));
            params.add(new BasicNameValuePair("name", name));
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
	
}