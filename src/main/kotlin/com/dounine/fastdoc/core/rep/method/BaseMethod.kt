package com.dounine.fastdoc.core.rep.method

import com.dounine.fastdoc.core.FastResponse

interface BaseMethod {

    fun addParameter(vararg parameters:Parameter)

    fun addHeader(vararg headers:Header)

    fun doResponse():FastResponse
}