LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE     := TestVector
LOCAL_SRC_FILES  := testvector.c

LOCAL_STATIC_LIBRARIES := libcamellia
LOCAL_LDLIBS     := -llog
LOCAL_CFLAGS     :=

include $(BUILD_SHARED_LIBRARY)
