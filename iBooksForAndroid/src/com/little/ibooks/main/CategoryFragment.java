package com.little.ibooks.main;

import java.util.Arrays;
import java.util.ArrayList;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.little.ibooks.view.dslv.DragSortController;
import com.little.ibooks.view.dslv.DragSortListView;
import com.little.ibooks.R;

public class CategoryFragment extends ListFragment {

	private MyListAdapter mListAdapter;
	private boolean isDragViewVisible = false;
	
	String[] mArray;
    ArrayList<String> mList;
	
    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    if (from != to) {
                    	String item = mList.get(from);
                    	mList.remove(from);
                    	mList.add(to, item);
                    	mListAdapter.notifyDataSetChanged();
                    	
                    }
                }
            };

    private DragSortListView.RemoveListener onRemove = 
            new DragSortListView.RemoveListener() {
                @Override
                public void remove(int which) {
                	mList.remove(which);
                	mListAdapter.notifyDataSetChanged();
                }
            };

    protected int getLayout() {
        // this DSLV xml declaration does not call for the use
        // of the default DragSortController; therefore,
        // DSLVFragment has a buildController() method.
        return R.layout.category_dslv_fragment;
    }
    
    /**
     * Return list item layout resource passed to the ArrayAdapter.
     */
    protected int getItemLayout() {
        /*if (removeMode == DragSortController.FLING_LEFT_REMOVE || removeMode == DragSortController.SLIDE_LEFT_REMOVE) {
            return R.layout.list_item_handle_right;
        } else */
    	if (removeMode == DragSortController.CLICK_REMOVE) {
            return R.layout.item_dslv_click_remove;
        } else {
            return R.layout.item_dslv_handle_left;
        }
    }

    private DragSortListView mDslv;
    private DragSortController mController;

    public int dragStartMode = DragSortController.ON_DOWN;
    public boolean removeEnabled = false;
    public int removeMode = DragSortController.FLING_REMOVE;
    public boolean sortEnabled = true;
    public boolean dragEnabled = true;

    public static CategoryFragment newInstance() {
        CategoryFragment f = new CategoryFragment();
        return f;
    }

    public DragSortController getController() {
        return mController;
    }

    /**
     * Called from DSLVFragment.onActivityCreated(). Override to
     * set a different adapter.
     */
    public void setListAdapter() {
        mListAdapter = new MyListAdapter();
        setListAdapter(mListAdapter);
    	
    }

    /**
     * Called in onCreateView. Override this to provide a custom
     * DragSortController.
     */
    public DragSortController buildController(DragSortListView dslv) {
        // defaults are
        //   dragStartMode = onDown
        //   removeMode = flingRight
        DragSortController controller = new DragSortController(dslv);
        controller.setDragHandleId(R.id.drag_handle);
        controller.setClickRemoveId(R.id.click_remove);
        controller.setRemoveEnabled(removeEnabled);
        controller.setSortEnabled(sortEnabled);
        controller.setDragInitMode(dragStartMode);
        controller.setRemoveMode(removeMode);
        return controller;
    }


    /** Called when the activity is first created. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mDslv = (DragSortListView) inflater.inflate(getLayout(), container, false);

        mController = buildController(mDslv);
        mDslv.setFloatViewManager(mController);
        mDslv.setOnTouchListener(mController);
        mDslv.setDragEnabled(dragEnabled);

        mArray = getResources().getStringArray(R.array.test_names);
        mList = new ArrayList<String>(Arrays.asList(mArray));
        
        return mDslv;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDslv = (DragSortListView) getListView(); 

        mDslv.setDropListener(onDrop);
        mDslv.setRemoveListener(onRemove);

        setListAdapter();
        
        mDslv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                String message = String.format("Clicked item %d", arg2);
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                
            }
        });
        mDslv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                String message = String.format("Long-clicked item %d", arg2);
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                // 长按进入编辑状态
                isDragViewVisible = true;
                mListAdapter.notifyDataSetChanged();
                
                return true;
            }
        });
        
    }
    
    
    class MyListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView dragView;
			TextView textView;
			
			if(convertView == null){
				
				convertView = LayoutInflater.from(getActivity())
						.inflate(getItemLayout(), null);
				
			}
			
			dragView = (ImageView)convertView.findViewById(R.id.drag_handle);
			textView = (TextView)convertView.findViewById(R.id.text);
			textView.setText(mList.get(position));
			if(isDragViewVisible){
				dragView.setVisibility(View.VISIBLE);
			}else{
				dragView.setVisibility(View.GONE);
			}
			
			return convertView;
		}
    	
    }

}
