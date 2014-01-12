package com.cyan.myday;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import com.android.demo.view.AchartDemo;
import com.android.demo.view.TimeBean;
import com.cyan.myday.sql.ObjectMap;
import com.cyan.myday.sql.tables.CalendarDateBean;
import com.cyan.myday.sql.tables.WeekDayRecord;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class Temperare extends Activity {
	/*
	 * 1:获取数据库中的数据 ２：设计chart view 1:生成dataset 2:生成render
	 */
	public static String static_Show = "myday.static_Show";
	public static String item = "";
	GraphicalView wChartView;
	GraphicalView mChartView;

	AchartDemo achart = new AchartDemo();
	XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

	String[] titles = new String[10];
	List<double[]> values = new ArrayList<double[]>();
	List<double[]> x = new ArrayList<double[]>();
	int[] colors = new int[10];
	PointStyle[] styles = new PointStyle[10];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temperare);

		LinearLayout layoutWeek = (LinearLayout) findViewById(R.id.week_chart);

		List<ObjectMap<String, Object>> week_data = new WeekDayRecord().new ExeSql(
				Temperare.this).retrieveWeekData(0, 0, item);

		TimeBean timeWeekData = getWeekData(week_data);

		dataset = getDataSet(timeWeekData, item);
		renderer = getRender(timeWeekData, item, "week");
		wChartView = ChartFactory.getLineChartView(this, dataset, renderer);

		layoutWeek.addView(wChartView, new LayoutParams(
				TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.MATCH_PARENT));

		// render.setSelectableBuffer(100);
		wChartView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SeriesSelection seriesSelection = wChartView
						.getCurrentSeriesAndPoint();
				double[] xy = wChartView.toRealPoint(0);
				if (seriesSelection == null) {
					Toast.makeText(Temperare.this,
							"No chart element was clicked", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(
							Temperare.this,
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
		LinearLayout layoutMonth = (LinearLayout) findViewById(R.id.month_chart);

		List<ObjectMap<String, Object>> month_data = new WeekDayRecord().new ExeSql(
				Temperare.this).retrieveMonthData(0, item);
		TimeBean timeMonthData = getMonthData(month_data);

		dataset = getDataSet(timeMonthData, item);
		renderer = getRender(timeMonthData, item, "month");
		mChartView = ChartFactory.getLineChartView(this, dataset, renderer);

		layoutMonth.addView(mChartView, new LayoutParams(
				TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.MATCH_PARENT));

		// render.setSelectableBuffer(100);
		mChartView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SeriesSelection seriesSelection = mChartView
						.getCurrentSeriesAndPoint();
				double[] xy = mChartView.toRealPoint(0);
				if (seriesSelection == null) {
					Toast.makeText(Temperare.this,
							"No chart element was clicked", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(
							Temperare.this,
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

	public XYMultipleSeriesDataset getDataSet(TimeBean timeData, String item) {

		String title = item + "时间";
		String[] legendLable = new String[] { title };
		List<double[]> x = new ArrayList<double[]>();
		List<double[]> values = new ArrayList<double[]>();
		x.add(timeData.getDay());
		values.add(timeData.getTime());
		dataset = achart.buildLineDataset(legendLable, x, values);

		return dataset;
	}

	public XYMultipleSeriesRenderer getRender(TimeBean timeData, String item,
			String xlabel) {
		colors = new int[] { Color.YELLOW };
		styles = new PointStyle[] { PointStyle.DIAMOND };
		renderer = achart.buildLineRenderer(colors, styles);
		renderer.setZoomButtonsVisible(true);
		renderer.setShowGrid(true);

		achart.setChartSettings(renderer, item + xlabel + "统计", xlabel, "Time",
				timeData.getDayMin(), timeData.getDayMax(),
				timeData.getTimeMin(), timeData.getTimeMax(), Color.LTGRAY,
				Color.LTGRAY, Color.BLACK);
		renderer.setClickEnabled(true);
		return renderer;
	}

	/*
	 * 处理 data 数据， 转变为 需要的实践格式
	 */
	public TimeBean getWeekData(List<ObjectMap<String, Object>> data) {
		int length = data.size();
		TimeBean timeData = new TimeBean();

		int year = 0;
		int month = 0;
		int dayMin = 31; // the min day
		int dayMax = 0;
		int timeMin = 24;
		int timeMax = 0;
		double[] dayList = new double[length]; // array of day
		double[] timeList = new double[length];
		int day;
		int hour;
		double min;

		for (int i = 0; i < data.size(); i++) {
			long time = (Long) data.get(i).get(
					WeekDayRecord.ITEM_WRITTEN_MILLSECECOND);

			Date NowDate = new Date();
			NowDate.setTime(time);
			if (i == 0) {
				year = CalendarDateBean.getYear(NowDate);
				month = CalendarDateBean.getMonth(NowDate);
			}
			day = CalendarDateBean.getDay(NowDate);
			hour = CalendarDateBean.getHour(NowDate);
			min = (double) CalendarDateBean.getMin(NowDate) / 60;

			if (dayMin > day) {
				dayMin = day;
			}
			if (dayMax < day) {
				dayMax = day;
			}
			if (timeMin > hour) {
				timeMin = hour;
			}
			if (timeMax < hour) {
				timeMax = hour;
			}

			dayList[i] = (double) day;

			// 保留一位小数
			BigDecimal b = new BigDecimal((double) hour + min);
			timeList[i] = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

		}
		timeData.setYear(year);
		timeData.setMonth(month);
		timeData.setDayMin(dayMin);
		timeData.setDayMax(dayMin + 7);
		timeData.setTimeMin(timeMin - 1);
		timeData.setTimeMax(timeMax + 1);
		timeData.setDay(dayList);
		timeData.setTime(timeList);

		Log.d("day min", String.valueOf(dayMax));
		return timeData;
	}
	
	/*
	 * 处理 data 数据， 转变为 需要的time格式
	 */
	public TimeBean getMonthData(List<ObjectMap<String, Object>> data) {
		int length = data.size();
		TimeBean timeData = new TimeBean();

		int year = 0;
		int month = 0;
		int dayMin = 31; // the min day
		int dayMax = 0;
		int timeMin = 24;
		int timeMax = 0;
		double[] dayList = new double[length]; // array of day
		double[] timeList = new double[length];
		int day;
		int hour;
		double min;

		for (int i = 0; i < data.size(); i++) {
			long time = (Long) data.get(i).get(
					WeekDayRecord.ITEM_WRITTEN_MILLSECECOND);

			Date NowDate = new Date();
			NowDate.setTime(time);
			if (i == 0) {
				year = CalendarDateBean.getYear(NowDate);
				month = CalendarDateBean.getMonth(NowDate);
			}
			day = CalendarDateBean.getDay(NowDate);
			hour = CalendarDateBean.getHour(NowDate);
			min = (double) CalendarDateBean.getMin(NowDate) / 60;

			if (dayMin > day) {
				dayMin = day;
			}
			if (dayMax < day) {
				dayMax = day;
			}
			if (timeMin > hour) {
				timeMin = hour;
			}
			if (timeMax < hour) {
				timeMax = hour;
			}

			dayList[i] = (double) day;

			// 保留一位小数
			BigDecimal b = new BigDecimal((double) hour + min);
			timeList[i] = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

		}
		timeData.setYear(year);
		timeData.setMonth(month);
		timeData.setDayMin(dayMin);
		timeData.setDayMax(dayMin + 30);
		timeData.setTimeMin(timeMin - 1);
		timeData.setTimeMax(timeMax + 1);
		timeData.setDay(dayList);
		timeData.setTime(timeList);

		return timeData;
	}

	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.temperare, menu);
		return true;
	}

}
