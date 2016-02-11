package com.example.group1project;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.achartengine.chartdemo.demo.chart.AverageCubicTemperatureChart;
import org.achartengine.chartdemo.demo.chart.AverageTemperatureChart;
import org.achartengine.chartdemo.demo.chart.BudgetDoughnutChart;
import org.achartengine.chartdemo.demo.chart.BudgetPieChart;
import org.achartengine.chartdemo.demo.chart.CombinedTemperatureChart;
import org.achartengine.chartdemo.demo.chart.IDemoChart;
import org.achartengine.chartdemo.demo.chart.MultipleTemperatureChart;
import org.achartengine.chartdemo.demo.chart.ProjectStatusBubbleChart;
import org.achartengine.chartdemo.demo.chart.ProjectStatusChart;
import org.achartengine.chartdemo.demo.chart.SalesBarChart;
import org.achartengine.chartdemo.demo.chart.SalesComparisonChart;
import org.achartengine.chartdemo.demo.chart.SalesGrowthChart;
import org.achartengine.chartdemo.demo.chart.SalesStackedBarChart;
import org.achartengine.chartdemo.demo.chart.ScatterChart;
import org.achartengine.chartdemo.demo.chart.SensorValuesChart;
import org.achartengine.chartdemo.demo.chart.TemperatureChart;
import org.achartengine.chartdemo.demo.chart.TrigonometricFunctionsChart;
import org.achartengine.chartdemo.demo.chart.WeightDialChart;

import com.example.group1project.DataService;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;


public class MainActivity extends Activity  implements TextToSpeech.OnInitListener {
	private IDemoChart[] mCharts = new IDemoChart[] { new AverageTemperatureChart(),
		      new AverageCubicTemperatureChart(), new SalesStackedBarChart(), new SalesBarChart(),
		      new TrigonometricFunctionsChart(), new ScatterChart(), new SalesComparisonChart(),
		      new ProjectStatusChart(), new SalesGrowthChart(), new BudgetPieChart(),
		      new BudgetDoughnutChart(), new ProjectStatusBubbleChart(), new TemperatureChart(),
		      new WeightDialChart(), new SensorValuesChart(), new CombinedTemperatureChart(),
		      new MultipleTemperatureChart() };
	private TextToSpeech tts;
	View report;
	Context context;
	private String texttospeech="";
	String message_thirsty = "Don't Worry, Syamala Will bring some water";
	String message_hungry = "Chaitanya will bring some food for you now";
	String message_emergency="We are coming in 2 minutes";
	String message_report="Have a look over Divya's report about your activities";
private int result=0;
		@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			startService(new Intent(this,DataService.class));
			setContentView(R.layout.activity_main);
		//	setContentView(R.layout.xy_chart);
			Button b1,b2,b3,b4;
			 tts = new TextToSpeech(this, this);
			b1=(Button)findViewById(R.id.button1);
			b2=(Button)findViewById(R.id.button2);
			b3=(Button)findViewById(R.id.button3);
			b4=(Button)findViewById(R.id.button4);
			
			b1.setOnClickListener(new View.OnClickListener() {

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String phoneNumber = "2012457552";
					String message = "I am feeling very Hunger";
					speakOut(message_hungry);
					SaveData("Hungry Alert Service Called"+"\t");
					@SuppressWarnings("deprecation")
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phoneNumber, null, message, null, null);
					
				}
				
			});
			b2.setOnClickListener(new View.OnClickListener() {

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SaveData("Thirsty Alert Service Called"+"\t");
					String phoneNumber = "8168105148";
					String message = "I need some water";
					speakOut(message_thirsty);
					
					@SuppressWarnings("deprecation")
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phoneNumber, null, message, null, null);
					
				}
				
			});
			 
			b3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SaveData("`Games Service Called"+"\t");
					Intent in=new Intent(v.getContext(), Game_view.class);
					startActivityForResult(in,0);
					speakOut("Tiru Made some games for you, Have fun, You have two games available to play now, 2,0,4,8, a number puzzle game and The Got2run an Arcade game");
					//speakOut("");
					//speakOut("");
					
					
				}
				
			});
			b4.setOnClickListener(new View.OnClickListener() {
					Intent intent=null;
					
				@Override
				public void onClick(View v) {
					
					Intent in=new Intent(v.getContext(), Report_view.class);
					startActivityForResult(in,0);
					
					
					// TODO Auto-generated method stub
					// intent = mCharts[14].execute(v.getContext());
					
				//	startActivity(intent);
					speakOut(message_report);
				}
				
			});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//onresume
	
	@Override
	protected void onResume() {
	//	Gameview.resume();
		
		
		registerReceiver(receiver, new IntentFilter("myproject"));
		
		
		super.onResume();
	}
	
	//Onpause
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
				if ("hungry".equalsIgnoreCase(data)){
					SaveData("Hungry Alert Service Called"+"\t");
					String phoneNumber = "2012457552";
					String message = "I need some food";
					speakOut(message_hungry);
					@SuppressWarnings("deprecation")
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phoneNumber, null, message, null, null);
				
				Toast.makeText(getApplicationContext(), "Don't Worry, Chaitanya will bring some food", Toast.LENGTH_SHORT).show();
				
				}
				else if ("thirsty".equalsIgnoreCase(data)){
					SaveData("Thirsty Alert Service Called"+"\t");
					String phoneNumber = "8168105148";
					String message = "I need some water";
					speakOut(message_thirsty);
					@SuppressWarnings("deprecation")
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phoneNumber, null, message, null, null);
				
				Toast.makeText(getApplicationContext(), "Don't Worry, Shyamala will bring some Water", Toast.LENGTH_SHORT).show();
				
				}
				else if ("game".equalsIgnoreCase(data)){
				//	Intent in=new Intent(this, GameActivity.class);
				//	startActivityForResult(in,0);
					SaveData("`Games Service Called"+"\t");
					speakOut("Tiru Made some games for you, Have fun");
					Intent in=new Intent(context, Game_view.class);
					//Intent in=new Intent(context, Report.class);
					
					startActivity(in);
					//Intent intent=null;
				//	intent = mCharts[14].execute(context);
				//	startActivity(intent);
				//	Toast.makeText(getApplicationContext(), "Divya's report on your activities", Toast.LENGTH_SHORT).show();
					speakOut("You have two games available to play now");
					speakOut("2048 a number puzzle game and The Got2run an Arcade game");
					
					Toast.makeText(getApplicationContext(), "Tiru Made some games for you, Have fun", Toast.LENGTH_SHORT).show();
					
				}
				else if ("emergency".equalsIgnoreCase(data)){
					SaveData("Emergency Alert Service Called"+"\t");
					String phoneNumber = "4086806151";
					String message = "Emergency Alert";
					speakOut("Emergency Alert Sent");
				@SuppressWarnings("deprecation")
						SmsManager smsManager = SmsManager.getDefault();
						smsManager.sendTextMessage(phoneNumber, null, message, null, null);
					
					Toast.makeText(getApplicationContext(), "Emergency Alert Sent", Toast.LENGTH_SHORT).show();
					
				}
				else if ("exit".equalsIgnoreCase(data)){
					SaveData("`Report Service Called"+"\t");
					speakOut("Divya Made a nice report for you");
					Intent in=new Intent(context, Report_view.class);
					
					
					startActivity(in);
					//For sensor Tag values
					//intent = mCharts[14].execute(context);
				//	startActivity(intent);
					Toast.makeText(getApplicationContext(), "Report", Toast.LENGTH_SHORT).show();
				}
			
					//	startActivity(intent);
	//			Toast.makeText(getApplicationContext(), "Divya's report on your activities", Toast.LENGTH_SHORT).show();
				
				
				
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
	    Toast.makeText(getApplicationContext(), "Hi Please enter the right Words......  ", Toast.LENGTH_LONG).show();
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
Boolean gesture = false;
	StringBuffer buffer = new StringBuffer();
  private File whichGesture(ArrayList<String> datapointsList) throws Exception {
		
  	File sdCard = Environment.getExternalStorageDirectory(); 
  	File directory = new File (sdCard.getAbsolutePath() + "/Data"); 
  	if(!directory.exists()) 
  		directory.mkdirs(); 
  	 
 
  	List<String> dataPoints= datapointsList;
		for (int i = 0; i < dataPoints.size(); i++) {
	            buffer.append(datapointsList.get(i));
	            Log.i("Data Points : ","writing into a seq file");
		}
		buffer.append(System.getProperty("line.separator"));
	  String  string = buffer.toString();
		File file = new File (directory, "learn.seq"); 
  	try { 
  		if(file.exists())file.delete();
  		if(!file.exists()) file.createNewFile(); 
  		FileOutputStream out = new FileOutputStream(file,true); 
  		out.write(string.getBytes()); 
  		out.flush(); 
  		out.close(); } 
  	catch (Exception e) 
  	{ e.printStackTrace();
   }
  	 buffer.delete(0, buffer.length());
	    return file;
	}
}


