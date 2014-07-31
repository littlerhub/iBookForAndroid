package com.little.ibooks.main;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.little.ibooks.*;
import com.little.ibooks.anim.AnimationFactory;
import com.little.ibooks.anim.AnimationFactory.FlipDirection;

public class MainActivity extends ActivityGroup {

	private ViewAnimator mViewContainer;
	private View mBookLibView;
	private View mBookShelfView;
	private ImageView mBtnToBookShelf;
	private ImageView mBtnToBookLib;

	public static MainActivity instance;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        instance = this;
        
        initView();
        
        addListener();
        
    }
    
    private void initView(){
    	
    	mViewContainer = (ViewAnimator)findViewById(R.id.viewFlipper);
    	
    	mBookShelfView = getLocalActivityManager().startActivity("BookShelfActivity", 
    			new Intent(this, BookShelfActivity.class)).getDecorView();
    	mBookLibView = getLocalActivityManager().startActivity("BookLibActivity", 
    			new Intent(this, BookLibActivity.class)).getDecorView();
    	
    	mViewContainer.addView(mBookShelfView);
    	mViewContainer.addView(mBookLibView);
    	
    	mBtnToBookShelf = (ImageView)mBookLibView.findViewById(R.id.mBtnToBookShelf);
    	mBtnToBookLib = (ImageView)mBookShelfView.findViewById(R.id.mBtnToBookLib);
    	
    }
    
    private void addListener(){
    	
    	mBtnToBookShelf.setOnClickListener(new OnBtnFlipClickListener());
    	mBtnToBookLib.setOnClickListener(new OnBtnFlipClickListener());
    	
    }
    
    private class OnBtnFlipClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			switch(v.getId()){
				case R.id.mBtnToBookLib:
					AnimationFactory.flipTransition(mViewContainer, FlipDirection.LEFT_RIGHT);
				break;
				case R.id.mBtnToBookShelf:
					AnimationFactory.flipTransition(mViewContainer, FlipDirection.RIGHT_LEFT);
				break;
			}
		}
    	
    }
    
}