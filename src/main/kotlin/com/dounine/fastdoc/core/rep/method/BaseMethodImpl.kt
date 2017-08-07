package com.dounine.fastdoc.core.rep.method

import com.dounine.fastdoc.core.*
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.methods.*
import org.apache.http.client.protocol.HttpClientContext
import org.apache.http.client.utils.URLEncodedUtils
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.cookie.BasicClientCookie2
import org.apache.http.message.BasicHeader
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import java.net.URI


open class BaseMethodImpl : BaseMethod {

    protected var parameters:List<Parameter> = ArrayList()
    protected var headers:List<Header> = ArrayList()
    protected var cookies:List<Cookie> = ArrayList()
    protected var httpContext:HttpClientContext = HttpClientContext()

    companion object {

    }

    protected val request:FastRequest
    protected val method:FastRequestMethod
    protected lateinit var url:String

    constructor(request: FastRequest,method:FastRequestMethod){
        this.request =request
        this.method = method
    }

    override fun getOverMethod(): FastRequestMethod? {
        return null
    }

    override fun methodVars(): MethodVars {
        if(getOverMethod()==null){
            return MethodVars(method = method,
                    url = url)
        }else{
            var me:FastRequestMethod? = getOverMethod()
            me?.let {
                return MethodVars(method = me,url = url)
            }
            return MethodVars(method = method,url = url)
        }
    }

    override fun addCookie(vararg cookies: Cookie) :BaseMethod{
        if(cookies.size>0){
            var ds:ArrayList<Cookie> = ArrayList(this.cookies)
            for(cookie in cookies){
                ds.add(cookie)
            }
            this.cookies = ds
        }
        return this
    }

    override fun addParameter(vararg parameters: Parameter) :BaseMethod{
        if(parameters.size>0){
            var ds:ArrayList<Parameter> = ArrayList(this.parameters)
            for(d in parameters){
                ds.add(d)
            }
            this.parameters = ds
        }
        return this
    }

    override fun addHeader(vararg headers: Header) :BaseMethod{
        if(headers.size>0){
            var ds:ArrayList<Header> = ArrayList(this.headers)
            for(d in headers){
                ds.add(d)
            }
            this.headers = ds
        }
        return this
    }

    fun fillParameter(url:String):String{
        if(this.parameters.size>0){
            var uri:URI = URI(url)
            var sb:StringBuilder = StringBuilder(uri.toString())
            var _ps:List<Parameter> = ArrayList(this.parameters)
            if(null==uri.query){
                sb.append("?${_ps.get(0).name}=${_ps.get(0).value}")
                _ps = _ps.subList(1,_ps.size)
            }
            for(p in _ps){
                sb.append("&${p.name}=${p.value}")
            }
            return sb.toString()
        }
        return url
    }

    fun fillHeader(httpRequest:HttpRequestBase){
        if(this.headers.size>0){
            for(header in headers){
                httpRequest.setHeader(BasicHeader(header.name,header.value.toString()))
            }
        }
    }

    fun fillCookie(httpContext:HttpClientContext){
        if(this.cookies.size>0){
            var nameCookie:BasicCookieStore
            if(null!=httpContext.cookieStore){
                nameCookie = httpContext.cookieStore as BasicCookieStore
            }else{
                nameCookie = BasicCookieStore()
            }
            for(d in cookies){
                var bcc:BasicClientCookie2 = BasicClientCookie2(d.name,d.value.toString())
                bcc.domain = d.domain
                nameCookie.addCookie(bcc)
            }
            if(null==httpContext.cookieStore){
                httpContext.cookieStore = nameCookie
            }
        }
    }

    override fun doResponse():FastResponse {
        var httpRequest: HttpRequestBase
        var urlPath = this.request.getPrefixUrl() + this.request.getReplaceUrl()
        url = fillParameter(urlPath)

        if (this.method.equals(FastRequestMethod.GET)) {
            httpRequest = HttpGet(url)
        } else if (this.method.equals(FastRequestMethod.DELETE)) {
            httpRequest = HttpDelete(url)
        } else{
            httpRequest = HttpOptions(url)
        }

        fillHeader(httpRequest)
        fillCookie(this.httpContext)

        var response: HttpResponse = FastRequestImpl.HTTP_CLIENT.execute(httpRequest,httpContext)

        var entity: HttpEntity = response.entity

        var body: String = EntityUtils.toString(entity)
        var status: Int = response.statusLine.statusCode
        var length: Long = response.entity.contentLength

        return FastResponseImpl(body, status, length, response, this.request)
    }
}