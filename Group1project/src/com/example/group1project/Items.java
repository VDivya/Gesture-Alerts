package com.example.group1project;
import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.AdapterView.OnItemSelectedListener;

import android.widget.TextView;
import android.widget.Toast;


public class Items extends Activity implements OnItemSelectedListener {





 public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

Toast.makeText(parent.getContext(),

"Report on : " + parent.getItemAtPosition(pos).toString(),

Toast.LENGTH_SHORT).show();





 }

@Override
public void onNothingSelected(AdapterView<?> parent) {
	// TODO Auto-generated method stub
	
}
}