package com.cyan.myday.adapters;

import java.util.List;

import com.cyan.myday.R;
import com.cyan.myday.sql.ObjectMap;
import com.cyan.myday.sql.tables.CommandsTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

/**
 * 
 * @author apple
 * 
 */
public class GridViewAdapter extends BaseAdapter {
	private List<ObjectMap<String, Object>> data = null;
	private Context ctx;
	private LayoutInflater inflater;

	/**
	 * 构造方法
	 * 
	 * @param ctx
	 * @param data
	 */
	public GridViewAdapter(Context ctx, List<ObjectMap<String, Object>> data) {
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
					R.layout.grid_view_component_view_item, null);
			c.btn = (Button) convertView.findViewById(R.id.grid_view_btn);
			convertView.setTag(c);
		} else {
			c = (Component) convertView.getTag();
		}
		// 给组件数据
		c.btn.setText((String) data.get(position).get(CommandsTable.KEY_NAME));
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
		public Button btn;
	}

}
