package com.dounine.fastdoc.core

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.dounine.fastdoc.core.rep.*
import com.dounine.fastdoc.core.rep.json.Expr
import com.dounine.fastdoc.core.rep.json.JFType
import com.dounine.fastdoc.core.rep.json.JsonField
import org.apache.http.HttpResponse
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

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

    fun expressStr(expressions:List<Expr>,responseStr:String,parentJsonFields:StringBuilder):String{
        var expr:Expr = expressions.get(0)
        if(""==responseStr){
            throw FastDocException("JsonField [ ${parentJsonFields.toString()}.${expr.value} ]键不存在")
        }
        if(expr.type.equals(JFType.ARRAY)){
            var jo:JSONObject = JSONObject.parseObject(responseStr)
            if(!jo.containsKey(expr.value.toString())){
                throw FastDocException("JsonField [ ${parentJsonFields.toString()} ]键不存在")
            }
            var jos = JSONObject.parseArray(jo.getString(expr.value.toString()))
            var exprs:List<Expr> = expressions.subList(1,expressions.size)
            var exprStr:String = jos.toString()
            if(exprs.size>0){
                if(parentJsonFields.length>0){
                    parentJsonFields.append(".")
                }
                parentJsonFields.append(expr.value.toString())
                return expressStr(exprs,exprStr,parentJsonFields)
            }else{
                return exprStr
            }
        }else if(expr.type.equals(JFType.ARRAY_SIZE)){
            var jos = JSONObject.parseArray(responseStr)
            return jos.size.toString()
        }else if(expr.type.equals(JFType.STR_LENGTH)){
            var jos = JSONObject.parseObject(responseStr)
            if(!jos.containsKey(expr.value.toString())){
                throw FastDocException("JsonField [ ${parentJsonFields.toString()} ]键不存在")
            }
            return jos.get(expr.value).toString().length.toString()
        }else if(expr.type.equals(JFType.ARRAY_GET)){
            var jos:JSONArray = JSONObject.parseArray(responseStr)
            var getIndex:Int = Integer.parseInt(expr.value.toString())
            if(getIndex>jos.size-1){
                throw FastDocException("JsonField jfArray数组越界,期望${getIndex},实际${jos.size-1}")
            }
            var exprStr:String = jos.get(getIndex).toString()
            var exprs:List<Expr> = expressions.subList(1,expressions.size)
            if(exprs.size>0){
                parentJsonFields.append("[")
                parentJsonFields.append(expr.value.toString())
                parentJsonFields.append("]")
                return expressStr(exprs,exprStr,parentJsonFields)
            }else{
                return exprStr
            }
        }else if(expr.type.equals(JFType.OBJECT)){
            var jo:JSONObject = JSONObject.parseObject(responseStr)
            if(!jo.containsKey(expr.value.toString())){
                throw FastDocException("JsonField [ ${parentJsonFields.toString()} ]键不存在")
            }
            var exprStr:String = jo.getString(expr.value.toString())
            var exprs:List<Expr> = expressions.subList(1,expressions.size)
            if(exprs.size>0){
                if(parentJsonFields.length>0){
                    parentJsonFields.append(".")
                }
                parentJsonFields.append(expr.value.toString())
                return expressStr(exprs,exprStr,parentJsonFields)
            }else{
                return exprStr
            }
        }else if(expr.type.equals(JFType.NAME)){
            var jo:JSONObject = JSONObject.parseObject(responseStr)
            parentJsonFields.append("."+expr.value.toString())
            if(jo.containsKey(expr.value.toString())){
                return jo.getString(expr.value.toString())
            }else{
                throw FastDocException("JsonField [ ${parentJsonFields.toString()} ]键不存在")
            }
        }
        return ""
    }

    override fun body(result: BodyCallback): FastResponse {
        var exprStr:String = ""
        var jf:JsonField = result.jsonField()
        var expressions:List<Expr> = jf.getExpression()
        if(expressions.size==1&&expressions.get(0).type.equals(JFType.NAME)){
            var jo:JSONObject = JSONObject.parseObject(this.body)
            exprStr = jo.getString(expressions.get(0).value.toString())
        }else if(expressions.size>0){
            var parentJsonFields:StringBuilder = StringBuilder()
            exprStr = expressStr(expressions,this.body,parentJsonFields)
        }
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