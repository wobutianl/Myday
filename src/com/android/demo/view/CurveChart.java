package com.android.demo.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Log;

import com.android.demo.view.AchartDemo;
import com.cyan.myday.sql.ObjectMap;
import com.cyan.myday.sql.tables.CalendarDateBean;
import com.cyan.myday.sql.tables.MyDayRecordTable;
import com.cyan.myday.sql.tables.WeekDayRecord;
import com.cyan.myday.sql.tables.WeekDayRecord.ExeSql;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class CurveChart {
	/*
	 * 1:��ȡ���ݿ��е�����
	 * �������chart view
	 * 		1:����dataset
	 * 		2:����render
	 * �������ɣ�ntent
	 */
	CalendarDateBean CalDateBean = new CalendarDateBean();
	
	
	AchartDemo achart = new AchartDemo();
	XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(); 
	
	String[] titles = new String[10]; 
	List<double[]> values = new ArrayList<double[]>();
	List<double[]> x = new ArrayList<double[]>();
	int[] colors = new int[10];
	PointStyle[] styles = new PointStyle[10];
	
	public Intent getIntent(Context context, String item) {
		
		List<ObjectMap<String, Object>> data = new WeekDayRecord().
				new ExeSql(context).retrieveWeekData(0, 0,0, item);
		int length = data.size();  			 //have sevral records
		TimeBean timeData = getWeekData(data);
		
		colors = new int[] { Color.YELLOW};
		styles = new PointStyle[] { PointStyle.DIAMOND};
		renderer = achart.buildLineRenderer(colors, styles);
		renderer.setZoomButtonsVisible(true);
		renderer.setShowGrid(true);

		achart.setChartSettings(renderer, item +"ʱ��", "week", "Time", 
				timeData.getDayMin(), timeData.getDayMax(), timeData.getTimeMin(), timeData.getTimeMax(),
				Color.LTGRAY, Color.LTGRAY, Color.BLACK);		
		String title = item + "��ͳ��";
		String[] titles = new String[] {title};
		List<double[]> x = new ArrayList<double[]>();
		List<double[]> values = new ArrayList<double[]>();
	    x.add(timeData.getDay());
	    values.add(timeData.getTime());
	    dataset = achart.buildLineDataset(titles, x, values);
	    
//	    for(int i=0; i<length; i++){
//	    	renderer.getSeriesRendererAt(i).setDisplayChartValues(true);
//	    }
	    renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
	    Intent intent = ChartFactory.getLineChartIntent(context, dataset, renderer,
	        item + "Week Statistic");
	    return intent;
	    
	}

	/*
	 * ���� data ���ݣ� ת��Ϊ ��Ҫ��ʵ����ʽ
	 */
	public TimeBean getWeekData(List<ObjectMap<String, Object>> data){
		int length = data.size();
        TimeBean timeData = new TimeBean();
        
		int year = 0;   
		int month = 0; 
		int dayMin = 31;    // the min day
		int dayMax = 0; 
		int timeMin = 24; 
		int timeMax = 0; 
	    double[] dayList = new double[length];      //array of day		
	    double[] timeList = new double[length];
	    int day;
	    int daytime;
	    int hour;
	    double min;
        
   
        for(int i=0; i<data.size(); i++){       	
        	long time = (Long) data.get(i).get(
    				WeekDayRecord.ITEM_WRITTEN_MILLSECECOND);
            Log.d("time", String.valueOf(time));

            Date NowDate = new Date();
    		NowDate.setTime(time);
    		if (i == 0){
    			year = CalDateBean.getYear(NowDate);
    			month = CalDateBean.getMonth(NowDate);
    		}
    		day = CalDateBean.getDay(NowDate);
    		hour = CalDateBean.getHour(NowDate);
    		min = (double)CalDateBean.getMin(NowDate)/60;
    		
    		if (dayMin > day){
    			dayMin = day;
    		}
    		if(dayMax < day){
    			dayMax = day;
    		}
    		if(timeMin > hour){
    			timeMin = hour;
    		}
    		if(timeMax < hour){
    			timeMax = hour;
    		}
    		
    		dayList[i] = (double)day;
//    		timeList[i] = (double)hour + min;
    		//����һλС��
    		BigDecimal b = new BigDecimal((double)hour + min); 
    		timeList[i] = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(); 

        }
        timeData.setYear(year);
        timeData.setMonth(month);
        timeData.setDayMin(dayMin);
        timeData.setDayMax(dayMin+7);
        timeData.setTimeMin(timeMin-1);
        timeData.setTimeMax(timeMax+1);
        timeData.setDay(dayList);
        timeData.setTime(timeList);
        
		Log.d("show data ", String.valueOf(dayMin));
		Log.d("show data ", String.valueOf(dayMax));
		Log.d("show data ", String.valueOf(timeMin));
		Log.d("show data ", String.valueOf(timeMax));
        
        
        return timeData;	
	}

	/**
	 * ��������
	 * @return
	 */
	public XYMultipleSeriesDataset getDataSet() {
		// ��������
		XYMultipleSeriesDataset barDataset = new XYMultipleSeriesDataset();
		CategorySeries barSeries = new CategorySeries("2012");
		barSeries.add(43.1);
		barSeries.add(27.2);
		barSeries.add(55.3);
		barSeries.add(43.4);
		barSeries.add(68.5);
		barSeries.add(12.6);
		barSeries.add(28.7);
		barSeries.add(33.8);
		barSeries.add(99.9);
		barSeries.add(128.0);
		barSeries.add(56.1);
		barSeries.add(77.2);
		barDataset.addSeries(barSeries.toXYSeries());
		return barDataset;
	}

	/**
	 * ������Ⱦ��
	 * @return
	 */
	public XYMultipleSeriesRenderer getRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setChartTitle("2012�깫˾����");
		renderer.setXTitle("�·�");
		renderer.setYTitle("����(��Ԫ)");
		renderer.setAxesColor(Color.WHITE);
		renderer.setLabelsColor(Color.WHITE);
		// ����X�����С���ֺ�������֣��������ǵ������Ǵ�1��ʼ����������Ϊ0.5�Ϳ�����1֮ǰ�ó�һ����
		// ����Ȥ��ͯЬ����ɾ���������д��뿴һ��Ч��
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(12.5);
		// ����Y�����С���ֺ��������
		renderer.setYAxisMin(10);
		renderer.setYAxisMax(150);
		// ������Ⱦ����ʾ���Ű�ť
		renderer.setZoomButtonsVisible(true);
		// ������Ⱦ������Ŵ���С
		renderer.setZoomEnabled(true);
		// �������
		renderer.setAntialiasing(true);
		// ���ñ�����ɫ
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.GRAY);
		// ����ÿ�����ӵ���ɫ
		SimpleSeriesRenderer sr = new SimpleSeriesRenderer();
		sr.setColor(Color.YELLOW);
		renderer.addSeriesRenderer(sr);
		// ����ÿ���������Ƿ���ʾ��ֵ
		renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
		// X��Ľ���������
		renderer.setXLabels(12);
		// Y��Ľ���������
		renderer.setYLabels(5);
		// �̶�����X����������������
		renderer.setXLabelsAlign(Align.LEFT);
		// Y����Y���������������
		renderer.setYLabelsAlign(Align.LEFT);
		// ���������϶�,�������������϶�.
		renderer.setPanEnabled(true, false);
		// ���Ӽ���
		renderer.setBarSpacing(0.1f);
		return renderer;
	}

}
