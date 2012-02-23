package com.nwmorris.rss;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.view.View;

/**
 * 
 * Activity creates a ListView to display stored rss feeds from the database
 *
 * @author neil
 *
 */
public class FeedViewActivity extends RSSReaderActivity {
	private static final String TAG = "ListFeeds";
	String prefix;
	String[] rssList;
	String[] columns;
	Object[] column_objects;
	ListView listView;
	ArrayList<String> displayedColumns = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		// Adds the columns to be displayed in the list
		setupList();
		// Only reading information here...
		dataHelper = new DataHelper(this);
		this.database = dataHelper.getReadableDatabase();
		
		// displayedColumns is an ArrayList here to simplify adding additional columns to be displayed later
		column_objects = displayedColumns.toArray();
		columns = new String[column_objects.length];
		for(int i=0; i<column_objects.length; i++) {
			columns[i] = column_objects[i].toString();
		}
				
		// order the list by the first displayed column
		Cursor data = database.query(DataHelper.SOURCE_TABLE_NAME, columns, null, null, null, null, null);
		
		dataSource = new SimpleCursorAdapter(this,
								R.layout.feedview, data, columns,
								new int[] { R.id.listurls });

		listView = new ListView(this);
		listView.setAdapter(dataSource);
		listView.setTextFilterEnabled(true);
		setContentView(listView);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id){
				
				// Setup db cursor to retrieve strings
				SQLiteDatabase database = dataHelper.getReadableDatabase();
				Cursor data = database.query(DataHelper.SOURCE_TABLE_NAME, columns, null, null, null, null, null);
				data.moveToPosition(position);
				
				// launch HeadlineView for the clicked rssFeed
				Intent i = new Intent(com.nwmorris.rss.FeedViewActivity.this,
						com.nwmorris.rss.HeadlineViewActivity.class);

				i.putExtra("rss_url", data.getString(data.getColumnIndex("url")));

				startActivity(i); 
			}
		});
	}

	/*
	 * helper method to add the columns being queried from database
	 */
	private void setupList(){
		displayedColumns.add("url");
		displayedColumns.add(BaseColumns._ID); 
	}
}
