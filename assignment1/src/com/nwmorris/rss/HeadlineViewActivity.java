package com.nwmorris.rss;

import java.util.ArrayList;

import com.nwmorris.rss.RSSParser.Item;
import com.nwmorris.rss.RSSParser.RssFeed;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * Activity creates a ListView to display headlines of the rss feed based on
 * url passed as an extra field.
 *
 * @author neil
 *
 */
public class HeadlineViewActivity extends RSSReaderActivity {

	private static final String TAG = "ListHeadlines";
	RSSParser parser;
	String url;
	RssFeed feed;
	ArrayList<String> stringArray;
	ListView listView;
	ArrayAdapter<String> dataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// grab the url intended to be displayed
		try {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				url = extras.getString("rss_url");
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	
		// catch imporper url exception here
		try {
			parser = new RSSParser(url);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

		feed = parser.getFeed();
		Log.v(TAG, "feed.title : " + feed.title); // Confirm feed is init'd
		final ArrayList<Item> items = feed.items;

		// fill table with article headlines aka item titles
		stringArray = new ArrayList<String>();
		for(int i=0; i < items.size(); i++){
			stringArray.add(items.get(i).title);
		}

		dataSource = new ArrayAdapter<String>(this, R.layout.headlineview, R.id.listarticles, stringArray);
		listView = new ListView(this);
		listView.setAdapter(dataSource);
		listView.setTextFilterEnabled(true);
		setContentView(listView);
		
		// count is only for debugging
		int count = dataSource.getCount();
		Log.v(TAG, "count:" + count);
	
			
		listView.setOnItemClickListener(new OnItemClickListener() {
			/*
			 * Launch the DescriptionViewActivity on click with intent to
			 * display detailed view of the clicked article
			 */
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id){
				// retrieve the clicked item
				Item thisItem = items.get(position);

				Intent i = new Intent(com.nwmorris.rss.HeadlineViewActivity.this,
						com.nwmorris.rss.DescriptionViewActivity.class);

				i.putExtra("title", thisItem.title);
				i.putExtra("description", thisItem.description);
				i.putExtra("link", thisItem.link);
				i.putExtra("pubDate", thisItem.pubDate);

				startActivity(i); 
			}
		});
	}
}
