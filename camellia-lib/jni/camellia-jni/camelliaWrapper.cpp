/*
 * camelliaWrapper.cpp
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

#include <stdio.h>
#include <string.h>
#include <jni.h>
#include <android/log.h>
#include "../camellia/camellia.h"
#include "jp_co_anaheim_eng_camellia_camelliaWrapper.h"

#include <string>
#include <iostream>
#include <assert.h>

#if DEBUG
#  define DebugInfoLog(...)	((void)__android_log_print(ANDROID_LOG_INFO, "CamelliaWrapper", __VA_ARGS__))
#else
#  define DebugInfoLog(...)	do{}while(0)
#endif

#define ERROR 1
#if ERROR
#  define ErrorLog(...)	((void)__android_log_print(ANDROID_LOG_ERROR, "CamelliaWrapper", __VA_ARGS__))
#else
#  define ErrorLog(...)	do{}while(0)
#endif

/*
 * Method:    CheckKeyBitLength
 * Param :	jint keyBitLen				Encode or Decode bit length
 */
int CheckKeyBitLength(jint keyBitLen)
{
	if ((keyBitLen == 128) or (keyBitLen == 192) or (keyBitLen == 256)) return 0;
	return 1;
}

#if DEBUG
/*
 * Debug method
 */
void DebugChar2Hex(unsigned char *data, int size, char *rtn) {
	char buf[10] = "";

	int i;
	for (i=0; i < size; i++) {
		sprintf(buf, "%02X ", data[i]);
		strcat(rtn, buf);
	}
	return;
}
void Log_Camellia_Ekeygen(int keyBitLen, unsigned char *rawKey) {
	char buf[200] = "";
	sprintf(buf, "Camellia with %d-bit key", keyBitLen);
	DebugInfoLog(buf);
	sprintf(buf, "K : ");
	DebugChar2Hex(rawKey, keyBitLen/8, buf);
	DebugInfoLog(buf);
}
void Log_Camellia_EncryptBlock(unsigned char *pt_buf, unsigned char *ct_buf) {
	char buf[200] = "";
	sprintf(buf, "[Encrypt] P : ");
	DebugChar2Hex(pt_buf, 16, buf);
	DebugInfoLog(buf);
	sprintf(buf, "[Encrypt] C : ");
	DebugChar2Hex(ct_buf, 16, buf);
	DebugInfoLog(buf);
}
void Log_Camellia_DecryptBlock(unsigned char *pt_buf, unsigned char *ct_buf) {
	char buf[200] = "";
	sprintf(buf, "[Decrypt] C : ");
	DebugChar2Hex(ct_buf, 16, buf);
	DebugInfoLog(buf);
	sprintf(buf, "[Decrypt] P : ");
	DebugChar2Hex(pt_buf, 16, buf);
	DebugInfoLog(buf);
}
#endif

/*
 * Method:	jniExceptionThrow
 * Param :	JNIEnv *env					JNI
 */
int jniExceptionThrow(JNIEnv* env, const char* className, const char* msg)
{
    jclass exceptionClass;
	char buf[256] = "";		// Error info

    exceptionClass = env->FindClass(className);
    if (exceptionClass == NULL) {
    	sprintf(buf, "Unable to find exception class %s :%s", className, msg);
        ErrorLog(buf);
        assert(0);      /* fatal during dev; should always be fatal? */
        return -1;
    }

    if (env->ThrowNew(exceptionClass, msg) != JNI_OK) {
        sprintf(buf, "Failed throwing '%s' '%s'", className, msg);
        assert(!"failed to throw");
    }
    return 0;
}

/*
 * Method:	jniFindClass
 * Param :	JNIEnv *env					JNI
 * 			const char *cname			class name(Android format)
 */
jclass jniFindClass(JNIEnv* env, const char* cname){
	jclass c_obj = (jclass)env->FindClass (cname);
	if (c_obj == NULL) {
		char buf[256];
		sprintf(buf, "Undefined class name:%s", cname);
		ErrorLog(buf);
		jniExceptionThrow(env, "java/lang/ClassNotFoundException", cname);
		return NULL;
	}
	return c_obj;
}

/*
 * Method:	jniGetMethodID
 * Param :	JNIEnv *env					JNI
 * 			jclass c_obj				jniFindClass result
 * 			const char* cmethod			java method name
 * 			const char* cparam			java method parameters
 */
jmethodID jniGetMethodID(JNIEnv* env, jclass c_obj, const char* cmethod, const char* cparam) {
	jmethodID j_mid = env->GetMethodID (c_obj, cmethod, cparam);
	if (j_mid == NULL) {
		char buf[256];
		sprintf(buf, "Undefined method id:%s %s", cmethod, cparam);
		ErrorLog(buf);
		jniExceptionThrow(env, "java/lang/NoSuchMethodException", cmethod);
		return NULL;
	}
	return j_mid;
}

/*
 * Method:	Java_jp_co_anaheim_1eng_camellia_CamelliaLib_init
 * Param :	JNIEnv *env					JNI
 *			jobject thiz				JNI
 */
JNIEXPORT void JNICALL Java_jp_co_anaheim_1eng_camellia_CamelliaLib_init
  (JNIEnv *env, jobject thiz)
{
	jclass al_class = jniFindClass(env, "java/util/ArrayList");
	if (al_class == NULL) { return; }

	al_add_MID = jniGetMethodID(env, al_class, "add", "(Ljava/lang/Object;)Z");
	if (al_add_MID == NULL) { return; }

	al_get_MID = jniGetMethodID(env, al_class, "get", "(I)Ljava/lang/Object;");
	if (al_get_MID == NULL) { return; }

	al_size_MID = jniGetMethodID(env, al_class, "size", "()I");
	if (al_size_MID == NULL) { return; }

}

/*
 * Method:	Java_jp_co_anaheim_1eng_camellia_CamelliaLib_KeyGen
 * Param :	JNIEnv *env					JNI
 *			jobject thiz				JNI
 *			jint keyBitLen				key bit length (input)
 */
JNIEXPORT jint JNICALL Java_jp_co_anaheim_1eng_camellia_CamelliaLib_KeyGen
  (JNIEnv *env, jobject thiz, jint keyBitLen, jbyteArray hashKey)
{
	// Check Input Parameters
	if (CheckKeyBitLength(keyBitLen) != 0) return 1;
	// Convert jbyteArray to jbyte
	jbyte *bArray = env->GetByteArrayElements(hashKey, NULL);

	memset(keyTable, 0, sizeof(keyTable));
	memset(rawKey, 0, sizeof(rawKey));
	memcpy(rawKey, bArray, keyBitLen/8);
	// Release of GetByteArrayElements function
	env->ReleaseByteArrayElements(hashKey, bArray, JNI_ABORT);
	// Call of Camellia_Ekeygen
	Camellia_Ekeygen(keyBitLen, rawKey, keyTable);

#if DEBUG
	Log_Camellia_Ekeygen(keyBitLen, rawKey);
#endif
	return 0;
}

/*
 * Method:	Java_jp_co_anaheim_1eng_camellia_CamelliaLib_Encode
 * Param :	JNIEnv *env					JNI
 *			jobject thiz				JNI
 *			jint keyBitLen				key bit length (input)
 *			jobject PlainTexts			plain text (input array)
 *			jobject Ciphers				cipher text (output array)
 */
JNIEXPORT jint JNICALL Java_jp_co_anaheim_1eng_camellia_CamelliaLib_Encode
  (JNIEnv *env, jobject thiz, jint keyBitLen, jobject PlainText, jobject Ciphers)
{
	unsigned char ct_buf[17];
	unsigned char pt_buf[17];
#if DEBUG
	char dbg[1000];						// Testing in a long string, note the buffer
#endif									// 1000 : Maximized of about 330 character.

	// Get jobjectArray array numbers
	jint iLen = (jint)env->CallIntMethod(PlainText, al_size_MID);

	for (int i=0; i<iLen; i++) {
		// Call of ArrayList has get method
		jobject jPTobj = env->CallObjectMethod(PlainText, al_get_MID, i);
		const char *src  = env->GetStringUTFChars((jstring)jPTobj, NULL);
		jsize cSize = env->GetStringUTFLength((jstring)jPTobj);
#if DEBUG
		sprintf(dbg, "src:");
		DebugChar2Hex((unsigned char*)src, cSize, dbg);
		DebugInfoLog(dbg);
#endif
		// Create byteArray
		jbyteArray dstj = env->NewByteArray((cSize%16) == 0 ? cSize : ((int)(cSize/16)+1)*16);
		if (dstj == NULL) {
			env->ReleaseStringUTFChars((jstring)jPTobj, src);
			return -1;
		}
		jbyte* dst = env->GetByteArrayElements(dstj, NULL);
		if (dst == NULL) {
			env->ReleaseStringUTFChars((jstring)jPTobj, src);
			return -1;
		}

		// Create to Ciphers text area
		for (int j=0; j<cSize; j+=16) {
			memset(pt_buf, 0, sizeof(pt_buf));
			memset(ct_buf, 0, sizeof(ct_buf));
			memcpy(pt_buf, &src[j], ((j+16 < cSize) ? 16 : cSize-j));
			Camellia_EncryptBlock(keyBitLen, pt_buf, keyTable, ct_buf);
			for (int k=0; k<16; k++) {
				dst[j+k] = ct_buf[k];
			}
#if DEBUG
			Log_Camellia_EncryptBlock(pt_buf, ct_buf);
#endif
		}
#if DEBUG
		sprintf(dbg, "dst:");
		DebugChar2Hex((unsigned char*)dst, ((cSize%16) == 0 ? cSize : ((int)(cSize/16)+1)*16), dbg);
		DebugInfoLog(dbg);
#endif
		jboolean jbool = env->CallBooleanMethod (Ciphers, al_add_MID, dstj);
		env->ReleaseStringUTFChars((jstring)jPTobj, src);
	}
	return 0;
}

/*
 * Method:	Java_jp_co_anaheim_1eng_camellia_CamelliaLib_Decode
 * Param :	JNIEnv *env					JNI
 *			jobject thiz				JNI
 *			jint keyBitLen				key bit length (input)
 *			jobject Ciphers				cipher text (input array)
 *			jobject PlainTexts			plain text (output array)
 */
JNIEXPORT jint JNICALL Java_jp_co_anaheim_1eng_camellia_CamelliaLib_Decode
  (JNIEnv *env, jobject thiz, jint keyBitLen, jobject Ciphers, jobject PlainText)
{
	unsigned char ct_buf[17];
	unsigned char pt_buf[17];
#if DEBUG
	char dbg[1000];						// Testing in a long string, note the buffer
#endif									// 1000 : Maximized of about 330 character.

	// Get jobjectArray array numbers
	jint iLen = (jint)env->CallIntMethod(Ciphers, al_size_MID);

	for (int i=0; i<iLen; i++) {
		// Call of ArrayList has get method
		jbyteArray srcj = (jbyteArray)env->CallObjectMethod(Ciphers, al_get_MID, i);
		jbyte* src = env->GetByteArrayElements(srcj, NULL);
		if (src == NULL) {
			ErrorLog("Cannot be get byte array elements.");
			return -1;
		}
		int cSize = env->GetArrayLength(srcj);
#if DEBUG
		sprintf(dbg, "src:");
		DebugChar2Hex((unsigned char*)src, cSize, dbg);
		DebugInfoLog(dbg);
#endif
		// Create plain text of char
		char *dst = new char[cSize+1];
		memset(dst, 0, cSize+1);

		// Decode to Plain text area
		for (int j=0; j<cSize; j+=16) {
			memset(pt_buf, 0, sizeof(pt_buf));
			memset(ct_buf, 0, sizeof(ct_buf));
			memcpy(ct_buf, &src[j], ((j+16 < cSize) ? 16 : cSize-j));
			Camellia_DecryptBlock(keyBitLen, ct_buf, keyTable, pt_buf);
			for (int k=0; k<strlen((const char*)pt_buf); k++) {
				dst[j+k] = pt_buf[k];
			}
#if DEBUG
			Log_Camellia_DecryptBlock(pt_buf, ct_buf);
#endif
		}
#if DEBUG
		sprintf(dbg, "dst:");
		DebugChar2Hex((unsigned char*)dst, cSize, dbg);
		DebugInfoLog(dbg);
#endif
		// Convert to char to string
		jstring jsDst = env->NewStringUTF(dst);
		jboolean jbool = env->CallBooleanMethod (PlainText, al_add_MID, jsDst);
		delete dst;
	}
	return 0;
}

