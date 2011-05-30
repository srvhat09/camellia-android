/* 
 * MainActivity.java
 *
 * Created on: 2011/05/21
 * AUTHOR:	Reiki Hattori - http://www.anaheim-eng.co.jp/
 * Copyright (c) 2011, Anaheim Engineering Co.,LTD. All rights reserved.
 * Copyright (c) 2011, Johospace Co.,Ltd. All rights reserved.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.co.anaheim_eng.camelliasample;

import java.util.ArrayList;

import org.apache.commons.codec.binary.Hex;

import jp.co.anaheim_eng.camellia.CamelliaLib;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    static private ArrayList<String> mPlainText;
    static private ArrayList<byte[]> mCiphers;
    static private String mPassword;
    static private int mKeyBitLen;
    
    private Button btnEncode;
	private Button btnDecode;
	private Button btnPref;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnEncode = (Button)findViewById(R.id.btn_encode);
        btnEncode.setOnClickListener(this);
        btnDecode = (Button)findViewById(R.id.btn_decode);
        btnDecode.setOnClickListener(this);
        btnPref = (Button)findViewById(R.id.btn_pref);
        btnPref.setOnClickListener(this);
        
        if (getPreferences() == false) {
			Intent intent = new Intent(this, MainPreferenceActivity.class);
			startActivity(intent);
        }
    }

	@Override
	public void onClick(View v) {
		byte[] bytes;
		
    	try {
    		switch (v.getId()) {
    		case R.id.btn_encode : {
    	        if (getPreferences() == false) {
    				Intent intent = new Intent(this, MainPreferenceActivity.class);
    				startActivity(intent);
    				return;
    	        }
    	        // Create Camellia Library object
    	        CamelliaLib cl = new CamelliaLib(mKeyBitLen, mPassword);
    			cl.setPlainText(mPlainText);
    			cl.Encode();
    			// Encode result
    			mCiphers = cl.getCiphers();
    			StringBuilder sb = new StringBuilder();
    			sb.append("Camellia Encode result\n");
    			for (int i=0; i<3; i++) {
	    			sb.append("No." + String.valueOf(i+1) + "\n");
	    			sb.append("Plain Text:" + mPlainText.get(i) + "\n");
	    			bytes = mCiphers.get(i);
	    			sb.append("Cipher    :" + String.valueOf(Hex.encodeHex(bytes)) + "\n");
    			}
    			TextView tv = (TextView) findViewById(R.id.textView1);
    			tv.setText(sb);    			
    			break;
    		}
    		case R.id.btn_decode : {
    			if ((mCiphers == null) || (mCiphers.size() == 0)) {
    				Toast.makeText(this, R.string.err_nonecipher, Toast.LENGTH_LONG).show();
    				return;
    			}
    	        // Create Camellia Library object
    	        CamelliaLib cl = new CamelliaLib(mKeyBitLen, mPassword);
    			cl.setCiphers(mCiphers);
    			cl.Decode();
    			// Decode result
    			mPlainText = cl.getPlainText();
    			StringBuilder sb = new StringBuilder();
    			sb.append("Camellia Decode result\n");
    			for (int i=0; i<3; i++) {
	    			sb.append("No." + String.valueOf(i+1) + "\n");
	    			bytes = mCiphers.get(i);
	    			sb.append("Cipher    :" + String.valueOf(Hex.encodeHex(bytes)) + "\n");
	    			sb.append("Plain Text:" + mPlainText.get(i) + "\n");
    			}
    			TextView tv = (TextView) findViewById(R.id.textView1);
    			tv.setText(sb);    			
    			break;
    		}
    		case R.id.btn_pref : {
    			Intent intent = new Intent(this, MainPreferenceActivity.class);
    			startActivity(intent);
    			break;
    		}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}

	private boolean getPreferences() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		mPassword  = prefs.getString("edt_password", "");
		if (mPassword.length() == 0) return false;

		mKeyBitLen = Integer.parseInt(prefs.getString("lst_keybitlen", ""));
		if (mKeyBitLen == 0) return false;

		mPlainText = null;
		mPlainText = new ArrayList<String>();
		mCiphers   = null;
		mCiphers   = new ArrayList<byte[]>();

		String PlainText;
		PlainText = prefs.getString("edt_plaintext1", "");
		mPlainText.add(PlainText);
		PlainText = prefs.getString("edt_plaintext2", "");
		mPlainText.add(PlainText);
		PlainText = prefs.getString("edt_plaintext3", "");
		mPlainText.add(PlainText);
		
		return true;
	}

}