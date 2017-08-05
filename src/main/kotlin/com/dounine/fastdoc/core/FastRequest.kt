package com.dounine.fastdoc.core

import com.dounine.fastdoc.core.postman.Header
import com.dounine.fastdoc.core.rep.method.*
import com.dounine.fastdoc.core.req.PostData

interface FastRequest {

    fun prefixUrl(url:String):FastRequest

    fun getPrefixUrl():String?

    fun getReplaceUrl():String

    fun url(url: String,vararg args: String): FastRequest

    fun getUrl():String

    fun url(url: String,args: List<UrlParameter>): FastRequest

    fun GET():IGetMethod

    fun POST():IPostMethod

    fun FILE():IFileMethod

    fun PATCH():IPatchMethod

    fun PUT():IPutMethod

    fun DELETE():IDeleteMethod

    fun OPTIONS():IOptionsMethod

    fun getMethodVars():MethodVars

    fun headers(argv: Array<Header>): FastRequest

    fun getHeaders():Array<Header>

    fun getData():List<PostData>?

    fun data(data:List<PostData>): FastRequest

    fun dataPush(data:PostData): FastRequest

}