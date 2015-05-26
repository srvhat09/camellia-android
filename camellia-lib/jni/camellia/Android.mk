LOCAL_PATH := $(call my-dir)

#LOCAL_CFLAGS += -pie -fPIE
#LOCAL_LDFLAGS += -pie -fPIE

include $(CLEAR_VARS)

LOCAL_MODULE     := libcamellia
LOCAL_SRC_FILES  := camellia.c

LOCAL_C_INCLUDES := $(JNI_H_INCLUDE)

include $(BUILD_STATIC_LIBRARY)
