LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := ndkstatic
LOCAL_SRC_FILES := ndkdemotest.c
include $(BUILD_SHARED_LIBRARY)