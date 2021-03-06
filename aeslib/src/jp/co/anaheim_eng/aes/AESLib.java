/*
 * AESLib.java
 *
 * Created on: 2011/07/06
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
package jp.co.anaheim_eng.aes;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES library
 * 
 * @author Reiki Hattori.
 * @date 2011/07/6
 * @since Android API Level 7
 *
 */
public class AESLib {
	private static final int MIN_PASS_LENGTH = 4;

	private static int BitLength;
//	private static byte[] rawKey;
	private static SecretKey secret;
	private ArrayList<String> PlainText;
	private ArrayList<byte[]> Ciphers;
	
	/**
	 * Default Constructor
	 * @param keyBitLen
	 * @param CiphersKey
	 * @param Salt
	 * @throws Exception 
	 * @throws Exception
	 */
	public AESLib(int keyBitLen, String CiphersKey, String Salt) throws Exception {
		// Check of AESLib's parameters
		if ((keyBitLen != 128) && (keyBitLen != 192) && (keyBitLen != 256)) {
			throw new AESLibException("Wrong key bit length of AES.");
		}
		if ((CiphersKey == null) || (CiphersKey.length() < MIN_PASS_LENGTH)) {
			if ((CiphersKey == null) || (CiphersKey.length() == 0)) {
				throw new AESLibException("Nothing of cipher parameter of AESLib.");
			} else {
				throw new AESLibException("Wrong cipher length of AESLib.");
			}
		}
		
		BitLength = keyBitLen;
		secret = createSecretKey(CiphersKey.toCharArray(), Salt.getBytes("UTF-8"));
		PlainText = new ArrayList<String>();
		Ciphers = new ArrayList<byte[]>();
	}

	/**
	 * Return to cipher key's bit length
	 * @return int
	 */
	public static int getBitLength() {
		return BitLength;
	}

	/**
	 * after Decode method : output of plane text ( ArrayList )
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getPlainText() {
		return PlainText;
	}

	/**
	 * before Encode method : input of plane text ( ArrayList )
	 */
	public void setPlainText(ArrayList<String> plainText) {
		PlainText = plainText;
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
	public void setCiphers(ArrayList<byte[]> ciphers) {
		Ciphers = ciphers;
	}

	public static int getMinPassLength() {
		return MIN_PASS_LENGTH;
	}

	/**
	 * Start AES encode
	 * @throws Exception 
	 */
	public void Encode() throws Exception {
		// Check of input & output data
		if (PlainText.size() == 0) {
			throw new AESLibException("Plain text data is not found.");
		}
		Ciphers = null;
		Ciphers = new ArrayList<byte[]>();

		// Call AES encrypt
		for (String obj : PlainText) {
			byte[] result = encrypt(obj.getBytes("UTF-8"));
			Ciphers.add(result);
		}
	}
	
	private static byte[] encrypt(byte[] clear) throws Exception {
	    SecretKeySpec skeySpec = new SecretKeySpec(secret.getEncoded(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	    byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	/**
	 * Start AES decode
	 * @param none
	 * @throws Exception 
	 */
	public void Decode() throws Exception {
		// Check of input & output data
		if (Ciphers.size() == 0) {
			throw new AESLibException("Ciphers data is not found.");
		}
		PlainText = null;
		PlainText = new ArrayList<String>();
		
		// Call AES decrypt
		for (byte[] obj : Ciphers) {
			byte[] result = decrypt(obj);
			PlainText.add(new String(result));
		}
	}
	
	private static byte[] decrypt(byte[] encrypted) throws Exception {
	    SecretKeySpec skeySpec = new SecretKeySpec(secret.getEncoded(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
	    byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	/**
	 * Key Generate
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	private static SecretKey createSecretKey(char[] passwd, byte[] salt) throws Exception {
		String algorithm = "";

		switch (BitLength) {
		case 128: algorithm = "PBEWithSHA256And128BitAES-CBC-BC";
		case 192: algorithm = "PBEWithSHA256And192BitAES-CBC-BC";
		case 256: algorithm = "PBEWithSHA256And256BitAES-CBC-BC";
		}
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
		KeySpec spec = new PBEKeySpec(passwd, salt, BitLength, BitLength);
		SecretKey base = factory.generateSecret(spec);
		
	    return base;
	}
}
