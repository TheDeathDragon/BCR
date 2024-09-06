LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := JzhkCallRecorder
LOCAL_SRC_FILES := JzhkCallRecorder.apk
LOCAL_MODULE_TAGS := optional
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_MODULE_CLASS := APPS
LOCAL_PRIVILEGED_MODULE := true
# LOCAL_SYSTEM_EXT_MODULE := true
LOCAL_DEX_PREOPT := false
LOCAL_CERTIFICATE := platform
LOCAL_REQUIRED_MODULES := \
    privapp_whitelist_la.shiro.call.recorder \
    default-permissions-la.shiro.call.recorder
LOCAL_ENFORCE_USES_LIBRARIES := false
include $(BUILD_PREBUILT)