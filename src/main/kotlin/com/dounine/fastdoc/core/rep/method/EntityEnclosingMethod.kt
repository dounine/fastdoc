package com.dounine.fastdoc.core.rep.method

import com.dounine.fastdoc.core.*
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.*
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import java.util.*
import kotlin.collections.ArrayList

open class EntityEnclosingMethod(request: FastRequest,method: FastRequestMethod) : BaseMethodImpl(request,method), IEntityEnclosingMethod {

    protected var data:List<Data> = ArrayList()

    override fun addData(vararg datas: Data):IEntityEnclosingMethod {
        if(datas.size>0){
            var ds:ArrayList<Data> = ArrayList(data)
            for(d in datas){
                ds.add(d)
            }
            data = ds
        }
        return this
    }

    override fun doResponse():FastResponse {
        var urlPath = this.request.getPrefixUrl() + this.request.getReplaceUrl()
        url = fillParameter(urlPath)

        var httpRequest: HttpEntityEnclosingRequestBase
        if(this.method.equals(FastRequestMethod.POST)){
            httpRequest = HttpPost(url)
        }else if(this.method.equals(FastRequestMethod.PUT)){
            httpRequest = HttpPut(url)
        }else{
            httpRequest = HttpPatch(url)
        }
        if (data.size > 0) {
            val params: ArrayList<BasicNameValuePair> = ArrayList(data.size)
            for (pd in data) {
                params.add(BasicNameValuePair(pd.name, pd.value.toString()))
            }
            httpRequest.entity = UrlEncodedFormEntity(params)
        }

        fillHeader(httpRequest)
        fillCookie(this.httpContext)

        var response:HttpResponse = FastRequestImpl.HTTP_CLIENT.execute(httpRequest,httpContext)

        var entity: HttpEntity = response.entity

        var body: String = EntityUtils.toString(entity)
        var status: Int = response.statusLine.statusCode
        var length: Long = response.entity.contentLength

        return FastResponseImpl(body, status, length, response, this.request)

    }
}