package cz.android.monet.restexample.test;

import cz.android.monet.restexample.R;
import cz.android.monet.restexample.RESTExampleActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;


public class RESTExampleTest extends
		ActivityInstrumentationTestCase2<RESTExampleActivity> {
	
	private EditText host;

	public RESTExampleTest() {
		super("cz.android.monet.restexample", RESTExampleActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		RESTExampleActivity activ = this.getActivity();
		host = (EditText) activ.findViewById(R.id.editHostAddress);
	}
	
    public void testPreconditions() {
        assertNotNull(host);
      }
    
    public void testText() {
    	
        assertEquals("193.33.22.109",host.getText().toString());
      }

}
