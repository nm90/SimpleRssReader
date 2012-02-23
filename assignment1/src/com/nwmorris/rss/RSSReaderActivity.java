package com.nwmorris.rss;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

/**
 * Super class that all activities will inherit in order to
 *  (*)declare variables for access to the database 
 *  (*)use the same menu 
 * 
 * @author neil
 *
 */
public class RSSReaderActivity extends Activity {
	
	protected DataHelper dataHelper;
	protected SimpleCursorAdapter dataSource;
	protected SQLiteDatabase database;
    
    /* 
     * Called when the activity is first created. 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

    }
    
    /*
     * Set xml for menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }
    
    /*
     * Set items in menu and resulting actions
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.quit:
            	// Finish all of the activities on top of the stack, launch the initial activity and call finish() there.
            	Intent i = new Intent(com.nwmorris.rss.RSSReaderActivity.this,
						com.nwmorris.rss.AddFeedFromUserActivity.class);
            	i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(i);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
