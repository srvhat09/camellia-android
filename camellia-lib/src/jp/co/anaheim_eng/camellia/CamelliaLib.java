/*
 * CamelliaLib.java
 *
 * Created on: 2011/05/16
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
package jp.co.anaheim_eng.camellia;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Camellia JNI library
 * 
 * @author Reiki Hattori.
 * @date 2011/05/16
 * @since Android API Level 7
 *
 */
public class CamelliaLib {

	private static final int MIN_PASS_LENGTH = 4;
	
	// load of library
	static {
		System.loadLibrary("camellia-jni");
		init();
	}
	
	// Declare a native method
	private native static void init();
	native static int KeyGen(int keyBitLen, byte[] hashKey);
	native static int Encode(int keyBitLen, ArrayList<?> PlainText, ArrayList<?> Ciphers);
	native static int Decode(int keyBitLen, ArrayList<?> Ciphers, ArrayList<?> PlainTexts);
	
	private static int BitLength;
	private static byte[] HashDigt;
	private ArrayList<String> PlainText;
	private ArrayList<byte[]> Ciphers;
	
	/**
	 * Default Constructor
	 * @throws CamelliaLibException 
	 */
	public CamelliaLib(int keyBitLen, String CiphersKey) throws CamelliaLibException {
		// Check of camellia-android's parameters
		if ((keyBitLen != 128) && (keyBitLen != 192) && (keyBitLen != 256)) {
			throw new CamelliaLibException("Wrong key bit length of camellia.");
		}
		if ((CiphersKey == null) || (CiphersKey.length() < MIN_PASS_LENGTH)) {
			if ((CiphersKey == null) || (CiphersKey.length() == 0)) {
				throw new CamelliaLibException("Nothing of cipher parameter of camellia-android.");
			} else {
				throw new CamelliaLibException("Wrong cipher length of camellia-android.");
			}
		}

		BitLength = keyBitLen;
		HashDigt = GetDigest(CiphersKey);
		PlainText = new ArrayList<String>();
		Ciphers = new ArrayList<byte[]>();
	}


	/**
	 * Return to cipher key's bit length
	 * @return int
	 */
	public int getBitLength() {
		return BitLength;
	}

	/**
	 * Return to hash code of ciphers key 
	 * @return byte[]
	 */
	public byte[] getHashDigt() {
		return HashDigt;
	}

	/**
	 * after Decode method : output of plane text ( ArrayList )
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getPlainText() {
		return PlainText;
	}

	/**
	 * after Decode method : output of plane text
	 * @return String
	 */
	public String getPlainText(int pos) {
		return this.PlainText.get(pos);
	}

	/**
	 * before Encode method : input of plane text ( ArrayList )
	 */
	public void setPlainText(ArrayList<String> PlainText) {
		this.PlainText = null;
		this.PlainText = PlainText;
	}

	/**
	 * before Encode method : input of plane text
	 */
	public void setPlainText(String PlainText) {
		this.PlainText.add(PlainText);
	}

	/**
	 * after Encode method : output of cipher bytes ( ArrayList )
	 * @return ArrayList<byte[]>
	 */
	public ArrayList<byte[]> getCiphers() {
		return Ciphers;
	}

	/**
	 * after Encode method : output of cipher byte array
	 * @param pos
	 * @return byte[]
	 */
	public byte[] getCiphers(int pos) {
		return this.Ciphers.get(pos);
	}

	/**
	 * before Decode method : input of cipher bytes ( ArrayList )
	 */
	public void setCiphers(ArrayList<byte[]> Ciphers) {
		this.Ciphers = Ciphers;
	}

	/**
	 * Start camellia encode
	 * @param none
	 * @throws CamelliaLibException 
	 */
	public void Encode() throws CamelliaLibException {
		// Check of input & output data
		if (PlainText.size() == 0) {
			throw new CamelliaLibException("Plain text data is not found.");
		}
		Ciphers = null;
		Ciphers = new ArrayList<byte[]>();
		
		// Call camellia-android key generate
		if (KeyGen(BitLength, HashDigt) != 0) {
			throw new CamelliaLibException("camellia-android key genarate error.");
		}
		// Call camellia-android encode
		if (Encode(BitLength, PlainText, Ciphers) != 0) {
			throw new CamelliaLibException("camellia-android encode error.");
		}
	}
	
	/**
	 * Start camellia decode
	 * @param none
	 * @throws CamelliaLibException 
	 */
	public void Decode() throws CamelliaLibException {
		// Check of input & output data
		if (Ciphers.size() == 0) {
			throw new CamelliaLibException("Ciphers data is not found.");
		}
		PlainText = null;
		PlainText = new ArrayList<String>();
		
		// Call camellia-android key generate
		if (KeyGen(BitLength, HashDigt) != 0) {
			throw new CamelliaLibException("camellia-android key genarate error.");
		}
		// Call camellia-android decode
		if (Decode(BitLength, Ciphers, PlainText) != 0) {
			throw new CamelliaLibException("camellia-android decode error.");
		}		
	}

	/**
	 * Create SHA256 hash
	 * @param String
	 * @return byte[]
	 */
	private byte[] GetDigest(String str) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return md.digest();
	}
}
