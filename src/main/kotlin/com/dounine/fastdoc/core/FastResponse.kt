package com.dounine.fastdoc.core

import com.dounine.fastdoc.core.rep.BodyCallback
import com.dounine.fastdoc.core.rep.HeaderCallback
import com.dounine.fastdoc.core.rep.LengthCallback
import com.dounine.fastdoc.core.rep.StatusCallback

interface FastResponse {

    fun status(result:StatusCallback):FastResponse

    fun length(result:LengthCallback):FastResponse

    fun body(result:BodyCallback):FastResponse

    fun headers(result:HeaderCallback):FastResponse

    fun restDoc():RestDoc

}