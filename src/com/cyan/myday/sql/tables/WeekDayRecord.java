package com.cyan.myday.sql.tables;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.cyan.myday.sql.MySQLHelper.SqlInterface;
import com.cyan.myday.sql.MySQLHelper;
import com.cyan.myday.sql.ObjectMap;
import com.cyan.myday.sql.tables.CalendarDateBean;

import java.util.*;
/**
 * 这就是周数据
 * 
 * @author WangXu
 * 
 */
public class WeekDayRecord {
	// 表名
	public static final String TABLE_NAME = "MyDayRecordTable";

	// id
	public static final String KEY_ID = "MyDayRecordTableId";
	// 事项的名字
	public static final String ITEM_NAME = "MyDayRecordTableItemName";
	// 时间
	public static final String ITEM_WRITTEN_MILLSECECOND = "MyDayRecordTableWrittenTime";

	// 创建语句
	private final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + " ( " + KEY_ID
			+ " integer primary key autoincrement, " + ITEM_NAME
			+ " varchar not null, " + ITEM_WRITTEN_MILLSECECOND
			+ " bigint not null)";
	//查询语句
	private final String Search_SQL = "";	
	CalendarDateBean CalDateBean = new CalendarDateBean();

	/**
	 * 封装sql操作
	 * 
	 * @author apple
	 * 
	 */
	public class ExeSql implements SqlInterface {
		// sqlhelper
		MySQLHelper helper = null;

		/**
		 * 构造方法
		 * 
		 * @param cxt
		 */
		public ExeSql(Context cxt) {
			helper = new MySQLHelper(cxt);
			// 如果表不存在
			if (!helper.isTableExits(TABLE_NAME)) {
				// 创建表
				helper.createTable(CREATE_SQL);
			}
		}

		@Override
		public long addData(ObjectMap<String, Object> om) {
			try {
				ContentValues values = new ContentValues();
				Log.d("SQL_ADD",
						WeekDayRecord.TABLE_NAME + " ~~ " + om.toString());
				values.put(WeekDayRecord.ITEM_NAME,
						om.getStringValue(WeekDayRecord.ITEM_NAME));
				values.put(
						WeekDayRecord.ITEM_WRITTEN_MILLSECECOND,
						om.getLongValue(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND));
				return helper.save(WeekDayRecord.TABLE_NAME, values);
			} catch (Exception e) {
				Log.d("addData Exception", e.toString());
				e.printStackTrace();
				return -1;
			}
		}
		@Override
		public List<ObjectMap<String,Object>> retrieveData(ObjectMap<String, Object> om) {
			return null;
		}
		
		/**
		 * get a week data
		 * @return
		 */
//		@Override
		public List<ObjectMap<String,Object>> retrieveWeekData(int month, int week) {
			Calendar cal = Calendar.getInstance();
			long firstDate = 0;
			long lastDate  = 0;
			if (month == 0 && week == 0){
				lastDate = System.currentTimeMillis();
				cal.set(Calendar.DAY_OF_WEEK, 1);
				firstDate = cal.getTimeInMillis();
			}
			else{
				cal.set(Calendar.WEEK_OF_MONTH, 1);
				firstDate = cal.getTimeInMillis();
				cal.set(Calendar.WEEK_OF_MONTH, 7);
				lastDate = cal.getTimeInMillis();
			}
			
			StringBuilder bd = new StringBuilder();
			bd.append("select * from ").append(WeekDayRecord.TABLE_NAME)
					.append(" where ")
					.append(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND)
					.append(" between ")
					.append(firstDate)
					.append(" and ")
					.append(lastDate)
					.append(" order by ")
					.append(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND)
					.append(" desc")
					;
			Log.d("SQL_RETRIEVE", bd.toString());

			Cursor cursor = helper.find(bd.toString(), null);
			List<ObjectMap<String,Object>> list = new ArrayList<ObjectMap<String,Object>>(cursor.getCount());

			while (cursor.moveToNext()) {
				ObjectMap<String, Object> _om = new ObjectMap<String, Object>();
				_om.put(WeekDayRecord.ITEM_NAME, cursor.getString(cursor
						.getColumnIndex(WeekDayRecord.ITEM_NAME)));
				
				_om.put(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND,
						cursor.getLong(cursor
								.getColumnIndex(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND)));
				_om.put(WeekDayRecord.KEY_ID, cursor.getInt(cursor
						.getColumnIndex(WeekDayRecord.KEY_ID)));
				list.add(_om);
				Log.d("weekDay_class", _om.toString());
			}
			return list;
		}
		
		public List<ObjectMap<String,Object>> retrieveWeekData(int month, int week, String om) {
			Calendar cal = Calendar.getInstance();
			long firstDate = 0;
			long lastDate  = 0;
			if (month == 0 && week == 0){
				lastDate = System.currentTimeMillis();
				cal.set(Calendar.DAY_OF_WEEK, 1);
				firstDate = cal.getTimeInMillis();
			}
			else{
				cal.set(Calendar.WEEK_OF_MONTH, 1);
				firstDate = cal.getTimeInMillis();
				cal.set(Calendar.WEEK_OF_MONTH, 7);
				lastDate = cal.getTimeInMillis();
			}
			
			StringBuilder bd = new StringBuilder();
			bd.append("select * from ").append(WeekDayRecord.TABLE_NAME)
					.append(" where ")
					.append(WeekDayRecord.ITEM_NAME)
					.append(" = '" )
					.append(om)
					.append("' and ")
					.append(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND)
					.append(" between ")
					.append(firstDate)
					.append(" and ")
					.append(lastDate)
					.append(" order by ")
					.append(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND)
					.append(" desc")
					;
			Log.d("SQL_RETRIEVE", bd.toString());

			Cursor cursor = helper.find(bd.toString(), null);
			List<ObjectMap<String,Object>> list = new ArrayList<ObjectMap<String,Object>>(cursor.getCount());

			while (cursor.moveToNext()) {
				ObjectMap<String, Object> _om = new ObjectMap<String, Object>();
				_om.put(WeekDayRecord.ITEM_NAME, cursor.getString(cursor
						.getColumnIndex(WeekDayRecord.ITEM_NAME)));				
				_om.put(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND,
						cursor.getLong(cursor
								.getColumnIndex(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND)));
				_om.put(WeekDayRecord.KEY_ID, cursor.getInt(cursor
						.getColumnIndex(WeekDayRecord.KEY_ID)));
				list.add(_om);
				Log.d("weekDay_class", _om.toString());
			}
			return list;
		}
		
		public List<ObjectMap<String,Object>> retrieveMonthData( int month, String om) {
			Calendar cal = Calendar.getInstance();
			long firstDate = 0;
			long lastDate  = 0;
			if (month == 0 ){
				lastDate = System.currentTimeMillis();
				cal.set(Calendar.DAY_OF_MONTH, 1);
				firstDate = cal.getTimeInMillis();
			}
			else{
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				firstDate = cal.getTimeInMillis();
				cal.set(Calendar.DAY_OF_MONTH, -1);
				lastDate = cal.getTimeInMillis();
			}
			
			StringBuilder bd = new StringBuilder();
			bd.append("select * from ").append(WeekDayRecord.TABLE_NAME)
					.append(" where ")
					.append(WeekDayRecord.ITEM_NAME)
					.append(" = '" )
					.append(om)
					.append("' and ")
					.append(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND)
					.append(" between ")
					.append(firstDate)
					.append(" and ")
					.append(lastDate)
					.append(" order by ")
					.append(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND)
					.append(" desc")
					;
			Log.d("SQL_RETRIEVE", bd.toString());

			Cursor cursor = helper.find(bd.toString(), null);
			List<ObjectMap<String,Object>> list = new ArrayList<ObjectMap<String,Object>>(cursor.getCount());

			while (cursor.moveToNext()) {
				ObjectMap<String, Object> _om = new ObjectMap<String, Object>();
				_om.put(WeekDayRecord.ITEM_NAME, cursor.getString(cursor
						.getColumnIndex(WeekDayRecord.ITEM_NAME)));				
				_om.put(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND,
						cursor.getLong(cursor
								.getColumnIndex(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND)));
				_om.put(WeekDayRecord.KEY_ID, cursor.getInt(cursor
						.getColumnIndex(WeekDayRecord.KEY_ID)));
				list.add(_om);
				Log.d("weekDay_class", _om.toString());
			}
			return list;
		}
		public List<ObjectMap<String,Object>> retrieveYearData(int year, String om) {
			Calendar cal = Calendar.getInstance();
			long firstDate = 0;
			long lastDate  = 0;
			if (year ==0){
				lastDate = System.currentTimeMillis();
				cal.set(Calendar.DAY_OF_YEAR, 1);
				firstDate = cal.getTimeInMillis();
			}
			else{
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.DAY_OF_YEAR, 1);
				firstDate = cal.getTimeInMillis();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.DAY_OF_YEAR, -1);
				lastDate = cal.getTimeInMillis();
			}
			
			StringBuilder bd = new StringBuilder();
			bd.append("select * from ").append(WeekDayRecord.TABLE_NAME)
					.append(" where ")
					.append(WeekDayRecord.ITEM_NAME)
					.append(" = '" )
					.append(om)
					.append("' and ")
					.append(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND)
					.append(" between ")
					.append(firstDate)
					.append(" and ")
					.append(lastDate)
					.append(" order by ")
					.append(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND)
					.append(" desc")
					;
			Log.d("SQL_RETRIEVE", bd.toString());

			Cursor cursor = helper.find(bd.toString(), null);
			List<ObjectMap<String,Object>> list = new ArrayList<ObjectMap<String,Object>>(cursor.getCount());

			while (cursor.moveToNext()) {
				ObjectMap<String, Object> _om = new ObjectMap<String, Object>();
				_om.put(WeekDayRecord.ITEM_NAME, cursor.getString(cursor
						.getColumnIndex(WeekDayRecord.ITEM_NAME)));				
				_om.put(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND,
						cursor.getLong(cursor
								.getColumnIndex(WeekDayRecord.ITEM_WRITTEN_MILLSECECOND)));
				_om.put(WeekDayRecord.KEY_ID, cursor.getInt(cursor
						.getColumnIndex(WeekDayRecord.KEY_ID)));
				list.add(_om);
				Log.d("weekDay_class", _om.toString());
			}
			return list;
		}
		
		@Override
		public boolean updateData(ObjectMap<String, Object> om) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean delData(ObjectMap<String, Object> om) {
			StringBuilder bd = new StringBuilder();
			bd.append(WeekDayRecord.KEY_ID).append(" = ?");
			return helper.delete(WeekDayRecord.TABLE_NAME, bd.toString(),
					new String[] { String.valueOf(om
							.getIntegerValue(WeekDayRecord.KEY_ID)) });
		}
	}

}
