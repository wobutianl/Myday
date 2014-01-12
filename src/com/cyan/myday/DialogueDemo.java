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
	 * ɾ���Ի���
	 */
	public void createDelDialogue(final Context context, final AdapterView<?> arg0,
			final View arg1, final int arg2, final long arg3){
		AlertDialog.Builder bdr = new Builder(context);
		bdr.setTitle("�Ƴ��������?");
		bdr.setPositiveButton("�Ƴ�", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ��ȡ����id
				ObjectMap<String, Object> om = (ObjectMap<String, Object>) arg0
						.getAdapter().getItem(arg2);
				int keyId = om.getIntegerValue(CommandsTable.KEY_ID);
				// ɾ������
				CommandsTable.ExeSql es = new CommandsTable().new ExeSql(
						context);
				es.delData(om);
				// ��������
				// ���»�ȡcommand������
				data.clear();
				data.addAll(es.retrieveData(null));

				((GridViewAdapter) arg0.getAdapter())
						.notifyDataSetChanged();
			}
		}).setNegativeButton("����", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		}).show();
	}

	/*
	 * view �Ի���
	 */
	public void createViewDialog(final Context context) {
		final EditText ed = new EditText(context);
		
		Dialog dialog = new AlertDialog.Builder(context).setTitle("������").setIcon(
					android.R.drawable.ic_dialog_info).setView(
				ed).setPositiveButton("ȷ��", new OnClickListener() {

				 @Override
				 public void onClick(DialogInterface dialog, int which) {
				  // TODO Auto-generated method stub
					 Toast.makeText(context, ed.getText().toString(),Toast.LENGTH_LONG).show();
				 }
				}).setNegativeButton("ȡ��", null).show();
	}
	/*
	 * ��ѡ��
	 * items = new String[] { "Item1", "Item2" }
	 */
	public void createItemDialog(final Context context, final String[] items) {
		Dialog dialog = new AlertDialog.Builder(context).setTitle("��ѡ��").setIcon(
				android.R.drawable.ic_dialog_info).setSingleChoiceItems(
				items, 0,
				new DialogInterface.OnClickListener() {
				 public void onClick(DialogInterface dialog, int which) {
					 String item = items[which];
					 
					 dialog.dismiss();
				 }
				}).setNegativeButton("ȡ��", null).show();
	}
	/*
	 * ��ѡ��
	 * items = new String[] { "Item1", "Item2" }
	 */
	public void createItemMultiDialog(final Context context, String[] items) {

		Dialog dialog = new AlertDialog.Builder(context).setTitle("��ѡ��").setMultiChoiceItems(
				 items, null, null)
				 .setPositiveButton("ȷ��", null)
				 .setNegativeButton("ȡ��", null).show();
	}
	/*
	 * �б��
	 * items = new String[] { "Item1", "Item2" }
	 */
	public void createItemListDialog(final Context context, String[] items) {

		new AlertDialog.Builder(context).setTitle("�б��").setItems(
				 items, null).setNegativeButton(
				 "ȷ��", null).show();
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
		builder.setTitle("��ȷ��Ҫ�뿪��"); 
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				//������ӵ��ȷ������߼� 
				showDialog("��ѡ����ȷ��"); 
		}});
		
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				//������ӵ��ȷ������߼� 
				showDialog("��ѡ����ȡ��"); 
		}}); 
		builder.create().show(); 
	}	
	/*
	 * three button
	 */
	public void createAlertMultiDialog(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 
		builder.setIcon(android.R.drawable.ic_dialog_info); 
		builder.setTitle("ͶƱ"); 
		builder.setMessage("����Ϊʲô������������������"); 
		builder.setPositiveButton("��Ȥζ��", new DialogInterface.OnClickListener() { 
		public void onClick(DialogInterface dialog, int whichButton) { 
		showDialog("��ѡ������Ȥζ��"); 
		}
		 });  
		builder.setNeutralButton("��˼���", new DialogInterface.OnClickListener() {  
		    public void onClick(DialogInterface dialog, int whichButton) {  
		        showDialog("��ѡ������˼���");                      
		    }  
		});  
		builder.setNegativeButton("����ǿ��", new DialogInterface.OnClickListener() {  
		    public void onClick(DialogInterface dialog, int whichButton) {  
		        showDialog("��ѡ��������ǿ��");    
		    }  
		});  
		builder.create().show();  
	}
	/*
	 * �б�ѡ���
	 */
	public void createItemsDialog(final Context context, final String[] items) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 
		builder.setTitle("�б�ѡ���"); 
		builder.setItems(items, new DialogInterface.OnClickListener() { 
		public void onClick(DialogInterface dialog, int which) { 
		//����󵯳�����ѡ���˵ڼ��� 
			showDialog("��ѡ���idΪ" + which + " , " + items[which]); 
		} }); 
		builder.create().show(); 
	}
	/**
	 * ��ѡ�����
	 * items = ���е�ѡ��
	 */
	int  mSingleChoiceID = -1;
	public Intent createChooseItemDialog(final Context context, final String[] items) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 

		builder.setIcon(android.R.drawable.ic_dialog_info); 
		builder.setTitle("����ѡ��"); 
		builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() { 
		public void onClick(DialogInterface dialog, int whichButton) { 
			mSingleChoiceID = whichButton; 
			showDialog("��ѡ���idΪ" + whichButton + " , " + items[whichButton]); 
		} 		}); 
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { 
		public void onClick(DialogInterface dialog, int whichButton) { 

			if(mSingleChoiceID > 0) { 
				showDialog("��ѡ�����" + mSingleChoiceID); 
				CurveChart chart = new CurveChart();
				intent = chart.getIntent(context, items[mSingleChoiceID]);
				
				} 
			} 		
		}); 
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { 
		public void onClick(DialogInterface dialog, int whichButton) { 
		}}); 
		builder.create().show(); 
		return intent;
	}
	
	/*
	 * ��ѡ��
	 */
	ArrayList <Integer> MultiChoiceID = new ArrayList <Integer>(); 
	public void createChooseItemsDialog(final Context context, final String[] items) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 	

		MultiChoiceID.clear(); 
		builder.setIcon(android.R.drawable.ic_dialog_info); 
		builder.setTitle("����ѡ��"); 
		builder.setMultiChoiceItems(items, 
		new boolean[]{false, false, false, false, false, false, false}, 
		new DialogInterface.OnMultiChoiceClickListener() { 
		public void onClick(DialogInterface dialog, int whichButton, 
		boolean isChecked) { 
			if(isChecked) { 
				MultiChoiceID.add(whichButton); 
				showDialog("��ѡ���idΪ" + whichButton + " , " + items[whichButton]); 
		}
		else { 
			MultiChoiceID.remove(whichButton); 
		} 		} 		}); 
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				String str = ""; 
				int size = MultiChoiceID.size(); 
				for (int i = 0 ;i < size; i++) { 
					str+= items[MultiChoiceID.get(i)] + ", "; 
		} 
			showDialog("��ѡ�����" + str); 
		} 		}); 
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
			}		}); 
		builder.create().show(); 
	}

}
