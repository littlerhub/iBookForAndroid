package com.little.ibooks.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;

import com.little.ibooks.R;
import com.little.ibooks.pull.lib.PullToRefreshBase2.OnRefreshListener;
import com.little.ibooks.view.PullToRefreshListView;

public class BookShelfActivity extends Activity {

	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;

	private List<String> mItemList;
	private PullToRefreshListView mPullRefreshListView;
	private BookShelfAdapter mAdapter;
	private ListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_shelf);

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Do work to refresh the list here.
				new GetDataTask().execute();
			}
		});
		mListView = mPullRefreshListView.getRefreshableView();
		mItemList = new ArrayList<String>();
		mItemList.addAll(Arrays.asList(mStrings));
		mAdapter = new BookShelfAdapter();
		mListView.setAdapter(mAdapter);
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mItemList.add("Added after refresh...");
			mAdapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_MANUAL_REFRESH, 0, "Manual Refresh");
		menu.add(0, MENU_DISABLE_SCROLL, 1,
				mPullRefreshListView.isDisableScrollingWhileRefreshing() ? "Enable Scrolling while Refreshing"
						: "Disable Scrolling while Refreshing");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem disableItem = menu.findItem(MENU_DISABLE_SCROLL);
		disableItem
				.setTitle(mPullRefreshListView.isDisableScrollingWhileRefreshing() ? "Enable Scrolling while Refreshing"
						: "Disable Scrolling while Refreshing");

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

			case MENU_MANUAL_REFRESH:
				new GetDataTask().execute();
				mPullRefreshListView.setRefreshing(false);
				break;
			case MENU_DISABLE_SCROLL:
				mPullRefreshListView.setDisableScrollingWhileRefreshing(!mPullRefreshListView
						.isDisableScrollingWhileRefreshing());
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };
	
	class BookShelfAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mItemList.size();
		}

		@Override
		public Object getItem(int position) {
			return mItemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null){
				
				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_bookshelf, null);
				
			}
			
			return convertView;
		}
		
	}
	
	// 弹出书籍分类的Dialog
	public void onTitleCategoryClick(View v){
		
		Intent intent = new Intent(this, CategoryDialogActivity.class);
		startActivity(intent);
		
	}
	// 从底部弹出“更多操作”的Dialog
	public void onButtonMoreClick(View v){
		
		Intent intent = new Intent(this, MoreMenuActivity.class);
		startActivity(intent);
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			
			this.finish();
			
		}
		
		return false;
	}
	
}
