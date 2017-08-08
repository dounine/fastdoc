package com.dounine.fastdoc.core.rep

interface BaseCallback {

    fun condition():Condition {
        return Condition.EQ
    }

    fun jsonExpress():String {
        return ""
    }

}