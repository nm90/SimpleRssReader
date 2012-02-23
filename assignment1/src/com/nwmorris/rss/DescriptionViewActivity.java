package com.nwmorris.rss;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

/**
 * 
 * Activity creates a WebView to display article descriptions from the rss feed
 * based on information passed through extra in the Intent
 *
 * @author neil
 *
 */
public class DescriptionViewActivity extends RSSReaderActivity {

	private static final String TAG = "ItemDescriptionView";
	String title;
	String description;
	String link;
	String pubdate;
	
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		WebView detailView = new WebView(this);
		
		try {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				title = extras.getString("title");
				description = extras.getString("description");
				link = extras.getString("link");
				pubdate = extras.getString("pubdate");
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		
		detailView.loadDataWithBaseURL(null, description, "text/html", null, null);
		
		setContentView(detailView);
	}
}
