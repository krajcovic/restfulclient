package cz.android.monet.restfulclient.test;

import cz.android.monet.restfulclient.R;
import cz.android.monet.restfulclient.RESTExampleActivity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class RESTExampleTest.
 */
public class RESTExampleTest extends
		ActivityInstrumentationTestCase2<RESTExampleActivity> {

	/** The e host. */
	private EditText eHost;
	
	/** The btn bar code. */
	private Button btnBarCode;
	
	/** The btn send. */
	private Button btnSend;
	
	/** The output. */
	private TextView output;

	/**
	 * Instantiates a new rEST example test.
	 */
	public RESTExampleTest() {
		super("cz.android.monet.restexample", RESTExampleActivity.class);
	}

	/* (non-Javadoc)
	 * @see android.test.ActivityInstrumentationTestCase2#setUp()
	 */
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
		btnSend = (Button) activ.findViewById(R.id.btnSend);
		output = (TextView) activ.findViewById(R.id.textReceiveData);

		assertNotNull(eHost);
		assertNotNull(btnBarCode);
		assertNotNull(btnSend);
		assertNotNull(output);
	}

	// public void testPreconditions() {
	// assertNotNull(eHost);
	// assertNotNull(btnBarCode);
	// }

	/**
	 * Sets the host focus.
	 */
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

	/**
	 * Test default value.
	 */
	public void testDefaultValue() {

		assertEquals("193.33.22.109", eHost.getText().toString());
	}

	/**
	 * Test set host value.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
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

//	public void testStateDestroy() {
//
//		assertEquals("193.33.22.109", eHost.getText().toString());
//		setHostFocus();
//		sendKeys(KeyEvent.KEYCODE_DPAD_RIGHT);
//		sendKeys(KeyEvent.KEYCODE_DEL);
//		sendKeys("9");
//
//		this.getActivity().finish();
//
//		RESTExampleActivity activ = this.getActivity();
//		eHost = (EditText) activ.findViewById(R.id.editHostAddress);
//		assertEquals("993.33.22.109", eHost.getText().toString());
//	}

	/**
 * Test state pause.
 */
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

	/**
	 * Test monkey bugs.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@UiThreadTest
	public void testMonkeyBugs() throws InterruptedException {
		assertEquals("193.33.22.109", eHost.getText().toString());
		setHostFocus();
		eHost.setText("n%w;ay193.33.22.6MG=109km,*mmp`");

		btnSend.performClick();
		
		assertEquals(true, output.getText().toString().isEmpty());
	}
}
