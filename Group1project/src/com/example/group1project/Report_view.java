package com.example.group1project;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import org.achartengine.chartdemo.demo.chart.*;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Report_view extends Activity implements TextToSpeech.OnInitListener {
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
	String location_text="";
	String temp,list="";
	Intent intent=null;
	String line1,s1;
	String[] splits;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_view);
		Button linegraph,display;
		 tts = new TextToSpeech(this, this);
		//String[] splits1;
		linegraph=(Button)findViewById(R.id.button1);
		display=(Button)findViewById(R.id.button2);
		linegraph.setOnClickListener(new View.OnClickListener() {
			
			Intent intent=null;
		@Override
		public void onClick(View v) {
			
			 intent = mCharts[14].execute(v.getContext());
			
		startActivity(intent);
				
			
			// TODO Auto-generated method stub
			// intent = mCharts[14].execute(v.getContext());
			//Intent in=new Intent(v.getContext(), Report.class);
		//	startActivity(intent);
			//speakOut(message_report);
		}
		
	});
display.setOnClickListener(new View.OnClickListener() {
			
			
		@Override
		public void onClick(View v) {
			
			Toast.makeText(getApplicationContext(), "Here is a report of your gesture activities", Toast.LENGTH_SHORT).show();	
			
			TextView t1;
			t1=(TextView)findViewById(R.id.textView2);
			  File f = new File(Environment.getExternalStorageDirectory()+"/Data/Group1project.txt");
				

				
				//FileInputStream fis = new FileInputStream(f);
				 try {
					 
					BufferedReader bufferr = new BufferedReader(new FileReader(f));
					if(f.exists())
					{
						try {
							while((line1 = bufferr.readLine())!=null)
							 {
								 s1 = line1;
							 
								//System.out.println(s1);
							 
							   splits = s1.split("\t");
							 //day=Report.getDefaults("day", context);
							  
							  if(splits.length==4)
							  {
							  if((!splits[3].equalsIgnoreCase(temp))&&(!splits[3].contains(":")))
							  {
								  if(location_text=="")
								  {
								  location_text=splits[3]+"\n"+splits[2]+" - ";
								 temp=splits[3]; 
								  }
								  else
								  {
									  location_text+=splits[2]+"\n"+splits[3]+"\n"+splits[2]+" - ";
										 temp=splits[3];
								  }
								  System.out.println(temp);
								  temp=splits[3];
							  }
							  }
								t1.setText("Current Location\n"+location_text);
							 //System.out.println(splits[0]+";"+splits[1]+";"+splits[2]);
							/* int temp=0;
							 for(int i=0;i<titles.length;i++)
							 {
								 if(splits[0].equalsIgnoreCase(titles[i]))
									{		
									 temp=i+1;
									// break;
									 }
							 
							 }
							 if(temp!=0)
							 {
								 temp=temp-1;
							 String[] gettime=splits[2].split(" ");
							// System.out.println(gettime[3]);
							 String[] gethour=gettime[3].split(":");
							 //System.out.println(Integer.parseInt(gethour[0]));
							  
							 count_titles[temp][Integer.parseInt(gethour[0])]++;
							 }*/
							 }
							/*for(int j=0;j<titles.length;j++)
							{
								for(int k=0;k<=23;k++)
								{
									//System.out.println(titles[j]+"count for "+k+" hour is " + count_titles[j][k]);
									if( max_count < count_titles[j][k] )
									{
										max_count=count_titles[j][k];
									}
										
								}
							}
							*/
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
		
			
			// TODO Auto-generated method stub
			// intent = mCharts[14].execute(v.getContext());
			//Intent in=new Intent(v.getContext(), Report.class);
		//	startActivity(intent);
			//speakOut(message_report);
		}
		
	});

		
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
				
					intent = mCharts[14].execute(context);
						startActivity(intent);
								
					speakOut("Line chart selected");
					
				Toast.makeText(getApplicationContext(), "Line chart selected", Toast.LENGTH_SHORT).show();
				
				}
				else if ("emergency".equalsIgnoreCase(data)){
					
					
				Toast.makeText(getApplicationContext(), "Your Recent Locations and Time spent", Toast.LENGTH_SHORT).show();

				speakOut("Your Recent Locations and Time spent");
				Toast.makeText(getApplicationContext(), "Here is a report of your gesture activities", Toast.LENGTH_SHORT).show();	
				
				TextView t1;
				t1=(TextView)findViewById(R.id.textView2);
				  File f = new File(Environment.getExternalStorageDirectory()+"/Data/Group1project.txt");
					

					
					//FileInputStream fis = new FileInputStream(f);
					 try {
						 
						BufferedReader bufferr = new BufferedReader(new FileReader(f));
						if(f.exists())
						{
							try {
								while((line1 = bufferr.readLine())!=null)
								 {
									 s1 = line1;
								 
									//System.out.println(s1);
								 
								   splits = s1.split("\t");
								 //day=Report.getDefaults("day", context);
								  
								  if(splits.length==4)
								  {
								  if((!splits[3].equalsIgnoreCase(temp))&&(!splits[3].contains(":")))
								  {
									  if(location_text=="")
									  {
									  location_text=splits[3]+"\n"+splits[2]+" - ";
									 temp=splits[3]; 
									  }
									  else
									  {
										  location_text+=splits[2]+"\n"+splits[3]+"\n"+splits[2]+" - ";
											 temp=splits[3];
									  }
									  System.out.println(temp);
									  temp=splits[3];
								  }
								  }
									t1.setText("Current Location\n"+location_text);
								 //System.out.println(splits[0]+";"+splits[1]+";"+splits[2]);
								/* int temp=0;
								 for(int i=0;i<titles.length;i++)
								 {
									 if(splits[0].equalsIgnoreCase(titles[i]))
										{		
										 temp=i+1;
										// break;
										 }
								 
								 }
								 if(temp!=0)
								 {
									 temp=temp-1;
								 String[] gettime=splits[2].split(" ");
								// System.out.println(gettime[3]);
								 String[] gethour=gettime[3].split(":");
								 //System.out.println(Integer.parseInt(gethour[0]));
								  
								 count_titles[temp][Integer.parseInt(gethour[0])]++;
								 }*/
								 }
								/*for(int j=0;j<titles.length;j++)
								{
									for(int k=0;k<=23;k++)
									{
										//System.out.println(titles[j]+"count for "+k+" hour is " + count_titles[j][k]);
										if( max_count < count_titles[j][k] )
										{
											max_count=count_titles[j][k];
										}
											
									}
								}
								*/
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
			
				
				
				
				}
				
				else if ("exit".equalsIgnoreCase(data)){
					
					speakOut("Exit");
					
					
					
				onBackPressed();
					
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report_view, menu);
		return true;
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
}
