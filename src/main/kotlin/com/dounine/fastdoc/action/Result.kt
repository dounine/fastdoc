package com.dounine.fastdoc.action

import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

interface Result<T> {

    fun getCode():Int

    fun getData():T

    fun getMsg():String

}