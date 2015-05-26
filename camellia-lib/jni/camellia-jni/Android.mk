LOCAL_PATH := $(call my-dir)

#LOCAL_CFLAGS += -pie -fPIE
#LOCAL_LDFLAGS += -pie -fPIE

include $(CLEAR_VARS)

LOCAL_MODULE    := camellia-jni
LOCAL_SRC_FILES := camelliaWrapper.cpp

LOCAL_STATIC_LIBRARIES := libcamellia
LOCAL_LDLIBS    := -llog


include $(BUILD_SHARED_LIBRARY)
