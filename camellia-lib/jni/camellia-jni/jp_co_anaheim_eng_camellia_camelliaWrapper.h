/*
 * jp_co_anaheim_eng_camellia_lib_camelliaWrapper.h
 *
 * Created on: 2011/05/10
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

#include <jni.h>

#ifndef JP_CO_ANAHEIM_ENG_CAMELLIA_CAMELLIAWRAPPER_H_
#define JP_CO_ANAHEIM_ENG_CAMELLIA_CAMELLIAWRAPPER_H_

#if defined(_MSC_VER)
typedef unsigned char uint8_t;
typedef unsigned int uint32_t;
typedef unsigned __int64 uint64_t;
#else
#include <inttypes.h>
#endif

#if __i386__ || __alpha__ || _M_IX86 || __LITTLE_ENDIAN || __LITTLE_ENDIAN__
#define L_ENDIAN
#else
#undef L_ENDIAN
#endif

#ifdef __cplusplus
extern "C" {
#endif

jclass		al_class;						// ArrayList class
//jmethodID	al_init_MID;					// ArrayList object initialize method ID
jmethodID	al_add_MID;						// ArrayList add Method ID encode
jmethodID	al_get_MID;						// ArrayList get Method ID
jmethodID	al_size_MID;					// ArrayList size Method ID

KEY_TABLE_TYPE keyTable;
unsigned char rawKey[32];

/*
 * Method:    Java_jp_co_anaheim_1eng_camellia_CamelliaLib_init
 * Summary:   JNI initialize of camellia encryption algorithm
 * Signature: Reiki Hattori.
 */
JNIEXPORT void JNICALL Java_jp_co_anaheim_1eng_camellia_CamelliaLib_init
  (JNIEnv *, jobject);

/*
 * Method:    Java_jp_co_anaheim_1eng_camellia_CamelliaLib_KeyGen
 * Summary:   Key generate of camellia encryption algorithm
 * Signature: Reiki Hattori.
 */
JNIEXPORT jint JNICALL Java_jp_co_anaheim_1eng_camellia_CamelliaLib_KeyGen
  (JNIEnv *, jobject, jint, jbyteArray);


/*
 * Method:    Java_jp_co_anaheim_1eng_camellia_CamelliaLib_Encode
 * Summary:   Encoding of camellia encryption algorithm
 * Signature: Reiki Hattori.
 */
JNIEXPORT jint JNICALL Java_jp_co_anaheim_1eng_camellia_CamelliaLib_Encode
  (JNIEnv *, jobject, jint, jobject, jobject);


/*
 * Method:    Java_jp_co_anaheim_1eng_camellia_CamelliaLib_Decode
 * Summary:   Decoding of camellia encryption algorithm
 * Signature: Reiki Hattori.
 */
JNIEXPORT jint JNICALL Java_jp_co_anaheim_1eng_camellia_CamelliaLib_Decode
  (JNIEnv *, jobject, jint, jobject, jobject);

#ifdef __cplusplus
}
#endif

#endif /* JP_CO_ANAHEIM_ENG_CAMELLIA_LIB_CAMELLIAWRAPPER_H_ */


#define DEBUG 1
