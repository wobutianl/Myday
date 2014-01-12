package com.android.demo.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class AchartDemo {

  /**
   * 构建 XYMultipleSeriesDataset. 线状图
   * 
   * @param titles 每个序列的图例
   * @param xValues X轴的坐标
   * @param yValues Y轴的坐标
   * @return XYMultipleSeriesDataset
   */
public XYMultipleSeriesDataset buildLineDataset(String[] titles, List<double[]> xValues,
      List<double[]> yValues) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    addXYSeries(dataset, titles, xValues, yValues, 0);
    return dataset;
  }

 public XYMultipleSeriesDataset buildLineDataset2(String title, Map<Integer,Double>  mapData) {
	    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();    
	    addXYSeries(dataset, title, mapData, 0);
	    return dataset;
	  }
  
  /**
   * 构建XYMultipleSeriesRenderer. 线状图
   * 
   * @param colors 每个序列的颜色
   * @param styles 每个序列点的类型(可设置三角,圆点,菱形,方块等多种)
   * @return XYMultipleSeriesRenderer
   */
  public XYMultipleSeriesRenderer buildLineRenderer(int[] colors, PointStyle[] styles) {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    setRenderer(renderer, colors, styles);
    return renderer;
  }
  
  //向DataSet中添加序列.
  public void addXYSeries(XYMultipleSeriesDataset dataset, String title, Map<Integer,Double>  mapData, int scale) {

      XYSeries series = new XYSeries(title, scale); //这里注意与TimeSeries区别.
      // 返回此映射中包含的映射关系的 Set集合
      Set<Entry<Integer,Double>> set = mapData.entrySet();
      int length = set.size();
      
      int[] xV = new int[length];
      double[] yV = new double[length];
      int i = 0;
      for(Entry<Integer,Double> entry: set)
      {
          //每个Entry就是map中一个key及其它对应的value被重新封装的对象
           xV[i] = entry.getKey();
           yV[i] = entry.getValue();
           i++;
      }

      int seriesLength = xV.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(xV[k], yV[k]);
      }
      dataset.addSeries(series);
  }
  public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues,
	     List<double[]> yValues, int scale) {
	    int length = titles.length;
	    for (int i = 0; i < length; i++) {
	      XYSeries series = new XYSeries(titles[i], scale); //这里注意与TimeSeries区别.
	      double[] xV = xValues.get(i);
	      double[] yV = yValues.get(i);
	      int seriesLength = xV.length;
	      for (int k = 0; k < seriesLength; k++) {
	        series.add(xV[k], yV[k]);
	      }
	      dataset.addSeries(series);
	    }
	  }



  public void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
    //整个图表属性设置
	//-->start
	renderer.setAxisTitleTextSize(16);//设置轴标题文字的大小
    renderer.setChartTitleTextSize(40);//设置整个图表标题文字的大小
    renderer.setLabelsTextSize(15);//设置轴刻度文字的大小
    renderer.setLegendTextSize(15);//设置图例文字大小
    renderer.setPointSize(5f);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
    renderer.setMargins(new int[] { 20, 30, 15, 20 });//设置图表的外边框(上/左/下/右)
    //-->end
    
    //以下代码设置没个序列的颜色.
    //-->start
    int length = colors.length;
    for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);// 设置颜色
			r.setPointStyle(styles[i]);
			r.setChartValuesTextSize(16);
			r.setChartValuesTextAlign(Align.RIGHT);

			/**
			 * 设置为每个点都出现数值 ；
			 * 
			 * 之前是三个点出现在个标数值的
			 * 
			 */
			r.setDisplayChartValuesDistance(25);
			renderer.addSeriesRenderer(r);

		}
		// -->end
	}

  /**
   * 设置renderer的一些属性.
   * 
   * @param renderer 要设置的renderer
   * @param title 图表标题
   * @param xTitle X轴标题
   * @param yTitle Y轴标题
   * @param xMin X轴最小值
   * @param xMax X轴最大值
   * @param yMin Y轴最小值
   * @param yMax Y轴最大值
   * @param axesColor X轴颜色
   * @param labelsColor Y轴颜色
   * @param bkColor     背景颜色 
   */
  public void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
      String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
      int labelsColor, int bkColor) {
    renderer.setChartTitle(title);
    renderer.setXTitle(xTitle);
    renderer.setYTitle(yTitle);
    renderer.setXAxisMin(xMin);
    renderer.setXAxisMax(xMax);
    renderer.setYAxisMin(yMin);
    renderer.setYAxisMax(yMax);
    renderer.setAxesColor(axesColor);
    renderer.setLabelsColor(labelsColor);
    // 设置背景颜色
 	renderer.setApplyBackgroundColor(true);
    renderer.setBackgroundColor(bkColor);
  }

  /**
   * 构建和时间有关的XYMultipleSeriesDataset,这个方法与buildDataset在参数上区别是需要List<Date[]>作参数.
   * 
   * @param titles 序列图例
   * @param xValues X轴值
   * @param yValues Y轴值
   * @return XYMultipleSeriesDataset
   */
  public XYMultipleSeriesDataset buildDateDataset(String[] titles, List<Date[]> xValues,
      List<double[]> yValues) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    int length = titles.length;
    for (int i = 0; i < length; i++) {
      TimeSeries series = new TimeSeries(titles[i]);//构建时间序列TimeSeries,
      Date[] xV = xValues.get(i);
      double[] yV = yValues.get(i);
      int seriesLength = xV.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(xV[k], yV[k]);
      }
      dataset.addSeries(series);
    }
    return dataset;
  }

  /**
   * 构建单个CategorySeries,可用于生成饼图,注意与buildMultipleCategoryDataset(构建圆环图)相区别.
   * 
   * @param titles the series titles
   * @param values the values
   * @return the category series
   */
  public CategorySeries buildCategoryDataset(String title, double[] values) {
    CategorySeries series = new CategorySeries(title);
    int k = 0;
    for (double value : values) {
      series.add("Project " + ++k, value);
    }

    return series;
  }

  /**
   * 构建MultipleCategorySeries,可用于构建圆环图(每个环是一个序列)
   * 
   * @param titles the series titles
   * @param values the values
   * @return the category series
   */
  public MultipleCategorySeries buildMultipleCategoryDataset(String title,
      List<String[]> titles, List<double[]> values) {
    MultipleCategorySeries series = new MultipleCategorySeries(title);
    int k = 0;
    for (double[] value : values) {
      series.add(2007 + k + "", titles.get(k), value);
      k++;
    }
    return series;
  }

  /**
   * 构建DefaultRenderer.
   * 
   * @param colors 每个序列的颜色
   * @return DefaultRenderer
   */
  public DefaultRenderer buildCategoryRenderer(int[] colors) {
    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    renderer.setMargins(new int[] { 20, 30, 15, 0 });
    for (int color : colors) {
      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
      r.setColor(color);
      renderer.addSeriesRenderer(r);
    }
    return renderer;
  }

  /**
   * 构建XYMultipleSeriesDataset,适用于柱状图..
   * 
   * @param titles 每中柱子序列的图列
   * @param values 柱子的高度值
   * @return XYMultipleSeriesDataset
   */
  public XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    int length = titles.length;
    for (int i = 0; i < length; i++) {
      CategorySeries series = new CategorySeries(titles[i]);
      double[] v = values.get(i);
      int seriesLength = v.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(v[k]);
      }
      dataset.addSeries(series.toXYSeries());
    }
    return dataset;
  }

  /**
   * 构建XYMultipleSeriesRenderer,适用于柱状图..
   * 
   * @param colors 每个序列的颜色
   * @return XYMultipleSeriesRenderer
   */
  public XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    renderer.setAxisTitleTextSize(16);
    renderer.setChartTitleTextSize(20);
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    int length = colors.length;
    for (int i = 0; i < length; i++) {
      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
      r.setColor(colors[i]);
      renderer.addSeriesRenderer(r);
    }
    return renderer;
  }


  /**
   * 构建XYMultipleSeriesRenderer,适用于线状图.
   * 
   * @param colors 每个序列的颜色
   * @return XYMultipleSeriesRenderer
   */
  public XYMultipleSeriesRenderer buildLineRenderer(int colors) {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    renderer.setAxisTitleTextSize(16);
    renderer.setChartTitleTextSize(20);
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    
    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
    r.setColor(colors);
    renderer.addSeriesRenderer(r);

    return renderer;
  }
}
