package com.nwmorris.rss;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 *
 * Activity to allow the user to save RSS feeds for later sessions
 *
 * Also currently the initial launched activity, so it also handles
 * graceful exit when launched with intent flagged to CLEAR_TO_TOP
 *
 * @author neil
 *
 */
public class AddFeedFromUserActivity extends RSSReaderActivity {

	private static final String TAG = "AddFeed";
	protected static final String FILENAME = "rss_feeds";

	int rssCount;
	ArrayList<String> rssList = new ArrayList<String>();
	String newUrl;
	RSSParser parser;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// If flag set CLEAR_TOP, assume Exit was called from the menu
		// finish before doing any work
		if(getIntent().getFlags() == getIntent().FLAG_ACTIVITY_CLEAR_TOP){
			finish();
		}

		dataHelper = new DataHelper(this);

		setContentView(R.layout.add_feed);
		rssCount = 0;

		// Set up widgets for the user to interact with from xml layout
		final EditText userInput = (EditText) findViewById(R.id.userRSSUrl);
		Button addByUrl = (Button) findViewById(R.id.submitUrl);
		ListView rssFeedList = (ListView) findViewById(R.id.rss_list);

		addByUrl.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				newUrl = userInput.getText().toString();
				parser = new RSSParser(newUrl);

				// add the newUrl to be displayed on the rssList
				// in the list of included RSS Feeds
				addUrl(newUrl);

				// launch listview with intent here. Bundle rssList in extra fields
				Intent i = new Intent(com.nwmorris.rss.AddFeedFromUserActivity.this,
						com.nwmorris.rss.FeedViewActivity.class);

				i.putExtra("rssList", rssList);

				startActivity(i); 
			}
		});
	}

	/*
	 * On submit check what is in userRSSUrl 
	 */
	private void addUrl(String url) {
		try{
			URL newUrl = new URL(url);
			ContentValues updateUrl = new ContentValues();
			database = dataHelper.getWritableDatabase();
			updateUrl.put("url", url);
			database.insert(DataHelper.SOURCE_TABLE_NAME, null, updateUrl);
		
		}catch(Exception e){
			Log.v(TAG, "Entry already exists or is not proper URL format");
		}
	}
}
