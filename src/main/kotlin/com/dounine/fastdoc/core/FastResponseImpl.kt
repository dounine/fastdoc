package com.dounine.fastdoc.core

import com.dounine.fastdoc.core.rep.BodyCallback
import com.dounine.fastdoc.core.rep.HeaderCallback
import com.dounine.fastdoc.core.rep.LengthCallback
import com.dounine.fastdoc.core.rep.StatusCallback
import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import java.io.InputStream

class FastResponseImpl : FastResponse {

    private val body:String
    private val length:Long
    private val status:Int
    private val response:HttpResponse
    private val input:InputStream?
    private val request:FastRequestImpl

    constructor(body:String, status:Int, length:Long, response: HttpResponse, request:FastRequest){
        this.request = request as FastRequestImpl
        this.body = body
        this.length = length
        this.status = status
        this.response = response
        this.input = null
    }

    constructor(input:InputStream, status:Int, length:Long, response: HttpResponse, request:FastRequest){
        this.request = request as FastRequestImpl
        this.body = ""
        this.length = length
        this.status = status
        this.response = response
        this.input = null
    }


    override fun status(result: StatusCallback): FastResponse {
        if(!result.result().equals(this.status)){
            throw FastDocException("status期望为${result.result()},实际为${this.status}")
        }
        return this
    }

    override fun length(result: LengthCallback): FastResponse {
        if(!result.result().equals(this.length)){
            throw FastDocException("length期望为${result.result()},实际为${this.length}")
        }
        return this
    }

    override fun body(result: BodyCallback): FastResponse {
        if(!result.result().equals(this.body)){
            throw FastDocException("body期望为${result.result()},实际为${this.body}")
        }
        return this
    }

    override fun headers(result: HeaderCallback): FastResponse {
        return this
    }

    override fun restDoc(): RestDoc {

        return RestDocImpl(this)
    }

    override fun getStatus(): Int = this.status

    override fun getLength(): Long  = this.length

    override fun getBody(): String = this.body

    override fun getResponse(): HttpResponse = this.response

    override fun getInputStream(): InputStream? = this.input

    fun getRequest():FastRequestImpl = this.request
}