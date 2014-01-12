package com.cyan.myday.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cyan.myday.R;
import com.cyan.myday.sql.ObjectMap;
import com.cyan.myday.sql.tables.MyDayRecordTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
/**
 * 用于查看所有记录的listview的adapter
 * 
 * @author apple
 * 
 */
public class ListViewAdapter extends BaseAdapter {
	private List<ObjectMap<String, Object>> data = null;
	private Context ctx;
	private LayoutInflater inflater;

	/**
	 * 构造方法
	 * 
	 * @param ctx
	 * @param data
	 */
	public ListViewAdapter(Context ctx, List<ObjectMap<String, Object>> data) {
		this.ctx = ctx;
		this.data = data;
		inflater = LayoutInflater.from(ctx);

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 初始化组件
		Component c = null;
		if (convertView == null) {
			c = new Component();
			convertView = inflater.inflate(
					R.layout.list_view_component_view_item, null);
			c.tv = (TextView) convertView.findViewById(R.id.list_view_text_view);
			convertView.setTag(c);
		} else {
			c = (Component) convertView.getTag();
		}
		// 给组件数据
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long time = (Long) data.get(position).get(
				MyDayRecordTable.ITEM_WRITTEN_MILLSECECOND);
		Date date = new Date();
		date.setTime(time);
		String dateStr = sdf.format(date);
		Log.d("data", dateStr);
		c.tv.setText(dateStr + " "
				+ (String) data.get(position).get(MyDayRecordTable.ITEM_NAME));
		// 返回数据
		return convertView;
	}

	/**
	 * inner class
	 * 
	 * @author apple
	 * 
	 */
	class Component {
		public TextView tv;
	}
}
