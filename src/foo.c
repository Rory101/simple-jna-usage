#include "foo.h"
#include <android/log.h>


typedef enum ret_t {
    RET_OK   = 0,
    RET_FAIL = 1
} ret_t;

static void* ptr_localContext = NULL;
static callback_t localCallback = NULL;

ret_t add(int a, int b, int *ans)
{
    __android_log_print(ANDROID_LOG_INFO, "foo.c", "add(%d, %d)", a, b);
    ret_t ret = RET_OK;

    *ans = a + b;
    __android_log_print(ANDROID_LOG_INFO, "foo.c", "add(%d, %d) = %d", a, b, *ans);

    if (localCallback != NULL) {
        answer_t ansStruct = {
            .answer = *ans,
        };
        localCallback(ptr_localContext, &ansStruct);
    }
    else {
        __android_log_print(ANDROID_LOG_INFO, "foo.c", "No callback registered");
    }

    return ret;
}


void registerCallback(void* ptr_context, callback_t callbackFunc)
{
    ptr_localContext = ptr_context;
    localCallback = callbackFunc;
}

int main()
{
    int a = 5;
    int b = 225;
    int ans = 0;
    add(a, b, &ans);
    printf("%d + %d = %d\n", a, b, ans);
    return 0;
}


// [X] - Function call
// [X] - Function pointer
// [X] - Function with pointer parameters
// [X] - Struct and struct pointer
// [X] - Add Android logging
