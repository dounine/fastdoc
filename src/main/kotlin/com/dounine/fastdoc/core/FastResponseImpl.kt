package com.dounine.fastdoc.core

import com.dounine.fastdoc.core.rep.*
import com.dounine.fastdoc.core.rep.json.express.BaseExpressEx
import org.apache.http.HttpResponse
import java.io.InputStream

class FastResponseImpl : FastResponse {

    private val body: String
    private val length: Long
    private val status: Int
    private val response: HttpResponse
    private val input: InputStream?
    private val request: FastRequestImpl

    constructor(body: String, status: Int, length: Long, response: HttpResponse, request: FastRequest) {
        this.request = request as FastRequestImpl
        this.body = body
        this.length = length
        this.status = status
        this.response = response
        this.input = null
    }

    constructor(input: InputStream, status: Int, length: Long, response: HttpResponse, request: FastRequest) {
        this.request = request as FastRequestImpl
        this.body = ""
        this.length = length
        this.status = status
        this.response = response
        this.input = null
    }


    override fun status(result: StatusCallback): FastResponse {
        if (!result.result().equals(this.status)) {
            throw FastDocException("status期望为${result.result()},实际为${this.status}")
        }
        return this
    }

    override fun length(result: LengthCallback): FastResponse {
        if (result.condition().equals(Condition.EQ)) {
            if (!result.result().equals(this.length)) {
                throw FastDocException("length期望=${result.result()},实际${this.length}")
            }
        } else if (result.condition().equals(Condition.GT)) {
            if (!(this.length > result.result())) {
                throw FastDocException("length期望>${result.result()},实际${this.length}")
            }
        } else {
            if (!(this.length < result.result())) {
                throw FastDocException("length期望<${result.result()},实际${this.length}")
            }
        }

        return this
    }

    override fun body(result: BodyCallback): FastResponse {
        var exprStr:String = BaseExpressEx(result.jsonExpress()).expressStr(this.body)
        if (!result.result().toString().equals(exprStr)) {
            throw FastDocException("期望为${result.result()},实际为${exprStr}")
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

    override fun getLength(): Long = this.length

    override fun getBody(): String = this.body

    override fun getResponse(): HttpResponse = this.response

    override fun getInputStream(): InputStream? = this.input

    fun getRequest(): FastRequestImpl = this.request
}