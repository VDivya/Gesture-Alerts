package com.example.gestureapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	 ImageView selectedImage;  
     private Integer[] mImageIds = {
                R.drawable.image1,
                R.drawable.image2,
                R.drawable.image3,
                R.drawable.image4,
                
        };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		 Gallery gallery = (Gallery) findViewById(R.id.gallery1);
	        selectedImage=(ImageView)findViewById(R.id.imageView1);
	        gallery.setSpacing(1);
	        gallery.setAdapter(new GalleryImageAdapter(this));
	        gallery.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	                Toast.makeText(MainActivity.this, "Your selected position = " + position, Toast.LENGTH_SHORT).show();
	                // show the selected Image
	                selectedImage.setImageResource(mImageIds[position]);
		
	}

		});
	}
}
