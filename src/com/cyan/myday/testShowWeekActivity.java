package com.cyan.myday;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;


import android.view.View;
import android.view.ViewGroup.LayoutParams;  


/////////////////////////////////////////
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;


public class testShowWeekActivity extends Activity{
        GraphicalView mChartView ;
    	public static String weekShow = "myday.weekShow";
    	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.week_show_view);
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.chart2);
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		for (int i = 0; i < 2; i++) {
			CategorySeries series = new CategorySeries("Demo Series" + i);
			for (int k = 0; k < 10; k++) {
				series.add(k * (i + 4));
			}
			dataset.addSeries(series.toXYSeries());
		}
		XYMultipleSeriesRenderer render = new XYMultipleSeriesRenderer();
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		render.addSeriesRenderer(r);
		r = new SimpleSeriesRenderer();
		r.setColor(0x80000000);
		render.addSeriesRenderer(r);
		render.setChartTitle("bar chart demo");
		render.setXTitle("xÖá");
		render.setYTitle("yÖá");
		render.setXAxisMin(0.5);
		render.setXAxisMax(10.5);
		render.setYAxisMin(0);
		render.setYAxisMax(210);
//		render.setBarSpacing(1);

		// ÉèÖÃ±³¾°ÑÕÉ«
		render.setApplyBackgroundColor(true);
		render.setBackgroundColor(Color.TRANSPARENT);
		render.setMarginsColor(0x80000000);
		render.setDisplayValues(true);
		render.setShowGrid(true);
		mChartView = ChartFactory.getBarChartView(this, dataset, render,
				Type.DEFAULT);
		render.setClickEnabled(true);

		layout.addView(mChartView, new LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.MATCH_PARENT));

		//  render.setSelectableBuffer(100);
		mChartView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SeriesSelection seriesSelection = mChartView
						.getCurrentSeriesAndPoint();
				double[] xy = mChartView.toRealPoint(0);
				if (seriesSelection == null) {
					Toast.makeText(testShowWeekActivity.this,
							"No chart element was clicked", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(
							testShowWeekActivity.this,
							"Chart element in series index "
									+ seriesSelection.getSeriesIndex()
									+ " data point index "
									+ seriesSelection.getPointIndex()
									+ " was clicked"
									+ " closest point value X="
									+ seriesSelection.getXValue() + ", Y="
									+ seriesSelection.getValue()
									+ " clicked point value X=" + (float) xy[0]
									+ ", Y=" + (float) xy[1],
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}