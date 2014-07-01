package com.example.gestureapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity implements OnClickListener {

	ImageView iv1,iv2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv1=(ImageView)findViewById(R.id.imageView2);
		iv2=(ImageView)findViewById(R.id.imageView3);

		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageView2:
			Intent intent1=new Intent(MainActivity.this,GameActivity.class);
			startActivity(intent1);
			break;
case R.id.imageView3:
	Intent intent2=new Intent(MainActivity.this,Display.class);
	startActivity(intent2);
			break;

		default:
			break;
		}
		
		
	}
}
