package cz.android.monet.restfulclient.test;

import cz.android.monet.restfulclient.R;
import cz.android.monet.restfulclient.RESTExampleActivity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

public class RESTExampleTest extends
		ActivityInstrumentationTestCase2<RESTExampleActivity> {

	private EditText eHost;
	private Button btnBarCode;

	public RESTExampleTest() {
		super("cz.android.monet.restexample", RESTExampleActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();

		// Calls setActivityInitialTouchMode(false). This turns off touch mode
		// in the device or emulator. If any of your test methods send key
		// events to the application, you must turn off touch mode before you
		// start any activities; otherwise, the call is ignored.
		// setActivityInitialTouchMode(false);

		RESTExampleActivity activ = this.getActivity();
		eHost = (EditText) activ.findViewById(R.id.editHostAddress);
		btnBarCode = (Button) activ.findViewById(R.id.btnReadBarCode);

		assertNotNull(eHost);
		assertNotNull(btnBarCode);
	}

	// public void testPreconditions() {
	// assertNotNull(eHost);
	// assertNotNull(btnBarCode);
	// }

	private void setHostFocus() {
		this.getActivity().runOnUiThread(new Runnable() {

			public void run() {

				EditText eHost = (EditText) getActivity().findViewById(
						R.id.editHostAddress);
				assertNotNull(eHost);

				eHost.requestFocus();
			}
		});
	}

	public void testDefaultValue() {

		assertEquals("193.33.22.109", eHost.getText().toString());
	}

	public void testSetHostValue() throws InterruptedException {
		// setActivityInitialTouchMode(true);
		setHostFocus();

		for (int i = 20; i != 0; i--) {
			sendKeys(KeyEvent.KEYCODE_DPAD_RIGHT);
			sendKeys(KeyEvent.KEYCODE_DEL);
		}

		assertEquals("", eHost.getText().toString());

		setHostFocus();

		sendKeys("1 7 2");
		sendKeys(KeyEvent.KEYCODE_PERIOD);
		sendKeys("2 5");
		sendKeys(KeyEvent.KEYCODE_PERIOD);
		sendKeys("5 0");
		sendKeys(KeyEvent.KEYCODE_PERIOD);
		sendKeys("5 3");

		assertEquals("172.25.50.53", eHost.getText().toString());
	}

	public void testStateDestroy() {

		assertEquals("193.33.22.109", eHost.getText().toString());
		setHostFocus();
		sendKeys(KeyEvent.KEYCODE_DPAD_RIGHT);
		sendKeys(KeyEvent.KEYCODE_DEL);
		sendKeys("9");

		this.getActivity().finish();

		RESTExampleActivity activ = this.getActivity();
		eHost = (EditText) activ.findViewById(R.id.editHostAddress);
		assertEquals("993.33.22.109", eHost.getText().toString());

	}

	@UiThreadTest
	public void testStatePause() {
		// This is used later to invoke the onPause() and onResume() methods:
		Instrumentation mInstr = this.getInstrumentation();
		
		assertEquals("193.33.22.109", eHost.getText().toString());
		setHostFocus();
		eHost.setText("127.0.0.1");
		
		mInstr.callActivityOnPause(this.getActivity());
		
		assertEquals("127.0.0.1", eHost.getText().toString());
		setHostFocus();
		eHost.setText("127.0.0.2");
		
		mInstr.callActivityOnResume(this.getActivity());
		assertEquals("127.0.0.2", eHost.getText().toString());
		
		
	}
}
