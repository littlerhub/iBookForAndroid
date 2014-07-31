package com.little.ibooks.main;


import java.util.ArrayList;
import java.util.List;

import com.little.ibooks.R;
import com.little.ibooks.file.FileExplorerTabActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * “更多”菜单栏
 * @author LittleBoy
 *
 */
public class MoreMenuActivity extends Activity {
	
	private ListView mListView;
	private List<String> mListData;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_more);
		
		initView();
		
		initData();
		
		addListener();
		
	}

	private void addListener() {
		
		mListView.setOnItemClickListener(new OnItemListClickListener());
		
	}

	private void initData() {
		
		mListData = new ArrayList<String>();
		mListData.add(getResources().getString(R.string.more_item_clear_bookshelf));
		mListData.add(getResources().getString(R.string.more_item_input_books));
		mListData.add(getResources().getString(R.string.more_item_wifi_transfer));
		mListData.add(getResources().getString(R.string.more_item_exit));
		
		mListView.setAdapter(new ListAdapter());
		
	}

	private void initView() {
		
		mListView = (ListView)findViewById(R.id.mListView);
		
	}

	class ListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mListData.size();
		}

		@Override
		public Object getItem(int position) {
			return mListData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null){
				
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.item_menu_more, null);
				TextView mItemTextView = (TextView)convertView.findViewById(R.id.mItemTextView);
				mItemTextView.setText(mListData.get(position));
				
			}
			
			return convertView;
		}
		
	}
	
	class OnItemListClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			switch(arg2){
			case 0:
				// 整理书架
				break;
			case 1:
				// 导入书籍
				MoreMenuActivity.this.finish();
				Intent intentFile = new Intent(MoreMenuActivity.this, FileExplorerTabActivity.class);
				MoreMenuActivity.this.startActivity(intentFile);
				break;
			case 2:
				// WIFI传书
				MoreMenuActivity.this.finish();
				Intent intentWifi = new Intent(MoreMenuActivity.this, FileExplorerTabActivity.class);
				intentWifi.putExtra("mode", "wifi");
				MoreMenuActivity.this.startActivity(intentWifi);
				break;
			case 3:
				// 退出程序
				MoreMenuActivity.this.finish();
				MainActivity.instance.finish();
				break;
			}			
		}
		
	}
	
	// 点击外部可使消失
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
}
