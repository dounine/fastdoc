package com.dounine.fastdoc.core.rep.method

import com.dounine.fastdoc.core.FastResponse

interface BaseMethod {

    fun addParameter(vararg parameters: Parameter): BaseMethod

    fun addHeader(vararg headers: Header): BaseMethod

    fun addCookie(vararg cookies: Cookie): BaseMethod

    fun methodVars():MethodVars

    fun doResponse(): FastResponse
}