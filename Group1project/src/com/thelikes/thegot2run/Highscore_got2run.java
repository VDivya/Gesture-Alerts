package com.thelikes.thegot2run;
import java.util.Locale;

import com.example.group1project.R;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Highscore_got2run extends Activity implements TextToSpeech.OnInitListener {
	View view;
	private TextToSpeech tts;
	View report;
	Context context;
	private String texttospeech="";
	private int result=0;
	TextView t1;
	int score,hscore;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highscore_got2run);
		SharedPreferences pref = getApplicationContext().getSharedPreferences("higher", MODE_PRIVATE);
	    Editor editor = pref.edit();
	    
	    score=pref.getInt("score", 0);
	    hscore=pref.getInt("hscore", 0);
	    
	    if(score>hscore)
	    {
	    	editor.putInt("hscore", score);
    	    editor.commit(); 
	    }
	    hscore=pref.getInt("hscore", 0);
	    
	    t1=(TextView) findViewById(R.id.textView1);
	    t1.setText("Highscore :"+hscore);
	}

	@Override
	protected void onResume() {
	//	Gameview.resume();
		
		
		registerReceiver(receiver, new IntentFilter("myproject"));
		
		
		super.onResume();
	}
	@Override
	protected void onPause() {
		//view.pause();
		
		 unregisterReceiver(receiver);
		super.onPause();
	}
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle!=null) {
				String data = bundle.getString("data");
				Log.i("data in main class", data);
				//if ("stomp".equalsIgnoreCase(data)||"stomp2".equalsIgnoreCase(data)||"stomp3".equalsIgnoreCase(data)) {
				if("exit".equalsIgnoreCase(data)){
					
				//	speakOut("Quit to Got2run Menu");
					
					Toast.makeText(getApplicationContext(), "Quit to Got2run Menu", Toast.LENGTH_SHORT).show();
				onBackPressed();	
				}
			
				//Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_SHORT).show();
			}else{
				Log.i("data in main class", "bundle null");
				//Toast.makeText(getApplicationContext(), "not", Toast.LENGTH_SHORT).show();
			}
			//handleResult(bundle);
		}

		
	};
	@Override
    public void onBackPressed() {
        super.onBackPressed();   
        //    finish();

    }
	@Override
	public void onDestroy() {
	    if (tts != null) {
	        tts.stop();
	        tts.shutdown();
	    }
	    super.onDestroy();
	 }
	//called when text to speech start
		@Override
		public void onInit(int status) {
			 // TODO Auto-generated method stub
		    if (status == TextToSpeech.SUCCESS) {
		        //set Language
		        result = tts.setLanguage(Locale.US);
		         tts.setPitch(19); // set pitch level
		         tts.setSpeechRate(1); // set speech speed rate
		        if (result == TextToSpeech.LANG_MISSING_DATA
		                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
		        } else {
		           
		            speakOut(texttospeech);
		        }
		    } else {
		        Log.e("TTS", "Initilization Failed");
		    }
		}
		private void speakOut(String text) {
			
		    //String text = txtText.getText().toString();
		    if(result!=tts.setLanguage(Locale.US))
		    {
		   // Toast.makeText(getApplicationContext(), "Hi Please enter the right Words......  ", Toast.LENGTH_LONG).show();
		    }else
		    {
		    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		    }
		   }
		
		
}
