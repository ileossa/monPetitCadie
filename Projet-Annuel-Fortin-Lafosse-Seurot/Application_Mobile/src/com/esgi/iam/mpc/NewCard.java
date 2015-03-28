package com.esgi.iam.mpc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.iam.mpc.basesqlite.Carte;
import com.esgi.iam.mpc.basesqlite.DatabaseHelper;

@SuppressLint("NewApi")
public class NewCard extends ActionBarActivity {
	
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
 
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "MPC Image";
 
    private Uri fileUri; // file url to store image/video
	
	static EditText editNomCarte;
	static TextView txtPhoto;
	static ImageView img;
	DatabaseHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_card);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
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
	    // Handle presses on the action bar items
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
			View rootView = inflater.inflate(R.layout.fragment_new_card,
					container, false);
			
			editNomCarte = (EditText) rootView.findViewById(R.id.editNomCarte);
			txtPhoto = (TextView) rootView.findViewById(R.id.txtPhoto);
			img = (ImageView) rootView.findViewById(R.id.imgPhoto);
			
			return rootView;
		}
	}
	
	public void onClickBtnValider(View v) {
		
		db = new DatabaseHelper(v.getContext());
		
		String nomCarte = editNomCarte.getText().toString();
		String cheminPhoto = txtPhoto.getText().toString();
		Carte carte = new Carte(0, nomCarte, cheminPhoto);
		if (nomCarte.equals("")==true || cheminPhoto.equals("")==true) {
			if (nomCarte.equals("")==true) {
				Toast.makeText(v.getContext(), "Le champ nom de la carte est vide", Toast.LENGTH_SHORT).show();
			}else if (cheminPhoto.equals("")==true) {
				Toast.makeText(v.getContext(), "Veuillez prendre une photo de votre carte", Toast.LENGTH_SHORT).show();
			}
		}else{
			db.createCarte(carte);
			Toast.makeText(v.getContext(), "Carte ajouté", Toast.LENGTH_SHORT).show();
			finish();
		}
		
	}
	
	public void onClickBtnPhoto (View v) {
        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
        }else{
    		captureImage();
        }

	}
	
	/**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    
    /**
     * Capturing Camera Image will lauch camera app requrest image capture
     * */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
     
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
     
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
     
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    
    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    
    /**
     * Display image from a path to ImageView
     * */
    private void previewCapturedImage() {
        try { 
            img.setVisibility(View.VISIBLE);
 
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
 
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
 
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
 
            img.setImageBitmap(bitmap);
            if (txtPhoto.getVisibility()==View.INVISIBLE) {
            	txtPhoto.setVisibility(View.VISIBLE);
			}
            txtPhoto.setText(fileUri.getPath().toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
     
    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {
     
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
     
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
     
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
     
        return mediaFile;
    }

}
