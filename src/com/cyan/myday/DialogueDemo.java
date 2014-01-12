package com.cyan.myday;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.demo.view.CurveChart;
import com.cyan.myday.adapters.GridViewAdapter;
import com.cyan.myday.sql.ObjectMap;
import com.cyan.myday.sql.tables.CommandsTable;
import com.cyan.myday.sql.tables.WeekDayRecord;
import com.cyan.myday.sql.tables.CommandsTable.ExeSql;

public class DialogueDemo {
	Intent intent = new Intent();
	List<ObjectMap<String, Object>> data = null;
	String choosedItem = "";
	/*
	 * 删除对话框
	 */
	public void createDelDialogue(final Context context, final AdapterView<?> arg0,
			final View arg1, final int arg2, final long arg3){
		AlertDialog.Builder bdr = new Builder(context);
		bdr.setTitle("移除这个事项?");
		bdr.setPositiveButton("移除", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 获取数据id
				ObjectMap<String, Object> om = (ObjectMap<String, Object>) arg0
						.getAdapter().getItem(arg2);
				int keyId = om.getIntegerValue(CommandsTable.KEY_ID);
				// 删除数据
				CommandsTable.ExeSql es = new CommandsTable().new ExeSql(
						context);
				es.delData(om);
				// 重置数据
				// 重新获取command的数据
				data.clear();
				data.addAll(es.retrieveData(null));

				((GridViewAdapter) arg0.getAdapter())
						.notifyDataSetChanged();
			}
		}).setNegativeButton("保留", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		}).show();
	}

	/*
	 * view 对话框
	 */
	public void createViewDialog(final Context context) {
		final EditText ed = new EditText(context);
		
		Dialog dialog = new AlertDialog.Builder(context).setTitle("请输入").setIcon(
					android.R.drawable.ic_dialog_info).setView(
				ed).setPositiveButton("确定", new OnClickListener() {

				 @Override
				 public void onClick(DialogInterface dialog, int which) {
				  // TODO Auto-generated method stub
					 Toast.makeText(context, ed.getText().toString(),Toast.LENGTH_LONG).show();
				 }
				}).setNegativeButton("取消", null).show();
	}
	/*
	 * 单选框
	 * items = new String[] { "Item1", "Item2" }
	 */
	public void createItemDialog(final Context context, final String[] items) {
		Dialog dialog = new AlertDialog.Builder(context).setTitle("单选框").setIcon(
				android.R.drawable.ic_dialog_info).setSingleChoiceItems(
				items, 0,
				new DialogInterface.OnClickListener() {
				 public void onClick(DialogInterface dialog, int which) {
					 String item = items[which];
					 
					 dialog.dismiss();
				 }
				}).setNegativeButton("取消", null).show();
	}
	/*
	 * 复选框
	 * items = new String[] { "Item1", "Item2" }
	 */
	public void createItemMultiDialog(final Context context, String[] items) {

		Dialog dialog = new AlertDialog.Builder(context).setTitle("复选框").setMultiChoiceItems(
				 items, null, null)
				 .setPositiveButton("确定", null)
				 .setNegativeButton("取消", null).show();
	}
	/*
	 * 列表框
	 * items = new String[] { "Item1", "Item2" }
	 */
	public void createItemListDialog(final Context context, String[] items) {

		new AlertDialog.Builder(context).setTitle("列表框").setItems(
				 items, null).setNegativeButton(
				 "确定", null).show();
	}
	
	public void showDialog(String string) {
		Log.d("show dialogue", string);
	}
	/*
	 * basic alert dialog
	 */
	public void createAlertDialog(final Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_info); 
		builder.setTitle("你确定要离开吗？"); 
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				//这里添加点击确定后的逻辑 
				showDialog("你选择了确定"); 
		}});
		
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				//这里添加点击确定后的逻辑 
				showDialog("你选择了取消"); 
		}}); 
		builder.create().show(); 
	}	
	/*
	 * three button
	 */
	public void createAlertMultiDialog(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 
		builder.setIcon(android.R.drawable.ic_dialog_info); 
		builder.setTitle("投票"); 
		builder.setMessage("您认为什么样的内容能吸引您？"); 
		builder.setPositiveButton("有趣味的", new DialogInterface.OnClickListener() { 
		public void onClick(DialogInterface dialog, int whichButton) { 
		showDialog("你选择了有趣味的"); 
		}
		 });  
		builder.setNeutralButton("有思想的", new DialogInterface.OnClickListener() {  
		    public void onClick(DialogInterface dialog, int whichButton) {  
		        showDialog("你选择了有思想的");                      
		    }  
		});  
		builder.setNegativeButton("主题强的", new DialogInterface.OnClickListener() {  
		    public void onClick(DialogInterface dialog, int whichButton) {  
		        showDialog("你选择了主题强的");    
		    }  
		});  
		builder.create().show();  
	}
	/*
	 * 列表选择框
	 */
	public void createItemsDialog(final Context context, final String[] items) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 
		builder.setTitle("列表选择框"); 
		builder.setItems(items, new DialogInterface.OnClickListener() { 
		public void onClick(DialogInterface dialog, int which) { 
		//点击后弹出窗口选择了第几项 
			showDialog("你选择的id为" + which + " , " + items[which]); 
		} }); 
		builder.create().show(); 
	}
	/**
	 * 单选项弹出框
	 * items = 所有的选项
	 */
	int  mSingleChoiceID = -1;
	public Intent createChooseItemDialog(final Context context, final String[] items) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 

		builder.setIcon(android.R.drawable.ic_dialog_info); 
		builder.setTitle("单项选择"); 
		builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() { 
		public void onClick(DialogInterface dialog, int whichButton) { 
			mSingleChoiceID = whichButton; 
			showDialog("你选择的id为" + whichButton + " , " + items[whichButton]); 
		} 		}); 
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { 
		public void onClick(DialogInterface dialog, int whichButton) { 

			if(mSingleChoiceID > 0) { 
				showDialog("你选择的是" + mSingleChoiceID); 
				CurveChart chart = new CurveChart();
				intent = chart.getIntent(context, items[mSingleChoiceID]);
				
				} 
			} 		
		}); 
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { 
		public void onClick(DialogInterface dialog, int whichButton) { 
		}}); 
		builder.create().show(); 
		return intent;
	}
	
	/*
	 * 复选框
	 */
	ArrayList <Integer> MultiChoiceID = new ArrayList <Integer>(); 
	public void createChooseItemsDialog(final Context context, final String[] items) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 	

		MultiChoiceID.clear(); 
		builder.setIcon(android.R.drawable.ic_dialog_info); 
		builder.setTitle("多项选择"); 
		builder.setMultiChoiceItems(items, 
		new boolean[]{false, false, false, false, false, false, false}, 
		new DialogInterface.OnMultiChoiceClickListener() { 
		public void onClick(DialogInterface dialog, int whichButton, 
		boolean isChecked) { 
			if(isChecked) { 
				MultiChoiceID.add(whichButton); 
				showDialog("你选择的id为" + whichButton + " , " + items[whichButton]); 
		}
		else { 
			MultiChoiceID.remove(whichButton); 
		} 		} 		}); 
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				String str = ""; 
				int size = MultiChoiceID.size(); 
				for (int i = 0 ;i < size; i++) { 
					str+= items[MultiChoiceID.get(i)] + ", "; 
		} 
			showDialog("你选择的是" + str); 
		} 		}); 
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
			}		}); 
		builder.create().show(); 
	}

}
