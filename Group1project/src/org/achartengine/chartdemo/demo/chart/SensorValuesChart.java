/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.achartengine.chartdemo.demo.chart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.util.MathHelper;

import com.example.group1project.Report;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Environment;
import android.util.Log;

/**
 * Temperature sensor demo chart.
 */
public class SensorValuesChart extends AbstractDemoChart {
  private static final long HOUR = 3600 * 1000;

  private static final long DAY = HOUR * 24;

  private static final int HOURS = 24;
  public String line;
  public String s;
  public String[] splits;

  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Sensor data";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The temperature, as read from an outside and an inside sensors";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Intent execute(Context context) {
	    String[] titles = new String[] { "hungry", "thirsty", "game","emergency","exit" };
	    double max_count=0;
	   double [][] count_titles=new double[10][24];
	    for(int j=0;j<titles.length;j++)
		{
			for(int k=0;k<23;k++)
			{
				count_titles[j][k]=0;
			}
		}
	    long now = Math.round(new Date().getTime() / DAY) * DAY;
	    List<Date[]> x = new ArrayList<Date[]>();
	    for (int i = 0; i < titles.length; i++) {
	      Date[] dates = new Date[HOURS];
	      for (int j = 0; j < HOURS; j++) {
	        dates[j] = new Date(now - (HOURS - j) * HOUR);
	      }
	      x.add(dates);
	    }
	    List<double[]> values = new ArrayList<double[]>();
	    File f = new File(Environment.getExternalStorageDirectory()+"/Data/Group1project.txt");
		

		
		//FileInputStream fis = new FileInputStream(f);
		 try {
			BufferedReader bufferr = new BufferedReader(new FileReader(f));
			if(f.exists())
			{
				try {
					while((line = bufferr.readLine())!=null)
					 {
						 s = line;
					 
						// System.out.println(s);
					 
					  splits = s.split("\t");
					 //day=Report.getDefaults("day", context);
					 //System.out.println(splits[0]+";"+splits[1]+";"+splits[2]);
					 int temp=0;
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
					 }
					 }
					for(int j=0;j<titles.length;j++)
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
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println(f.exists());
		 
/*
	    values.add(new double[] { 15.2, 15.5, 15.7, 15.5, 15.4, 15.4, 15.3, 15.1, 17.6, 17.3, 17.2,
	        13.9, 13.7, 13.6, 13.9, 17.3, 17.6, 17.9, 15.2, 15.6, 15.9, 22.1, 15.7, 15.5 });
	    values.add(new double[] { 1.9, 1.2, 0.9, 0.5, 0.1, -0.5, -0.6,1.4,1.5,1.8
	         -1.8, -0.3, 1.4, 3.4, 4.9, 7.0, 6.4, 3.4, 2.0, 1.5, 0.9, -0.5,
	        -1.9, -2.5, -4.3 });
	    values.add(new double[] { 5.9, 5.7, 7.9, 7.5, 7.5, -7.5, -7.6, -7.3,-7.2,-7.1
	             -5.8, -7.8, 5.4, 8.4, 4.9, 7.7, 6.4, 8.4, 7.7, 5.5, 7.9, -7.5,
	             -5.9, -7.5, -4.8 });
*/
		 for(int l=0;l<titles.length;l++)
		 {
		 values.add(count_titles[l]);
		 }
	    int[] colors = new int[] { Color.GREEN, Color.BLUE , Color.RED , Color.MAGENTA, Color.WHITE};
	    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND ,PointStyle.SQUARE, PointStyle.TRIANGLE,PointStyle.POINT };
	    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
	    int length = renderer.getSeriesRendererCount();
	    for (int i = 0; i < length; i++) {
	      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
	    }
	    setChartSettings(renderer, "MOTION SENSOR GESTURES", "Hour", "NUMBER OF GESTURES",
	        x.get(0)[0].getTime(), x.get(0)[HOURS - 2].getTime(), -10, (max_count+10), Color.LTGRAY, Color.LTGRAY);
	    renderer.setXLabels(60);
	    renderer.setYLabels(10);
	    renderer.setShowGrid(true);
	    renderer.setXLabelsAlign(Align.CENTER);
	    renderer.setYLabelsAlign(Align.RIGHT);
	    Intent intent = ChartFactory.getTimeChartIntent(context, buildDateDataset(titles, x, values),
	        renderer, "h:mm a");
	    return intent;
	  }

	}
