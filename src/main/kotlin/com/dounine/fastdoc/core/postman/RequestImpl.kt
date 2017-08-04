package com.dounine.fastdoc.core.postman

import com.dounine.fastdoc.core.FastRequestMethod

class RequestImpl : Request {

    private lateinit var url:Any
    private var method:FastRequestMethod = FastRequestMethod.GET
    private var header:List<Header> = ArrayList()
    private var body:Body? = null
    private var description:String = ""

    override fun getUrl(): Any = this.url

    fun setUrl(url:Any){
        this.url = url
    }

    override fun getMethod(): FastRequestMethod = this.method

    fun setMethod(method:FastRequestMethod){
        this.method = method
    }

    override fun getHeader(): List<Header> = this.header

    fun setHeader(header:List<Header>){
        this.header = header
    }

    override fun getBody(): Body? = this.body

    fun setBody(body:Body){
        this.body = body
    }

    override fun getDescription(): String = this.description

    fun setDescription(des:String){
        this.description = des
    }
}