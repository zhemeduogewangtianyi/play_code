#include "com_opencode_jni_HelloJni.h"

JNIEXPORT char * JNICALL Java_com_opencode_jni_HelloJni_hello
  (JNIEnv *jni_env, jobject jobject){
    printf("Hello Jni~\n");
    char res[] = "I say Hello !";
    return res;
 }