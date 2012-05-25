/*
 * 
 */
package cz.android.monet.restfulclient.fragments;

import cz.android.monet.restfulclient.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class OutputFragment.
 */
public class OutputFragment extends Fragment {

	/** The Constant TAG. */
	private static final String TAG = "OutputFragment";

	/** The receive data. */
	private TextView receiveData;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		receiveData = (TextView) getView().findViewById(R.id.textReceiveData);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.output, container, false);
	}

	/**
	 * Update result view.
	 *
	 * @param message the message
	 */
	public void updateResultView(String message) {
		if (receiveData != null) {
			receiveData.setText(message);
		} else {
			Log.e(TAG, "receiveData isn't allocated.");
		}
	}

}
