package com.android.demo.view;

import android.content.Context;
import android.content.Intent;

/**
 * 一个AChart接口
 * @Package com.manyou.androidchart
 * @FileName AChartAbstract.java
 * @Author APKBUS-manyou
 * @Date 2013-1-30
 */
public interface AChartAbstract {
	
	/**
	 * 获取一个当前类型图标的Intent实例
	 */
	public Intent getIntent(Context context);
}
