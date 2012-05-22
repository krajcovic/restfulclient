package cz.android.monet.restfulclient.providers;

import android.R;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.UserDictionary;
import android.text.TextUtils;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorAdapter;

public class HistoryHostsProvider {
	
	// A "projection" defines the columns that will be returned for each row
	private String[] mProjection =
	{
	    UserDictionary.Words._ID,    // Contract class constant for the _ID column name
	    UserDictionary.Words.WORD,   // Contract class constant for the word column name
	};

	// Defines a string to contain the selection clause
	private String mSelectionClause = null;

	// Initializes an array to contain selection arguments
	private String[] mSelectionArgs = {""};
	
	private void fillSelection(String mSearchString)
	{
		// If the word is the empty string, gets everything
		if (TextUtils.isEmpty(mSearchString)) {
		    // Setting the selection clause to null will return all words
		    mSelectionClause = null;
		    mSelectionArgs[0] = "";

		} else {
		    // Constructs a selection clause that matches the word that the user entered.
		    //mSelectionClause = " = ?";
			mSelectionClause = " IN(?,?)";

		    // Moves the user's input string to the selection arguments.
		    mSelectionArgs[0] = mSearchString;

		}

	}
	
	public Cursor getUserDictionaryWords(Context context, String mSearchString)
	{	
		fillSelection(mSearchString);
		
		return context.getContentResolver().query(
			    UserDictionary.Words.CONTENT_URI,  // The content URI of the words table
			    mProjection,                       // The columns to return for each row
			    null,	               // Either null, or the word the user entered
			    null,    // Either empty, or the string the user entered
			    null);                       // The sort order for the returned rows

	}
	
	public SimpleCursorAdapter getUserDictionaryWords(Context context)
	{	
		//new SimpleCursorAdapter(context, (Integer) null, null, null, null);
		return new SimpleCursorAdapter(
			    context,       // The application's Context object
			    R.layout.simple_list_item_1,                  // A layout in XML for one row in the ListView
			    getUserDictionaryWords(context, null),                              // The result from the query
			    mProjection,                      // A string array of column names in the cursor
			    null);                                    // Flags (usually none are needed)

	}
}
