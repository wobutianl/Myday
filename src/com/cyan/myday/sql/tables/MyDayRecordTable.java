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

import java.util.*;
/**
 * 这就是记录噢
 * 
 * @author WangXu
 * 
 */
public class MyDayRecordTable {
	// 表名
	public static final String TABLE_NAME = "MyDayRecordTable";

	// id
	public static final String KEY_ID = "MyDayRecordTableId";
	// 事项的名字
	public static final String ITEM_NAME = "MyDayRecordTableItemName";
	// 时间
	public static final String ITEM_WRITTEN_MILLSECECOND = "MyDayRecordTableWrittenTime";
	//GPS
	public static final String GPS_LONGITUDE = "MyGPSLongitude";
	public static final String GPS_LATITUDE = "MyGPSLatitude";

	// 创建语句
	private final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + " ( " + KEY_ID
			+ " integer primary key autoincrement, " + ITEM_NAME
			+ " varchar not null, " + ITEM_WRITTEN_MILLSECECOND
			+ " bigint not null) ";
	
//	private final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS "
//			+ TABLE_NAME + " ( " + KEY_ID
//			+ " integer primary key autoincrement, " + ITEM_NAME
//			+ " varchar not null, " + ITEM_WRITTEN_MILLSECECOND
//			+ " bigint not null, " + GPS_LATITUDE + " double not null, " 
//			+ GPS_LONGITUDE + "double not null )";

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
						MyDayRecordTable.TABLE_NAME + " ~~ " + om.toString());
				values.put(MyDayRecordTable.ITEM_NAME,
						om.getStringValue(MyDayRecordTable.ITEM_NAME));
				values.put(
						MyDayRecordTable.ITEM_WRITTEN_MILLSECECOND,
						om.getLongValue(MyDayRecordTable.ITEM_WRITTEN_MILLSECECOND));
				return helper.save(MyDayRecordTable.TABLE_NAME, values);
			} catch (Exception e) {
				Log.d("addData Exception", e.toString());
				e.printStackTrace();
				return -1;
			}
		}

		@Override
		public List<ObjectMap<String,Object>> retrieveData(ObjectMap<String, Object> om) {
			StringBuilder bd = new StringBuilder();
			bd.append("select * from ").append(MyDayRecordTable.TABLE_NAME)
					.append(" order by ")
					.append(MyDayRecordTable.ITEM_WRITTEN_MILLSECECOND)
					.append(" desc");
			Log.d("SQL_RETRIEVE", bd.toString());

			Cursor cursor = helper.find(bd.toString(), null);
			List<ObjectMap<String,Object>> list = new ArrayList<ObjectMap<String,Object>>(cursor.getCount());

			while (cursor.moveToNext()) {
				ObjectMap<String, Object> _om = new ObjectMap<String, Object>();
				_om.put(MyDayRecordTable.ITEM_NAME, cursor.getString(cursor
						.getColumnIndex(MyDayRecordTable.ITEM_NAME)));
				_om.put(MyDayRecordTable.ITEM_WRITTEN_MILLSECECOND,
						cursor.getLong(cursor
								.getColumnIndex(MyDayRecordTable.ITEM_WRITTEN_MILLSECECOND)));
				_om.put(MyDayRecordTable.KEY_ID, cursor.getInt(cursor
						.getColumnIndex(MyDayRecordTable.KEY_ID)));
				list.add(_om);
			}
			return list;
		}
		
		
//		@Override
		public List<ObjectMap<String,Object>> retrieveWeekData(String om) {
			StringBuilder bd = new StringBuilder();
			bd.append("select * from ").append(MyDayRecordTable.TABLE_NAME)
					.append(" where ")
					.append(MyDayRecordTable.ITEM_NAME)
					.append(" = " )
					.append(om)
//					.append(" order by ")
//					.append(MyDayRecordTable.ITEM_WRITTEN_MILLSECECOND)
//					.append(" desc")
					;
			Log.d("SQL_RETRIEVE", bd.toString());

			Cursor cursor = helper.find(bd.toString(), null);
			List<ObjectMap<String,Object>> list = new ArrayList<ObjectMap<String,Object>>(cursor.getCount());

			while (cursor.moveToNext()) {
				ObjectMap<String, Object> _om = new ObjectMap<String, Object>();
				
				
				_om.put(MyDayRecordTable.ITEM_NAME, cursor.getString(cursor
						.getColumnIndex(MyDayRecordTable.ITEM_NAME)));
//						.getColumnIndex(om)));
				_om.put(MyDayRecordTable.ITEM_WRITTEN_MILLSECECOND,
						cursor.getLong(cursor
								.getColumnIndex(MyDayRecordTable.ITEM_WRITTEN_MILLSECECOND)));
				_om.put(MyDayRecordTable.KEY_ID, cursor.getInt(cursor
						.getColumnIndex(MyDayRecordTable.KEY_ID)));
				list.add(_om);
			}
			return list;
		}
		
		public int getDaysBetween (Calendar d1, Calendar d2){
			 if (d1.after(d2)){  // swap dates so that d1 is start and d2 is end
			           java.util.Calendar swap = d1;
			           d1 = d2;
			           d2 = swap;
			      }
			       int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
			       int y2 = d2.get(Calendar.YEAR);
			     if (d1.get(Calendar.YEAR) != y2){
			           d1 = (Calendar) d1.clone();
			           do{
			             days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
			               d1.add(Calendar.YEAR, 1);
			          } while (d1.get(Calendar.YEAR) != y2);
			 }
			 return days;
		}
		@Override
		public boolean updateData(ObjectMap<String, Object> om) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean delData(ObjectMap<String, Object> om) {
			StringBuilder bd = new StringBuilder();
			bd.append(MyDayRecordTable.KEY_ID).append(" = ?");
			return helper.delete(MyDayRecordTable.TABLE_NAME, bd.toString(),
					new String[] { String.valueOf(om
							.getIntegerValue(MyDayRecordTable.KEY_ID)) });
		}
		
		public boolean AlterAddCol(String col_name, String data_type) {
			StringBuilder bd = new StringBuilder();
			bd.append("ALTER TABLE ")
				.append(MyDayRecordTable.TABLE_NAME)
				.append(" ADD ").append(col_name).append("  ")
				.append(data_type).append(" not null");
			return true;
		}
	}

}
