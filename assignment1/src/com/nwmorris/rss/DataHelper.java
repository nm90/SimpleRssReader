package com.nwmorris.rss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 *
 * Helper class to initialize database to store info between sessions
 *
 * @author neil
 *
 */
public class DataHelper extends SQLiteOpenHelper {

		protected static final String DATABASE_NAME = "rssfeed.db";
	    private static final int DATABASE_VERSION = 2;
	    protected static final String SOURCE_TABLE_NAME = "tbl_sources";
	    
		private static final String CREATE_RSS_TABLE =
    			"CREATE TABLE "+SOURCE_TABLE_NAME+"(	"+
    					BaseColumns._ID +
    						" INTEGER PRIMARY KEY AUTOINCREMENT,	"+
    					"url VARCHAR,								"+
    					"title TEXT,								"+
    					"UNIQUE(url)								"+
    			")										";			
	    
	    DataHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(CREATE_RSS_TABLE);
	    }

}
