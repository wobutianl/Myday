package com.cyan.myday.sql.tables;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.cyan.myday.sql.MySQLHelper;
import com.cyan.myday.sql.MySQLHelper.SqlInterface;
import com.cyan.myday.sql.ObjectMap;

/**
 * 软件初始化的时候，根据这个来生成记录按钮
 * 
 * @author wangxu
 * 
 */
public class CommandsTable {
	// 表名
	public static final String TABLE_NAME = "CommandsTable";

	// id
	public static final String KEY_ID = "CommandsTableId";
	// 按钮的名字
	public static final String KEY_NAME = "CommandsTableName";

	// 创建语句
	private final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + " ( " + KEY_ID
			+ " integer primary key autoincrement, " + KEY_NAME
			+ " varchar not null)";

	/**
	 * 内部类，封装sql
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
						CommandsTable.TABLE_NAME + " ~~ " + om.toString());
				values.put(CommandsTable.KEY_NAME,
						om.getStringValue(CommandsTable.KEY_NAME));
				return helper.save(CommandsTable.TABLE_NAME, values);
			} catch (Exception e) {
				Log.d("addData Exception", e.toString());
				e.printStackTrace();
				return -1;
			}
		}

		@Override
		public List<ObjectMap<String, Object>> retrieveData(
				ObjectMap<String, Object> om) {
			StringBuilder bd = new StringBuilder();
			bd.append("select * from ").append(CommandsTable.TABLE_NAME);
			Log.d("SQL_RETRIEVE", bd.toString());

			Cursor cursor = helper.find(bd.toString(), null);
			List<ObjectMap<String, Object>> list = new ArrayList<ObjectMap<String, Object>>(
					cursor.getCount());

			while (cursor.moveToNext()) {
				ObjectMap<String, Object> _om = new ObjectMap<String, Object>();
				_om.put(CommandsTable.KEY_NAME, cursor.getString(cursor
						.getColumnIndex(CommandsTable.KEY_NAME)));
				_om.put(CommandsTable.KEY_ID, cursor.getInt(cursor
						.getColumnIndex(CommandsTable.KEY_ID)));
				list.add(_om);
				Log.d("CommandTable retrieveData",
						_om.getStringValue(CommandsTable.KEY_NAME));

			}
			return list;
		}

		@Override
		public boolean updateData(ObjectMap<String, Object> om) {
			return false;
		}

		@Override
		public boolean delData(ObjectMap<String, Object> om) {
			StringBuilder bd = new StringBuilder();
			bd.append(CommandsTable.KEY_ID).append(" = ?");
			return helper.delete(CommandsTable.TABLE_NAME, bd.toString(),
					new String[] { String.valueOf(om
							.getIntegerValue(CommandsTable.KEY_ID)) });
		}
	}

}
