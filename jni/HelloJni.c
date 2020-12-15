#include "com_opencode_jni_HelloJni.h"

JNIEXPORT char * JNICALL Java_com_opencode_jni_HelloJni_hello
  (JNIEnv *jni_env, jobject jobject){
    printf("Hello Jni~\n");
    char* str = "Delete file [ System_32_SO¡¢SYSTEM_64_SO¡¢SYSTEM_32_DLL¡¢SYSTEM_64¡¢DLL ] O(¡É_¡É)O~";
    return (**jni_env).NewStringUTF(jni_env, str);
 }