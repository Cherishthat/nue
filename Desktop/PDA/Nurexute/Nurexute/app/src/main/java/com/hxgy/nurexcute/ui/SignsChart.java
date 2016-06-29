/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
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
package com.hxgy.nurexcute.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Average temperature demo chart.
 */
public class SignsChart extends AbstractChart {
 
  
  private ArrayList<Date[]> xdate;
  
  private ArrayList<double[]>  values;
  
  private String title;
  
  

  public ArrayList<Date[]> getXdate() {
	return xdate;
}



public void setXdate(ArrayList<Date[]> xdate) {
	this.xdate = xdate;
}

public ArrayList<double[]> getValues() {
	return values;
}


public void setValues(ArrayList<double[]> values) {
	this.values = values;
}


public String getTitle() {
	return title;
}



public void setTitle(String title) {
	this.title = title;
}



/**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
public Intent execute(Context context) {
    String[] titles = new String[] { title };
    List<Date[]> x = xdate;
   
//    x.add(new Date[12]);
//    x.get(0)[0] = new Date(2013, 9, 1);
//    x.get(0)[1] = new Date(2013, 9, 8);
//    x.get(0)[2] = new Date(2013, 9, 15);
//    x.get(0)[3] = new Date(2013, 9, 22);
//    x.get(0)[4] = new Date(2013, 9, 29);
//    x.get(0)[5] = new Date(2013, 10, 5);
//    x.get(0)[6] = new Date(2013, 10, 12);
//    x.get(0)[7] = new Date(2013, 10, 19);
//    x.get(0)[8] = new Date(2013, 10, 26);
//    x.get(0)[9] = new Date(2013, 11, 3);
//    x.get(0)[10] = new Date(2013, 11, 10);
//    x.get(0)[11] = new Date(2013, 11, 17);
//    List<double[]> values = new ArrayList<double[]>();
//    values.add(new double[] { 35.58,35.6,35.7,36.5,35.75,38.5,38.5,39.5,39.5,39.5,39.5,36.5 });
   
    int[] colors = new int[] { Color.RED};
    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };
    
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    renderer.setPointSize(10);

    int length = renderer.getSeriesRendererCount();
    for (int i = 0; i < length; i++) {
      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
    }
    double[] sortValue=values.get(0);
    Arrays.sort(sortValue);
    
    setChartSettings(renderer, title+"线性图", "", "数值", x.get(0)[0].getTime(), x.get(0)[x.get(0).length-1].getTime(), sortValue[0], sortValue[sortValue.length-1],
        Color.LTGRAY, Color.LTGRAY);
//    renderer.setXLabels(30);
//    renderer.setXLabels(0);
 
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");  
    for (int i=0; i< x.get(0).length;i++){
//    String name=sdf.format(x.get(0)[i])
    renderer.addXTextLabel (x.get(0)[i].getTime(), sdf.format(x.get(0)[i]));

    }


    renderer.setYLabels(30);
    renderer.setShowGrid(true);
    
   
    renderer.setXLabelsAlign(Align.RIGHT);
    renderer.setYLabelsAlign(Align.RIGHT);
    renderer.setZoomButtonsVisible(true);
    //renderer.setPanLimits(new double[] { 30, 45, 0, 0 });
    renderer.setXLabelsAngle(-45);
   
    //renderer.setZoomLimits(new double[] { -10, 20, -10, 20 });
    renderer.setGridColor(Color.LTGRAY);
    //renderer.setMarginsColor(Color.RED);
    //renderer.setClickEnabled(true);
    //renderer.setSelectableBuffer(100);
   
    XYMultipleSeriesDataset dataset=buildDateDataset(titles, x, values);
//    GraphicalView mChartView = ChartFactory.getLineChartView(context, dataset, renderer);
//    mChartView.setOnClickListener(new CharViewClick(context));
    Intent intent = ChartFactory.getTimeChartIntent(context,dataset , renderer, "yy-MM-dd hh:mm");
    return intent;
  }

//class CharViewClick implements View.OnClickListener{
//    Context context;
//	public CharViewClick(Context context){
//		this.context=context;
//	}
//	@Override
//	public void onClick(View v) {
//		 UIHelper.ToastMessage(context, "asdfdsf");
//	}
//	
//}





}
