package com.esgi.iam.mpc.adapter;

import java.util.List;

import com.esgi.iam.mpc.R;
import com.esgi.iam.mpc.basesqlite.DatabaseHelper;
import com.esgi.iam.mpc.basesqlite.ListeProduit;
import com.esgi.iam.mpc.basesqlite.Produit;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;




public class ListProductAdapter extends BaseAdapter{
 DatabaseHelper db;
 List <ListeProduit> listeproduits;
 Activity context;
 String produitNom;
 boolean[] itemChecked;
 
 public ListProductAdapter(Activity context, List <ListeProduit> listeproduits) {
	 super();
	 this.context = context;
	 this.listeproduits = listeproduits;
	 itemChecked = new boolean[listeproduits.size()];
 }
 
 private class ViewHolder {
  TextView produitName;
  TextView produitCode;
  CheckBox ck1;
  TextView produitQte;
 }
 
 public int getCount() {
  return listeproduits.size();
 }
 
 public Object getItem(int position) {
  return listeproduits.get(position);
 }
 
 public long getItemId(int position) {
  return 0;
 }
 
 @Override
 public View getView(final int position, View convertView, ViewGroup parent) {
  final ViewHolder holder;
   
  LayoutInflater inflater = context.getLayoutInflater();
 
  if (convertView == null) {
   convertView = inflater.inflate(R.layout.listview_product, null);
   holder = new ViewHolder();
 
   holder.produitName = (TextView) convertView
     .findViewById(R.id.nomProduit);
   holder.produitCode = (TextView) convertView
		     .findViewById(R.id.code_produit);
   holder.ck1 = (CheckBox) convertView
     .findViewById(R.id.checkBox);
   holder.produitQte = (TextView) convertView
		     .findViewById(R.id.qteProduit);
 
   convertView.setTag(holder);
 
  } else {
    
   holder = (ViewHolder) convertView.getTag();
  }
  
  db = new DatabaseHelper(context);
  Produit produit = db.getProduit(listeproduits.get(position).getId_produit());
  
  holder.produitName.setText(produit.getNom_produit());
  holder.produitCode.setText(Integer.toString(produit.getId_produit()));
  holder.produitQte.setText(listeproduits.get(position).getQte_produit());
  holder.ck1.setChecked(false);
 
  if (itemChecked[position])
   holder.ck1.setChecked(true);
  else
   holder.ck1.setChecked(false);
 
  holder.ck1.setOnClickListener(new OnClickListener() {
   @Override
   public void onClick(View v) {
    // TODO Auto-generated method stub
    if (holder.ck1.isChecked()){
    	itemChecked[position] = true;
    	holder.produitName.setBackgroundResource(R.drawable.bg_strikethrough);
    }
    else{
    	itemChecked[position] = false;
    	holder.produitName.setBackgroundResource(0);
    }
     
   }
  });
 
  return convertView;
 
 }
 
 
 
 
 
 
 
}