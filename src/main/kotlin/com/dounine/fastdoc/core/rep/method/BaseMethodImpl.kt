package com.dounine.fastdoc.core.rep.method

import com.dounine.fastdoc.core.*
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.*
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils

open class BaseMethodImpl : BaseMethod {


    protected var parameters:List<Parameter> = ArrayList()
    protected var headers:List<Header> = ArrayList()


    protected val request:FastRequest
    public val method:FastRequestMethod

    constructor(request: FastRequest,method:FastRequestMethod){
        this.request =request
        this.method = method
    }

    override fun addParameter(vararg parameters: Parameter) {
        if(parameters.size>0){
            var ds:ArrayList<Parameter> = ArrayList(this.parameters)
            for(d in parameters){
                ds.add(d)
            }
            this.parameters = ds
        }
    }

    override fun addHeader(vararg headers: Header) {
        if(headers.size>0){
            var ds:ArrayList<Header> = ArrayList(this.headers)
            for(d in this.headers){
                ds.add(d)
            }
            this.headers = ds
        }
    }

    override fun doResponse():FastResponse {
        var httpRequest: HttpRequestBase? = null;
        var urlPath = this.request.getPrefixUrl() + this.request.getReplaceUrl()
        if (this.method.equals(FastRequestMethod.GET)) {
            httpRequest = HttpGet(urlPath)
        } else if (this.method.equals(FastRequestMethod.DELETE)) {
            httpRequest = HttpDelete(urlPath)
        } else if (this.method.equals(FastRequestMethod.OPTIONS)) {
            httpRequest = HttpOptions(urlPath)
        }

        var response: HttpResponse = FastRequestImpl.HTTP_CLIENT.execute(httpRequest)

        var entity: HttpEntity = response.entity

        var body: String = EntityUtils.toString(entity)
        var status: Int = response.statusLine.statusCode
        var length: Long = response.entity.contentLength

        return FastResponseImpl(body, status, length, response, this.request)
    }
}