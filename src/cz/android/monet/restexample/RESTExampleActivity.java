package cz.android.monet.restexample;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RESTExampleActivity extends Activity {

	private EditText host;
	private EditText sendData;
	private TextView receiveData;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		receiveData = (TextView) findViewById(R.id.textReceiveData);
		host = (EditText) findViewById(R.id.editHostAddress);
		sendData = (EditText) findViewById(R.id.editSendData);
		// receiveData = (TextView) findViewById(R.id.textReceiveData);

		sendData.setText("1");
		host.setText("193.33.22.109");

		Button butSend = (Button) findViewById(R.id.btnSend);
		butSend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new MyAsyncTask().execute(host.getText().toString(), sendData
						.getText().toString());
			}

		});

		// ThreadPolicy tp = ThreadPolicy.LAX;
		// StrictMode.setThreadPolicy(tp);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.default_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.quit:
			break;
		default:
			Toast.makeText(this, "Menu Item undefined selected",
					Toast.LENGTH_SHORT).show();
			break;
		}
		return true;
	}

	private class MyAsyncTask extends AsyncTask<Object, Void, String> {

		@Override
		protected String doInBackground(Object... params) {

			return SendData(params[0].toString(), params[1].toString());
		}

		protected void onPostExecute(String result) {
			receiveData.setText(result);
		}

		protected String SendData(String host, String userId) {
			String urlToSendRequest = "http://" + host + ":" + "2323"
					+ "/restfulexample/app/user/" + userId;
			String targetDomain = host;
			// String xmlContentToSend = "hello this is a test";

			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpHost targetHost = new HttpHost(targetDomain, 2323, "http");
			// Using GET here
			HttpGet httpGet = new HttpGet(urlToSendRequest);

			// Make sure the server knows what kind of a response we will accept
			httpGet.addHeader("Accept", "application/xml");

			// Also be sure to tell the server what kind of content we are
			// sending
			httpGet.addHeader("Content-Type", "application/xml");

			try {
				// execute is a blocking call, it's best to call this code in a
				// thread separate from the ui's
				HttpResponse response = httpClient.execute(targetHost, httpGet);

				// Have your way with the response
				final OutputStream outstrem = new ByteArrayOutputStream();

				response.getEntity().writeTo(outstrem);
				return outstrem.toString();

			} catch (Exception ex) {
				ex.printStackTrace();
				Log.e(ex.getClass().toString(), ex.getMessage());
			}
			return null;
		}
	}
}