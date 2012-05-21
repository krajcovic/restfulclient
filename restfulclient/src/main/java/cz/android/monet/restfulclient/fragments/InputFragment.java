package cz.android.monet.restfulclient.fragments;

import java.util.List;
import cz.android.monet.restfulclient.R;
import cz.android.monet.restfulclient.SendUserIdAsyncTask;
import cz.android.monet.restfulclient.interfaces.OnServerResultReturned;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class InputFragment extends Fragment {

	private static final String TAG = "InputFragment";
	private static final String PREFS_NAME = "RestInputPrefs";
	private static final String PREF_HOST = "hostIP";
	private static final String PREF_SEND_VALUE = "lastBarCode";
	private static final String PREF_PORT = "hostPort";

	private EditText host;
	private EditText sendData;
	OnServerResultReturned mResultCallback;

	SharedPreferences settings;

	// The resul code
	static final int RESULT_OK = -1;

	// The request code
	static final int PICK_CONTACT_REQUEST = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		host = ((EditText) getView().findViewById(R.id.editHostAddress));
		sendData = (EditText) getView().findViewById(R.id.editSendData);

		// Get the intent that started this activity
		Intent intent = getActivity().getIntent();
		Uri data = intent.getData();
		settings = getActivity().getSharedPreferences(PREFS_NAME, 0);

		if (data != null && intent.getType().equals("text/plain")) {
			host.setText(data.getHost().toString());
		} else {
			// Restore preferences
			host.setText(settings.getString("PREF_HOST", "193.33.22.109"));
		}

		sendData.setText(settings.getString("PREF_SEND_VALUE", "123456789"));

		Button butSend = (Button) getView().findViewById(R.id.btnSend);
		butSend.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				new SendUserIdAsyncTask().execute(host.getText().toString().trim(),
						settings.getInt("PREF_PORT", 2323), sendData.getText()
								.toString().trim(), mResultCallback);
			}

		});

		Button butTest = (Button) getView().findViewById(R.id.btnReadBarCode);
		butTest.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri
						.parse("content://contacts"));
				pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only
																// contacts w/
																// phone numbers

				PackageManager packageManager = getActivity()
						.getApplicationContext().getPackageManager();
				List<ResolveInfo> activities = packageManager
						.queryIntentActivities(pickContactIntent, 0);
				boolean isIntentSafe = activities.size() > 0;

				if (isIntentSafe) {
					startActivityForResult(pickContactIntent,
							PICK_CONTACT_REQUEST);
				} else {
					Log.e(TAG, "Activity pickContactIntent isn't safe.");
				}
			}
		});
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		SharedPreferences.Editor editor = settings.edit();

		editor.putString(PREF_HOST, host.getText().toString());
		editor.putString(PREF_SEND_VALUE, sendData.getText().toString());
		editor.putInt(PREF_PORT, 2323);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.input, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mResultCallback = (OnServerResultReturned) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnServerResultReturned");
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Check which request we're responding to
		switch (requestCode) {
		case PICK_CONTACT_REQUEST:
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				// Get the URI that points to the selected contact
				Uri contactUri = data.getData();
				// We only need the NUMBER column, because there will be only
				// one row in the result
				String[] projection = { Phone.NUMBER };

				// Perform the query on the contact to get the NUMBER column
				// We don't need a selection or sort order (there's only one
				// result for the given URI)
				// CAUTION: The query() method should be called from a separate
				// thread to avoid blocking
				// your app's UI thread. (For simplicity of the sample, this
				// code doesn't do that.)
				// Consider using CursorLoader to perform the query.
				Cursor cursor = getActivity().getContentResolver().query(
						contactUri, projection, null, null, null);
				cursor.moveToFirst();

				// Retrieve the phone number from the NUMBER column
				int column = cursor.getColumnIndex(Phone.NUMBER);
				String number = cursor.getString(column);

				// Do something with the phone number...
				mResultCallback.onResultReturned(number);
				sendData.setText(number);

			}
			break;
		}
	}
}
