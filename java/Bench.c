#include <jni.h>
#include "Bench.h"

JNIEXPORT jlong JNICALL Java_Bench_add_1one(JNIEnv *env, jobject this, jlong i){
    return i + 1;
}