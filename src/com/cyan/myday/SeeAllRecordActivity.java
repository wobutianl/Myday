package com.cyan.myday;

import java.util.List;

import com.cyan.myday.adapters.ListViewAdapter;
import com.cyan.myday.sql.ObjectMap;
import com.cyan.myday.sql.tables.CommandsTable;
import com.cyan.myday.sql.tables.MyDayRecordTable;
import com.cyan.myday.sql.tables.WeekDayRecord;
import com.cyan.myday.sql.tables.MyDayRecordTable.ExeSql;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class SeeAllRecordActivity extends Activity {
	public static String NAME = "myday.seeall";

	// 显示按钮的ListView视图
	private ListView listView;
	// 用户保存按钮的list
	List<ObjectMap<String, Object>> data = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_see_all_record);

		data = new MyDayRecordTable().new ExeSql(this).retrieveData(null);
//		data = new WeekDayRecord().new ExeSql(this).retrieveWeekData(0,0,"'起床'");
		Log.d("string", "string");

		listView = (ListView) findViewById(R.id.see_all_record_list_view);
		ListViewAdapter lva = new ListViewAdapter(this, data);
		listView.setAdapter(lva);
		
		registerRecordViewClick();
	}
	/*
	 * 
	 */
	private void registerRecordViewClick() {

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(final AdapterView<?> arg0,
					final View arg1, final int arg2, final long arg3) {
				AlertDialog.Builder bdr = new Builder(SeeAllRecordActivity.this);
				bdr.setTitle("移除这个事项?");
				bdr.setPositiveButton("移除", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 获取数据id
						ObjectMap<String, Object> om = (ObjectMap<String, Object>) arg0
								.getAdapter().getItem(arg2);
						int keyId = om.getIntegerValue(MyDayRecordTable.KEY_ID);
						// 删除数据
						MyDayRecordTable.ExeSql es = new MyDayRecordTable().new ExeSql(
								SeeAllRecordActivity.this);
						es.delData(om);
						// 重置数据
						// 重新获取command的数据
						data.clear();
						data.addAll(es.retrieveData(null));

						((ListViewAdapter) arg0.getAdapter())
								.notifyDataSetChanged();
					}
				}).setNegativeButton("保留", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
				return false;
			}
		});

	}
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.see_all_record, menu);
//		return true;
//	}

}
