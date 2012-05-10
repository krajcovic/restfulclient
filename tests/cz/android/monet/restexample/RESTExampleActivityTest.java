package cz.android.monet.restexample;

import android.test.ActivityInstrumentationTestCase2;

public class RESTExampleActivityTest extends
		ActivityInstrumentationTestCase2<RESTExampleActivity> {

	public RESTExampleActivityTest() {
		super("cz.android.monet.restexample", RESTExampleActivity.class);  
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		RESTExampleActivity testedActivity = getActivity();  
	}

}
