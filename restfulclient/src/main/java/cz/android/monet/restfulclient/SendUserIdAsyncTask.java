/*
 * 
 */
package cz.android.monet.restfulclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import cz.android.monet.restfulclient.interfaces.OnServerResultReturned;

import android.R.bool;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.URLUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class SendUserIdAsyncTask.
 */
public class SendUserIdAsyncTask extends AsyncTask<Object, Void, String> {

	/** The Constant TAG. */
	private static final String TAG = "SendUserIdAsyncTask";

	/** The m result callback. */
	OnServerResultReturned mResultCallback;

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(Object... params) {

		mResultCallback = (OnServerResultReturned) params[3];
		return sendData(params[0].toString(), new Integer(params[1].toString()), params[2].toString());
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	protected void onPostExecute(String result) {
		mResultCallback.onResultReturned(result);
	}

/*	private boolean validateHost(String string) {
		Pattern p = Pattern.compile("^\\s*(.*?)");
		Matcher m = p.matcher(string);

		return m.matches();
	}*/

	/**
 * Send data.
 *
 * @param targetDomain the target domain
 * @param targetPort the target port
 * @param userId the user id
 * @return the string
 */
protected String sendData(String targetDomain, Integer targetPort, String userId) {

		try {
			if (!targetDomain.matches("^\\s*(.*?)")) {
				Log.e(TAG, "Invalid host string | " + targetDomain);
				return null;
			}

			Uri.Builder uri = Uri.parse("http://" + targetDomain + ":" + targetPort.toString()).buildUpon();
			uri.path("/restfulexample/app/user/" + userId);		
			if(!URLUtil.isValidUrl(uri.toString()))
			{
				Log.e(TAG, "Invalid uri |" + uri.toString());
				return null;
			}


			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			HttpHost targetHost = new HttpHost(targetDomain, 2323, "http");
			// Using GET here
			//HttpGet httpGet = new HttpGet(urlToSendRequest);
			HttpGet httpGet = new HttpGet(new java.net.URI(uri.toString()));

			// Make sure the server knows what kind of a response we will accept
			httpGet.addHeader("Accept", "application/xml");

			// Also be sure to tell the server what kind of content we are
			// sending
			httpGet.addHeader("Content-Type", "application/xml");

			// execute is a blocking call, it's best to call this code in a
			// thread separate from the ui's
			HttpResponse response = httpClient.execute(targetHost, httpGet);

			// Have your way with the response
			final OutputStream outstrem = new ByteArrayOutputStream();

			response.getEntity().writeTo(outstrem);
			return outstrem.toString();

		} catch (ClientProtocolException ex) {
			ex.printStackTrace();
			Log.e(TAG, ex.getMessage());
		} catch (URISyntaxException ex) {
			ex.printStackTrace();
			Log.e(TAG, ex.getMessage());
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			Log.e(TAG, ex.getMessage());
		} catch (IOException ex) {
			ex.printStackTrace();
			Log.e(TAG, ex.getMessage());
		}

		return null;
	}
}