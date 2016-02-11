package com.young.games.game2048;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Locale;

import com.example.group1project.*;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity_2048 extends Activity  implements TextToSpeech.OnInitListener {

	private TextToSpeech tts;
	View report;
	Context context;
	private String texttospeech="";
	private int result=0;
	MainView view;
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String SCORE = "score";
	public static final String HIGH_SCORE = "high score temp";
	public static final String UNDO_SCORE = "undo score";
	public static final String CAN_UNDO = "can undo";
	public static final String UNDO_GRID = "undo";
	public static final String GAME_STATE = "game state";
	public static final String UNDO_GAME_STATE = "undo game state";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		view = new MainView(getBaseContext());
		tts = new TextToSpeech(this, this);
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);
		view.hasSaveState = settings.getBoolean("save_state", false);

		if (savedInstanceState != null) {
			if (savedInstanceState.getBoolean("hasState")) {
				load();
			}
		}
		setContentView(view);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			// Do nothing
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			view.game.move(2);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			view.game.move(0);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			view.game.move(3);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			view.game.move(1);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putBoolean("hasState", true);
		save();
	}

	protected void onPause() {
		super.onPause();
		 unregisterReceiver(receiver);
		save();
	}

	private void save() {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = settings.edit();
		Tile[][] field = view.game.grid.field;
		Tile[][] undoField = view.game.grid.undoField;
		editor.putInt(WIDTH, field.length);
		editor.putInt(HEIGHT, field.length);
		for (int xx = 0; xx < field.length; xx++) {
			for (int yy = 0; yy < field[0].length; yy++) {
				if (field[xx][yy] != null) {
					editor.putInt(xx + " " + yy, field[xx][yy].getValue());
				} else {
					editor.putInt(xx + " " + yy, 0);
				}

				if (undoField[xx][yy] != null) {
					editor.putInt(UNDO_GRID + xx + " " + yy,
							undoField[xx][yy].getValue());
				} else {
					editor.putInt(UNDO_GRID + xx + " " + yy, 0);
				}
			}
		}
		editor.putLong(SCORE, view.game.score);
		editor.putLong(HIGH_SCORE, view.game.highScore);
		editor.putLong(UNDO_SCORE, view.game.lastScore);
		editor.putBoolean(CAN_UNDO, view.game.canUndo);
		editor.putInt(GAME_STATE, view.game.gameState);
		editor.putInt(UNDO_GAME_STATE, view.game.lastGameState);
		editor.commit();
	}

	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, new IntentFilter("myproject"));
		load();
	}

	private void load() {
		// Stopping all animations
		view.game.aGrid.cancelAnimations();

		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);
		for (int xx = 0; xx < view.game.grid.field.length; xx++) {
			for (int yy = 0; yy < view.game.grid.field[0].length; yy++) {
				int value = settings.getInt(xx + " " + yy, -1);
				if (value > 0) {
					view.game.grid.field[xx][yy] = new Tile(xx, yy, value);
				} else if (value == 0) {
					view.game.grid.field[xx][yy] = null;
				}

				int undoValue = settings.getInt(UNDO_GRID + xx + " " + yy, -1);
				if (undoValue > 0) {
					view.game.grid.undoField[xx][yy] = new Tile(xx, yy,
							undoValue);
				} else if (value == 0) {
					view.game.grid.undoField[xx][yy] = null;
				}
			}
		}

		view.game.score = settings.getLong(SCORE, view.game.score);
		view.game.highScore = settings.getLong(HIGH_SCORE, view.game.highScore);
		view.game.lastScore = settings.getLong(UNDO_SCORE, view.game.lastScore);
		view.game.canUndo = settings.getBoolean(CAN_UNDO, view.game.canUndo);
		view.game.gameState = settings.getInt(GAME_STATE, view.game.gameState);
		view.game.lastGameState = settings.getInt(UNDO_GAME_STATE,
				view.game.lastGameState);
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
					//Moving Up
					view.game.move(0);
					speakOut("Moved Up");
				Toast.makeText(getApplicationContext(), "Moved Up", Toast.LENGTH_SHORT).show();
				}
				else if ("game".equalsIgnoreCase(data)){
					//Move Down
					view.game.move(2);
					speakOut("Moved Down");
					Toast.makeText(getApplicationContext(), "Moved Down", Toast.LENGTH_SHORT).show();
					}
				else if("thirsty".equalsIgnoreCase(data)){
					//Move Left
					view.game.move(3);
					speakOut("Moved Left");
					Toast.makeText(getApplicationContext(), "Moved Left", Toast.LENGTH_SHORT).show();
					}
			
				else if("emergency".equalsIgnoreCase(data)){
					
				//	setting(view3);
					speakOut("Moved Right");
					view.game.move(1);
					Toast.makeText(getApplicationContext(), "Moved Right", Toast.LENGTH_SHORT).show();
					}
				else if("exit".equalsIgnoreCase(data)){
					SaveData("The 2048 Game Ends ");
					speakOut("Quit 2048 Game");
					onBackPressed();
					Toast.makeText(getApplicationContext(), "Quit 2048 Game", Toast.LENGTH_SHORT).show();
					
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