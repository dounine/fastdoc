package com.dounine.fastdoc.core

import com.dounine.fastdoc.core.rep.BodyCallback
import com.dounine.fastdoc.core.rep.HeaderCallback
import com.dounine.fastdoc.core.rep.LengthCallback
import com.dounine.fastdoc.core.rep.StatusCallback
import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import java.io.InputStream

interface FastResponse {

    fun status(result:StatusCallback):FastResponse

    fun getStatus():Int

    fun length(result:LengthCallback):FastResponse

    fun getLength():Long

    fun body(result:BodyCallback):FastResponse

    fun getBody():String

    fun headers(result:HeaderCallback):FastResponse

    fun getResponse(): HttpResponse

    fun getInputStream():InputStream?

    fun restDoc():RestDoc

}