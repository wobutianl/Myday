package com.cyan.myday.sql;

import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ImageView;

public class MySQLHelper extends SQLiteOpenHelper {
	private static int DB_VISION = 2;
	private SQLiteDatabase db;

	// 数据库名
	private static String DATABASE_NAME = "com_cyan_myday.db";

	public MySQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DB_VISION);
		init(context);
	}

	private void init(Context context) {
		try {
			db = getWritableDatabase();
		} catch (Exception e) {
		} finally {
			closeConnection();
		}
	}

	/**
	 * 打开数据库链接
	 * 
	 * @return
	 */
	public SQLiteDatabase openConnection() {
		try {
			if (!db.isOpen()) {
				db = getWritableDatabase();
			}
		} catch (Exception e) {
		}

		return db;
	}

	/**
	 * 关闭数据库链接
	 */
	public void closeConnection() {
		try {
			if (db != null && db.isOpen()) {
				db.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建表
	 * 
	 * @param createTableSql
	 * @return
	 */
	public boolean createTable(String createTableSql) {
		try {
			openConnection();
			db.execSQL(createTableSql);
			Log.d("MySqlHelper", "execSQL createTable " + createTableSql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

	/**
	 * 保存数据
	 * 
	 * @param tableName
	 * @param values
	 * @return
	 */
	public long save(String tableName, ContentValues values) {
		try {
			openConnection();
			long id = db.insert(tableName, null, values);
			Log.d("MySqlHelper", this + " save rowId " + id);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			closeConnection();
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public boolean delete(String table, String whereClause, String[] whereArgs) {
		try {
			openConnection();
			db.delete(table, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

	/**
	 * 修改数据
	 * 
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public boolean update(String table, ContentValues values,
			String whereClause, String[] whereArgs) {
		try {
			openConnection();
			db.update(table, values, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}
	
	public boolean alter(String alterString) {
		try {
			openConnection();
			db.execSQL(alterString);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

	/**
	 * 查询操作
	 * 
	 * @param findSql
	 * @param obj
	 * @return
	 */
	public Cursor find(String findSql, String obj[]) {
		try {
			openConnection();
			//Log.d("MySqlHelper", this + " find() " + findSql + " " + obj);
			Cursor cursor = db.rawQuery(findSql, obj);
			cursor.getCount();
			return cursor;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			closeConnection();
		}
	}

	/**
	 * 判断表是否存在
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean isTableExits(String tableName) {
		try {
			openConnection();
			String str = "select count(*) xcount from " + tableName;
			db.rawQuery(str, null);
		} catch (Exception e) {
			return false;
		} finally {
			closeConnection();
		}
		return true;
	}

	// -------Override-------
	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * 每一个table的内部类ExeSql都实现这个接口
	 * 
	 * @author apple
	 * 
	 */
	public interface SqlInterface {
		/**
		 * 添加数据
		 * 
		 * @param m
		 * @return
		 */
		public long addData(ObjectMap<String, Object> om);

		/**
		 * 查询数据
		 * 
		 * @param om
		 * @return
		 */
		public List<ObjectMap<String,Object>> retrieveData(ObjectMap<String, Object> om);

		/**
		 * 查询数据
		 * 
		 * @param om
		 * @return
		 */
		public boolean updateData(ObjectMap<String, Object> om);

		/**
		 * 删除数据
		 * 
		 * @param om
		 * @return
		 */
		public boolean delData(ObjectMap<String, Object> om);
	}
}
