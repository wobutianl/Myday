package com.cyan.myday;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cyan.myday.adapters.GridViewAdapter;
import com.cyan.myday.sql.ObjectMap;
import com.cyan.myday.sql.tables.CommandsTable;
import com.cyan.myday.sql.tables.MyDayRecordTable;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.baidu.oauth.BaiduOAuth;
import com.baidu.oauth.BaiduOAuth.BaiduOAuthResponse;
import com.baidu.oauth.BaiduOAuth.OAuthListener;

import com.baidu.pcs.BaiduPCSActionInfo;
import com.baidu.pcs.BaiduPCSClient;



public class MainActivity extends Activity {
	// ��ʾ��ť��gridview��ͼ
	private GridView gridView;
	// �û����水ť��list
	List<ObjectMap<String, Object>> data = null;
	private static int mSingleChoiceID = -1;
//	private Intent intent = new Intent();
//	private static String[] items = new String[100];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		data = new CommandsTable().new ExeSql(this).retrieveData(null);

		gridView = (GridView) findViewById(R.id.grid_view);
		GridViewAdapter gva = new GridViewAdapter(this, data);
		gridView.setAdapter(gva);

		// ��gridview��itemע���¼�
		registerGirdViewClick();

	}

	/**
	 * ��gridview��itemע���¼�
	 */
	private void registerGirdViewClick() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> arg0, final View arg1,
					final int arg2, final long arg3) {
				AlertDialog.Builder bdr = new Builder(MainActivity.this);
				bdr.setTitle("ȷ��Ҫ��¼�������?");
				bdr.setPositiveButton("��¼", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						MyDayRecordTable recd = new MyDayRecordTable();
						MyDayRecordTable.ExeSql es = recd.new ExeSql(
								MainActivity.this);

						ObjectMap<String, Object> om = new ObjectMap<String, Object>();
						om.put(MyDayRecordTable.ITEM_NAME,
								((ObjectMap<String, Object>) arg0.getAdapter()
										.getItem(arg2))
										.getStringValue(CommandsTable.KEY_NAME));
						om.put(MyDayRecordTable.ITEM_WRITTEN_MILLSECECOND,
								System.currentTimeMillis());
						es.addData(om);
					}
				}).setNegativeButton("����", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();

			}
		});

		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(final AdapterView<?> arg0,
					final View arg1, final int arg2, final long arg3) {
				createDelDialogue(arg0,
						arg1,  arg2, arg3) ;
				return false;
			}
		});

	}
	/*
	 * ɾ���Ի���
	 */
	public void createDelDialogue(final AdapterView<?> arg0,
			final View arg1, final int arg2, final long arg3){
		AlertDialog.Builder bdr = new Builder(MainActivity.this);
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
						MainActivity.this);
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
	 * ��ȡ ���а�ť��ֵ
	 */
	public CharSequence[] getItems(List<ObjectMap<String, Object>> data){
		int size = data.size();
		CharSequence[] btn_items = new String[size];

		for(int i=0; i<size; i++){				
			String j = (String) data.get(i).get(CommandsTable.KEY_NAME);

			btn_items[i]= j;		
		}
		return btn_items;
	}
	/*
	 * ��½baiduyun
	 * 
	 */
    private String mbOauth = null;
    private String cursor =null;
    private Handler mbUiThreadHandler = null;  //
    
    // the api key
    /*
     * mbApiKey should be your app_key, please instead of "your api_key"
     */
    private final static String mbApiKey = "gPL70tqRyvfpgEnerp2jl81C"; //your api_key";
    
    // the default root folder
    /*
     * mbRootPath should be your app_path, please instead of "/apps/pcstest_oauth"
     */
    private final static String mbRootPath =  "/sdcard/cyan.myday.export/";
    //
    // login ��½
    //
    private void test_login(){
		BaiduOAuth oauthClient = new BaiduOAuth();  // ��Ȩ�ͻ���    ��ҪApiKey
		oauthClient.startOAuth(this, mbApiKey, new BaiduOAuth.OAuthListener() {
			//��½ʧ��
			@Override
			public void onException(String msg) {
				Toast.makeText(getApplicationContext(), "Login failed " + msg, Toast.LENGTH_SHORT).show();
			}
			//��½�ɹ�
			@Override
			public void onComplete(BaiduOAuthResponse response) {
				if(null != response){
					mbOauth = response.getAccessToken();
					Toast.makeText(getApplicationContext(), "Token: " + mbOauth + "    User name:" + response.getUserName(), Toast.LENGTH_SHORT).show();
				}
			}
			//ȡ����½
			@Override
			public void onCancel() {
				Toast.makeText(getApplicationContext(), "Login cancelled", Toast.LENGTH_SHORT).show();
			}
		});
    }
    //
    // get quota  ���  ��ȡ access token
    // post �ύ
    //
    private void test_getQuota(){

    	if(null != mbOauth){

    		Thread workThread = new Thread(new Runnable(){
				public void run() {
		    		BaiduPCSClient api = new BaiduPCSClient();
		    		api.setAccessToken(mbOauth);
		    		final BaiduPCSActionInfo.PCSQuotaResponse info = api.quota();

		    		mbUiThreadHandler.post(new Runnable(){
		    			public void run(){
				    		if(null != info){
				    			if(0 == info.status.errorCode){
				    				Log.d("access token:", String.valueOf(info.total));
				    				Toast.makeText(getApplicationContext(), "Quota :" + info.total + "  used: " + info.used, Toast.LENGTH_SHORT).show();
				    			}
				    			else{
				    				Toast.makeText(getApplicationContext(), "Quota failed: " + info.status.errorCode + "  " + info.status.message, Toast.LENGTH_SHORT).show();
				    			}
				    		}
		    			}
		    		});
				}
			});
			 
    		workThread.start();
    	}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/**
	 * ��ѡ�����
	 * items = ���е�ѡ��
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.create_command) {
			View createView = LayoutInflater.from(this).inflate(
					R.layout.create_command_dialog, null);
			final EditText ed = (EditText) createView
					.findViewById(R.id.create_command_edit_text);
			AlertDialog.Builder bdr = new AlertDialog.Builder(MainActivity.this);
			bdr.setView(createView);
			bdr.setPositiveButton("ȷ��",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							if (!ed.getText().toString().trim().equals("")) {

								// ����
								CommandsTable ct = new CommandsTable();
								CommandsTable.ExeSql es = ct.new ExeSql(
										MainActivity.this);
								ObjectMap<String, Object> om = new ObjectMap<String, Object>();
								om.put(CommandsTable.KEY_NAME, ed.getText()
										.toString());
								es.addData(om);

								// ���»�ȡcommand������
								data.clear();
								data.addAll(es.retrieveData(null));

								// �޸�gridview
								GridViewAdapter gva = (GridViewAdapter) gridView
										.getAdapter();
								gva.notifyDataSetChanged();
							}
						}
					}).setNegativeButton("ȡ��",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			bdr.show();
		} else if (item.getItemId() == R.id.see_record) {
			Intent intt = new Intent();
			intt.setAction(SeeAllRecordActivity.NAME);
			startActivity(intt);
		} else if (item.getItemId() == R.id.oneWeek) {
			final CharSequence[] items = getItems(data); 

			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); 

			builder.setIcon(android.R.drawable.ic_dialog_info); 
			builder.setTitle("����ѡ��"); 
			builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				mSingleChoiceID = whichButton; 
			} 		}); 
			builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				if(mSingleChoiceID >= 0) { 
					Intent intt = new Intent();
					Temperare temp = new Temperare();
					Log.d("choosed", items[mSingleChoiceID].toString());
					temp.setItem(items[mSingleChoiceID].toString());
					intt.setAction(Temperare.static_Show);
					startActivity(intt);
					} 
				} 		
			}); 
			builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
			}}); 
			builder.create().show(); 
		} else if (item.getItemId() == R.id.oneMonth) {
			test_login();
		}else if (item.getItemId() == R.id.oneYear) {
			test_getQuota();
		}
			else if (item.getItemId() == R.id.export_record) {
			try {
				String path = "/sdcard/cyan.myday.export/";
				File folder = new File(path);
				if (!folder.exists()) {
					folder.mkdirs();
				}

				// ���������
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

				path = path + "record.txt";
				File file = new File(path);
				MyDayRecordTable.ExeSql es = new MyDayRecordTable().new ExeSql(
						this);
				List<ObjectMap<String, Object>> records = es.retrieveData(null);
				StringBuilder bdr = new StringBuilder();
				bdr.append("{\"records\":[");
				for (int i = 0; i < records.size(); i++) {
					ObjectMap<String, Object> om = records.get(i);
					Date d = new Date();
					d.setTime(om
							.getLongValue(MyDayRecordTable.ITEM_WRITTEN_MILLSECECOND));
					bdr.append("{\"time\":\"")
							.append(sdf.format(d))
							.append("\",\"item\":\"")
							.append(om
									.getStringValue(MyDayRecordTable.ITEM_NAME))
							.append("\"},");
				}
				bdr.setLength(bdr.length() - 1);
				bdr.append("]}");

				FileOutputStream fos = new FileOutputStream(file);
				fos.write(bdr.toString().getBytes());
				fos.flush();
				fos.close();
				Toast.makeText(MainActivity.this, "�ļ�λ��:" + path,
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return true;
	}
}
