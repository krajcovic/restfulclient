/*
 * 
 */
package cz.android.monet.restfulclient;

import cz.android.monet.restfulclient.providers.HistoryHostsProvider;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class HistoryListActivity.
 */
public class HistoryListActivity extends ListActivity {

	/** The Constant TAG. */
	private static final String TAG = "HistoryListActivity";

	// the XML defined views which the data will be bound to
	/** The to. */
	int[] to = new int[] { R.id.historyId, R.id.historyItem };

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new HistoryHostsProvider().getUserDictionaryWords(this,
				R.layout.history_list, to));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				// Toast.makeText(getApplicationContext(),
				// ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

				Intent result = new Intent();
				EditText host = (EditText) view.findViewById(R.id.historyItem);
				result.putExtra("NEW_HOST", host.getText());
				setResult(ListActivity.RESULT_OK, result);

				if (getParent() == null) {
					setResult(ListActivity.RESULT_OK, result);
				} else {
					getParent().setResult(ListActivity.RESULT_OK, result);
				}
				finish();

			}
		});

	}

}
