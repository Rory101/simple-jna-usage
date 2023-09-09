package com.example.samplejna.presentation

import android.util.Log
import com.sun.jna.Pointer

class CallbackImpl: LibInterface.callback_t {
    private var ans: Int = 0
    override fun invoke(ptr_context: Pointer?, answer: answer_t?) {
        Log.d("Callback", "$answer")
        if (answer != null) {
            Log.d("Callback", "${answer.answer}")
            ans = answer.answer
        }
    }

    fun getAnswer(): Int {
        Log.d("Callback", "Callback ans = $ans")
        return ans
    }
}