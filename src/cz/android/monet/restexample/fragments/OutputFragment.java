package cz.android.monet.restexample.fragments;

import cz.android.monet.restexample.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OutputFragment extends Fragment {

	private static final String TAG = "OutputFragment";

	private TextView receiveData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		receiveData = (TextView) getView().findViewById(R.id.textReceiveData);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.output, container, false);
	}

	public void updateResultView(String message) {
		if (receiveData != null) {
			receiveData.setText(message);
		} else {
			Log.e(TAG, "receiveData isn't allocated.");
		}
	}

}
