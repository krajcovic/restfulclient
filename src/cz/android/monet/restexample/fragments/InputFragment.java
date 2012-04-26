package cz.android.monet.restexample.fragments;

import cz.android.monet.restexample.R;
import cz.android.monet.restexample.MyAsyncTask;
import cz.android.monet.restexample.interfaces.OnServerResultReturned;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InputFragment extends Fragment {

	private EditText host;
	private EditText sendData;
	private String receiveData;
	
	OnServerResultReturned mResultCallback;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		host = (EditText) getView().findViewById(R.id.editHostAddress);
		sendData = (EditText) getView().findViewById(R.id.editSendData);

		sendData.setText("1");
		host.setText("193.33.22.109");

		Button butSend = (Button) getView().findViewById(R.id.btnSend);
		butSend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new MyAsyncTask().execute(host.getText().toString(), sendData
						.getText().toString(), mResultCallback);
			}

		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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
}
