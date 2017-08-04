package com.dounine.fastdoc.core

import com.dounine.fastdoc.core.postman.Header
import com.dounine.fastdoc.core.req.PostData

interface FastRequest {

    fun prefixUrl(url:String):FastRequest

    fun getPrefixUrl():String

    fun url(url: String,vararg args: String): FastRequest

    fun getUrl():String

    fun url(url: String,args: List<UrlParameter>): FastRequest

    fun method(method: FastRequestMethod): FastRequest

    fun getMethod():FastRequestMethod

    fun headers(argv: Array<Header>): FastRequest

    fun getHeaders():Array<Header>

    fun doResponse():FastResponse

    fun getData():List<PostData>?

    fun data(data:List<PostData>): FastRequest

    fun dataPush(data:PostData): FastRequest

}