//
// Created by 周彦斌 on 16/5/28.
//

#ifndef JNIBLUR_JNIBLUR_H
#define JNIBLUR_JNIBLUR_H

#include <jni.h>
#include <android/bitmap.h>
#include <android/log.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

#define BLUR_RESULT_SUCCESS 0;
#define BLUR_RESULT_ERROR -1;

#define LOG_TAG "libjniblur"
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)

int do_fast_blur(int *, int, int, int);

int _max(int, int);

int _min(int, int);

double now_time();

#endif
