package com.little.ibooks.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.little.ibooks.*;
import com.little.ibooks.data.DataConstants;
import com.little.ibooks.pull.lib.PullToRefreshBase;

public class PullToRefreshWebView extends PullToRefreshBase<WebView> {

	private final OnRefreshListener defaultOnRefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh() {
			if("file:///android_asset/web_error.html".equals(refreshableView.getUrl())){
				
				refreshableView.loadUrl(DataConstants.BOOKLIB_BASE_URL);
				
			}else{
				refreshableView.reload();
			}
		}

	};

	private final WebChromeClient defaultWebChromeClient = new WebChromeClient() {

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				onRefreshComplete();
			}
		}

	};

	public PullToRefreshWebView(Context context) {
		super(context);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
		setOnRefreshListener(defaultOnRefreshListener);
		refreshableView.setWebChromeClient(defaultWebChromeClient);
	}

	public PullToRefreshWebView(Context context, int mode) {
		super(context, mode);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
		setOnRefreshListener(defaultOnRefreshListener);
		refreshableView.setWebChromeClient(defaultWebChromeClient);
	}

	public PullToRefreshWebView(Context context, AttributeSet attrs) {
		super(context, attrs);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
		setOnRefreshListener(defaultOnRefreshListener);
		refreshableView.setWebChromeClient(defaultWebChromeClient);
	}

	@Override
	protected WebView createRefreshableView(Context context, AttributeSet attrs) {
		WebView webView = new WebView(context, attrs);

		webView.setId(R.id.webview);
		return webView;
	}

	@Override
	protected boolean isReadyForPullDown() {
		return refreshableView.getScrollY() == 0;
	}

	@Override
	protected boolean isReadyForPullUp() {
		return refreshableView.getScrollY() >= (refreshableView.getContentHeight() - refreshableView.getHeight());
	}
}
