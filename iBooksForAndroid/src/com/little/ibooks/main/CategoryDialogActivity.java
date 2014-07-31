package com.little.ibooks.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.little.ibooks.R;
import com.little.ibooks.view.dslv.DragInitModeDialog;
import com.little.ibooks.view.dslv.DragSortController;
import com.little.ibooks.view.dslv.DragSortListView;
import com.little.ibooks.view.dslv.EnablesDialog;
import com.little.ibooks.view.dslv.RemoveModeDialog;

public class CategoryDialogActivity extends FragmentActivity implements
RemoveModeDialog.RemoveOkListener,
DragInitModeDialog.DragOkListener,
EnablesDialog.EnabledOkListener
{
    private int mDragStartMode = DragSortController.ON_DRAG;
    private boolean mRemoveEnabled = true;
    private int mRemoveMode = DragSortController.FLING_REMOVE;
    private boolean mSortEnabled = true;
    private boolean mDragEnabled = true;

    private String mTag = "dslvTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_dialog);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.mDSLVFragment, getNewDslvFragment(), mTag).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mode_menu, menu);
        return true;
    }

    @Override
    public void onRemoveOkClick(int removeMode) {
        if (removeMode != mRemoveMode) {
            mRemoveMode = removeMode;
            getSupportFragmentManager().beginTransaction().replace(R.id.mDSLVFragment, getNewDslvFragment(), mTag).commit();
        }
    }

    @Override
    public void onDragOkClick(int dragStartMode) {
        mDragStartMode = dragStartMode;
        CategoryFragment f = (CategoryFragment) getSupportFragmentManager().findFragmentByTag(mTag);
        f.getController().setDragInitMode(dragStartMode);
    }

    @Override
    public void onEnabledOkClick(boolean drag, boolean sort, boolean remove) {
        mSortEnabled = sort;
        mRemoveEnabled = remove;
        mDragEnabled = drag;
        CategoryFragment f = (CategoryFragment) getSupportFragmentManager().findFragmentByTag(mTag);
        DragSortListView dslv = (DragSortListView) f.getListView();
        f.getController().setRemoveEnabled(remove);
        f.getController().setSortEnabled(sort);
        dslv.setDragEnabled(drag);
    }

    private Fragment getNewDslvFragment() {
        CategoryFragment f = new CategoryFragment();//DSLVFragment.newInstance();
        f.removeMode = mRemoveMode;
        f.removeEnabled = mRemoveEnabled;
        f.dragStartMode = mDragStartMode;
        f.sortEnabled = mSortEnabled;
        f.dragEnabled = mDragEnabled;
        return f;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        FragmentTransaction transaction;
        CategoryFragment f = (CategoryFragment) getSupportFragmentManager().findFragmentByTag(mTag);
        DragSortListView dslv = (DragSortListView) f.getListView();
        DragSortController control = f.getController();

        Log.e("TestBedDSLV", "item.getItemId() = " + item.getItemId());
        
        switch (item.getItemId() - 2131099659) {
        case 0:
            RemoveModeDialog rdialog = new RemoveModeDialog(mRemoveMode);
            rdialog.setRemoveOkListener(this);
            rdialog.show(getSupportFragmentManager(), "RemoveMode");
            return true;
        case 1:
            DragInitModeDialog ddialog = new DragInitModeDialog(mDragStartMode);
            ddialog.setDragOkListener(this);
            ddialog.show(getSupportFragmentManager(), "DragInitMode");
            return true;
        case 2:
            EnablesDialog edialog = new EnablesDialog(mDragEnabled, mSortEnabled, mRemoveEnabled);
            edialog.setEnabledOkListener(this);
            edialog.show(getSupportFragmentManager(), "Enables");
            return true;
       
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	// 【新增】按钮
	public void onBtnAddClick(View v){
		
		// 弹出输入对话框
		Toast.makeText(getApplicationContext(), "弹出输入对话框", 
				Toast.LENGTH_SHORT).show();
	}
	// 【完成】按钮
	public void onBtnFinishClick(View v){
		Toast.makeText(getApplicationContext(), "完成", 
						Toast.LENGTH_SHORT).show();
	}
    
}