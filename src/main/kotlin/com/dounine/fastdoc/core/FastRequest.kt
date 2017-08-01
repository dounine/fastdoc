package com.dounine.fastdoc.core

import java.lang.reflect.Method
import kotlin.reflect.KFunction

interface FastRequest {

    fun prefixUrl(url:String):FastRequest

    fun url(url: String,vararg args: Any): FastRequest

    fun method(method: FastRequestMethod): FastRequest

    fun params(argv: Array<Unit>): FastRequest

    fun headers(argv: Array<FastHeader>): FastRequest

    fun doResponse():FastResponse

}