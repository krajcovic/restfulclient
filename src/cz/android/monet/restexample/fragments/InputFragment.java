package cz.android.monet.restexample.fragments;

import java.util.List;

import cz.android.monet.restexample.R;
import cz.android.monet.restexample.MyAsyncTask;
import cz.android.monet.restexample.interfaces.OnServerResultReturned;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
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

		Button butTest = (Button) getView().findViewById(R.id.btnReadBarCode);
		butTest.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri number = Uri.parse("tel:734423665");
				Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

				// Map point based on address
				Uri location = Uri
						.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
				// Or map point based on latitude/longitude
				// Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); //
				// z param is zoom level
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

				Uri webpage = Uri.parse("http://www.android.com");
				Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
				
				Intent intent = new Intent(Intent.ACTION_SEND);

				// Always use string resources for UI text. This says something like "Share this photo with"
				String title = "Vyberte aplikaci";
				// Create and start the chooser
				Intent chooser = Intent.createChooser(intent, title);
	
				PackageManager packageManager = getActivity().getApplicationContext().getPackageManager();
				List<ResolveInfo> activities = packageManager
						.queryIntentActivities(webIntent, 0);
				boolean isIntentSafe = activities.size() > 0;

				if (isIntentSafe) {
					startActivity(chooser);
				}
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
