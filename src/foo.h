#ifndef __FOO_H_
#define __FOO_H_

#include <stdio.h>

typedef struct {
    int answer;
} answer_t;

typedef void (*callback_t) (void* ptr_context, answer_t* answer);



#endif // __FOO_H_