package com.thelikes.thegot2run;




import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Locale;

import com.example.group1project.GPSService;
import com.example.group1project.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity_got2run extends Activity implements TextToSpeech.OnInitListener {
	View view;
	private TextToSpeech tts;
	View report;
	Context context;
	private String texttospeech="";
	private int result=0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_got2run);
		tts = new TextToSpeech(this, this);
	//	startService(new Intent(this,DataService.class));
		
	}

	public void play(View v)
	{
		//startService(new Intent(this,DataService.class));
		Intent i=new Intent(this,Game_got2run.class);
		SaveData("The Got2Run Game Starts ");
		speakOut("You Chose The Got2run Game");
		startActivity(i);
	}
	
	public void highscore(View v)
	{
		Intent i=new Intent(this,Highscore_got2run.class);
		startActivity(i);
	}
	
	public void setting(View v)
	{
		Intent i=new Intent(this,Setting_got2run.class);
		startActivity(i);
	}
	
	public void exit(View v)
	{
		onBackPressed();	
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
				if ("hungry".equalsIgnoreCase(data)){
					View view1;
					SaveData("The Got2Run Game Starts ");
					Intent i=new Intent(getApplicationContext(),Game_got2run.class);
					speakOut("Play Now");
					startActivity(i);
					//play(view1);
				Toast.makeText(getApplicationContext(), "Play Now", Toast.LENGTH_SHORT).show();
				}
				else if ("game".equalsIgnoreCase(data)){
					View view2;
					Intent i=new Intent(getApplicationContext(),Highscore_got2run.class);
					startActivity(i);
					speakOut("High Score");
				//	highscore(view2);
					Toast.makeText(getApplicationContext(), "High Score", Toast.LENGTH_SHORT).show();
					}
				else if("thirsty".equalsIgnoreCase(data)){
					View view3;
					Intent i=new Intent(getApplicationContext(),Setting_got2run.class);
					startActivity(i);
					speakOut("Setting");
				//	setting(view3);
					Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
					}
			
				else if("exit".equalsIgnoreCase(data)){
					SaveData("The Got2Run Ends ");
					speakOut("Quit Got2run Game");
					onBackPressed();
					Toast.makeText(getApplicationContext(), "Quit Got2run Game", Toast.LENGTH_SHORT).show();
					
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
	
	
	private void SaveData(String string) {
	      // Log.i("string", string);
	 
	 File sdCard = Environment.getExternalStorageDirectory(); 
		File myDir = new File (sdCard.getAbsolutePath() + "/Data"); 
		Log.i("File writing","entered  "+string);
	      if(!myDir.exists())
	        myDir.mkdirs();
	        String fname = "Group1project.txt";
	        File file = new File (myDir, fname);
	        


			String address = "";
			GPSService mGPSService = new GPSService(getApplicationContext());
			mGPSService.getLocation();

			if (mGPSService.isLocationAvailable == false) {

				// Here you can ask the user to try again, using return; for that
			//	Toast.makeText(getActivity(), "Your location is not available, please try again.", Toast.LENGTH_SHORT).show();
				return;

				// Or you can continue without getting the location, remove the return; above and uncomment the line given below
				// address = "Location not available";
			} else {

				// Getting location co-ordinates
				double latitude = mGPSService.getLatitude();
				double longitude = mGPSService.getLongitude();
		//		Toast.makeText(getApplicationContext(), "Latitude:" + latitude + " | Longitude: " + longitude, Toast.LENGTH_LONG).show();

				address = mGPSService.getLocationAddress();

			
			}

		//	Toast.makeText(getApplicationContext(), "Your address is: " + address, Toast.LENGTH_SHORT).show();
			
			// make sure you close the gps after using it. Save user's battery power
			mGPSService.closeGPS();

			
		
			 Date d = new Date();
	        
	        String line="\n"+string+"\t"+d.toGMTString()+"\t"+address+"\t";
	        try {
	            if(!file.exists())
	                file.createNewFile();
	               FileOutputStream out = new FileOutputStream(file,true);
	               out.write(line.getBytes());
	               out.flush();
	               out.close();

	        } catch (Exception e) {
	               e.printStackTrace();
	        }
	    }
}


	
