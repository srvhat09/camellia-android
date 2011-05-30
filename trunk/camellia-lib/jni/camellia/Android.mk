LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE     := libcamellia
LOCAL_SRC_FILES  := camellia.c

LOCAL_C_INCLUDES := $(JNI_H_INCLUDE)

include $(BUILD_STATIC_LIBRARY)
