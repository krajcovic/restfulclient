/*
 * 
 */
package cz.android.monet.restfulclient.providers;

import java.util.ArrayList;

import android.R;
import android.app.ListActivity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.UserDictionary;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class HistoryHostsProvider.
 */
public class HistoryHostsProvider extends ListActivity {

	// A "projection" defines the columns that will be returned for each row
	/** The m projection. */
	public String[] mProjection = { UserDictionary.Words._ID, // Contract class
																// constant for
																// the _ID
																// column name
			UserDictionary.Words.WORD, // Contract class constant for the word
										// column name
	};

	// Defines a string to contain the selection clause
	/** The m selection clause. */
	private String mSelectionClause = null;

	// Initializes an array to contain selection arguments
	/** The m selection args. */
	private String[] mSelectionArgs = { "" };

	/**
	 * Fill selection.
	 *
	 * @param mSearchString the m search string
	 */
	private void fillSelection(String mSearchString) {
		// If the word is the empty string, gets everything
		if (TextUtils.isEmpty(mSearchString)) {
			// Setting the selection clause to null will return all words
			mSelectionClause = null;
			mSelectionArgs[0] = "";

		} else {
			// Constructs a selection clause that matches the word that the user
			// entered.
			// mSelectionClause = " = ?";
			mSelectionClause = " IN(?,?)";

			// Moves the user's input string to the selection arguments.
			mSelectionArgs[0] = mSearchString;

		}

	}

	/**
	 * Gets the user dictionary words.
	 *
	 * @param context the context
	 * @param mSearchString the m search string
	 * @return the user dictionary words
	 */
	public Cursor getUserDictionaryWords(Context context, String mSearchString) {
		fillSelection(mSearchString);

		return context.getContentResolver().query(
				UserDictionary.Words.CONTENT_URI, // The content URI of the
													// words table
				mProjection, // The columns to return for each row
				null, // Either null, or the word the user entered
				null, // Either empty, or the string the user entered
				null); // The sort order for the returned rows

	}

	/**
	 * Gets the user dictionary words.
	 *
	 * @param context the context
	 * @param layoutId the layout id
	 * @param to the to
	 * @return the user dictionary words
	 */
	public SimpleCursorAdapter getUserDictionaryWords(Context context,
			int layoutId, int[] to) {
		return new SimpleCursorAdapter(context, layoutId,
				getUserDictionaryWords(context, null), mProjection, to);

	}

	// public ArrayAdapter<String> getUserDictionaryWords() {
	// String[] values = new String[] { "193.33.22.109", "172.25.50.53" };

	// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	// R.layout.simple_list_item _1, values);
	// new ArrayAdapter<String>(this, R.layout.history_list,FRUITS);
	// }
}
