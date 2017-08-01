package com.dounine.fastdoc.core

import kotlin.reflect.KFunction

class FastRequestImpl : FastRequest {



    lateinit var prefixUrl:String
    lateinit var url:String
    lateinit var method:FastRequestMethod
    lateinit var params:Array<Unit>
    lateinit var headers:Array<FastHeader>

    override fun prefixUrl(url: String): FastRequest {
        prefixUrl = prefixUrl?:"http://localhost"?:prefixUrl
        return this
    }

    override fun doResponse(): FastResponse {
        return FastResponseImpl()
    }

    override fun url(url: String,vararg args: Any): FastRequest {
        this.url = url
        return this
    }

    override fun method(method: FastRequestMethod): FastRequest {
        this.method = method
        return this
    }

    override fun params(argv: Array<Unit>): FastRequest {
        this.params = argv
        return this
    }

    override fun headers(argv: Array<FastHeader>): FastRequest {
        this.headers = argv
        return this
    }
}