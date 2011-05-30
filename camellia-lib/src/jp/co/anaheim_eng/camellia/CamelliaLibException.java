/*
 * CamelliaLibException.java
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

/**
 * Camellia JNI library supported exception.
 * 
 * @author Reiki Hattori - http://www.anaheim-eng.co.jp/
 * @date 2011/05/16
 * @since Android API Level 7
 *
 */
public class CamelliaLibException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
     * CamelliaLibException Constructor
     * @param Set of messages
     */
	public CamelliaLibException(String messages) {
		super(messages);
	}

}
