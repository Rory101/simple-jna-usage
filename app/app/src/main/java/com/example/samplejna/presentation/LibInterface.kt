package com.example.samplejna.presentation

import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.NativeLibrary
import com.sun.jna.Pointer
import com.sun.jna.Structure
import java.util.Arrays

open class answer_t : Structure {
    @JvmField public var answer: Int = 0

    constructor() : super() {}

    override fun getFieldOrder(): MutableList<String>? {
        return Arrays.asList(
            "answer"
        )
    }

    constructor(answer: Int) : super() {
        this.answer = answer
    }

    constructor(peer: Pointer?, answer: Int) : super(peer) {
        this.answer = answer
    }

    class ByReference : answer_t(), Structure.ByReference
    class ByValue : answer_t(), Structure.ByValue
}

interface LibInterface: Library {


    interface callback_t : Callback {
        fun invoke(
            ptr_context: Pointer?,
            answer: answer_t?,
        )
    }

    fun add(a: Int, b: Int, ans: Pointer?): Int

    fun registerCallback(ptr_context: Pointer?, callbackFunc: callback_t?)

    companion object {
        const val JNA_LIBRARY_NAME = "foo"
        val JNA_NATIVE_LIB = NativeLibrary.getInstance(JNA_LIBRARY_NAME)
        val INSTANCE = Native.load(JNA_LIBRARY_NAME, LibInterface::class.java) as LibInterface
    }
}

//open class answer_t : Structure() {
//    var elo_mon_num = 0
//    var x = 0
//    var y = 0
//    var width = 0
//    var height = 0
//    var orientation = 0
//    var is_primary: Byte = 0
//
//    override fun getFieldOrder(): MutableList<String>? {
//        return mutableListOf(
//            "elo_mon_num",
//            "x",
//            "y",
//            "width",
//            "height",
//            "orientation",
//            "is_primary"
//        )
//    }
//
//
//    class ByReference : answer_t(), Structure.ByReference
//    class ByValue : answer_t(), Structure.ByValue
//}
