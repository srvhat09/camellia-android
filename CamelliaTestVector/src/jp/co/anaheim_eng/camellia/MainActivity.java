package jp.co.anaheim_eng.camellia;

import jp.co.anaheim_eng.camellia.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	static {
    	// ライブラリロード
        System.loadLibrary("TestVector");
    }

    public native int testVectors();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView tv = (TextView) findViewById(R.id.textView1);
        
        int rtncd = testVectors();
        if (rtncd != 0) {
        	tv.setText("Camellia test vector has error.");
        } else {
        	tv.setText("Camellia test vector is sucess.\n output file:/sdcard/a_camellia.txt");
        }
    }
}