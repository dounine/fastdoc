package com.dounine.fastdoc.core

import com.alibaba.fastjson.JSON
import com.dounine.fastdoc.core.doc.LinkCallback
import com.dounine.fastdoc.core.postman.*
import com.dounine.fastdoc.core.rep.SubsectionPath
import com.dounine.fastdoc.core.rep.SubsectionPathImpl
import com.dounine.fastdoc.core.req.PostData
import org.apache.commons.lang3.StringUtils
import org.apache.http.NameValuePair
import org.apache.http.client.utils.URLEncodedUtils
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.ArrayList

class RestDocImpl : RestDoc {

    private val response:FastResponseImpl
    private lateinit var name:String
    private var subsections:ArrayList<SubsectionPath> = ArrayList<SubsectionPath>()

    constructor(response: FastResponseImpl){
        this.response = response
    }

    override fun create(): RestDoc {
        var request:FastRequestImpl = response.getRequest()
        println(name)
        println("url请求 "+request.getUrl())
        println("参数 "+JSON.toJSONString(subsections))
        println("Example http request")
        println(request.getMethodVars().method.name+" "+request.getReplaceUrl())
        println("Host: "+request.getPrefixUrl())
        println("Example response")
        println(response.getResponse().statusLine)
//        println("Content-Type: "+JSON.toJSONString(response.getResponse().getFirstHeader("Content-Type").value))
        println("Content-Length: "+response.getLength())
        println(response.getBody())

        println("postman配置")


        var postMan:PostManImpl = PostManImpl()
        var info:InfoImpl = InfoImpl()
        info.setName(FastDocImpl.getAppName())
        postMan.setInfo(info)
        var item:ItemGroupImpl = ItemGroupImpl()
        item.setName(FastDocImpl.getGroupName())

        var listItem:ItemImpl = ItemImpl()
        listItem.setName(name)

        var pmRequest:RequestImpl = RequestImpl()
        var url:URL = URL(request.getMethodVars().url)
        if(null!=url.query){
            var params:List<NameValuePair> = URLEncodedUtils.parse(url.query,StandardCharsets.UTF_8)
            var querys:ArrayList<QueryImpl> = ArrayList(params.size)
            for(p in params){
                querys.add(QueryImpl(key = p.name,value = p.value,description = "",disabled = null))
            }
            var pmUrl:UrlImpl = UrlImpl(raw = url.toString(),
                    host = Arrays.asList(url.host),
                    port = url.port,
                    protocol = url.protocol,
                    path = url.path.split("/"),
                    query = querys
                    )
            pmRequest.setUrl(pmUrl)
        }else{
            pmRequest.setUrl(url.toString())
        }
        pmRequest.setMethod(response.getRequest().getMethodVars().method)

        if(request.getMethodVars().method.equals(FastRequestMethod.POST)||
                request.getMethodVars().method.equals(FastRequestMethod.PUT)||
                request.getMethodVars().method.equals(FastRequestMethod.PATCH)){
            var pmBody:BodyImpl = BodyImpl()
            pmBody.setMode(BodyMode.formdata)
            var formdatas:ArrayList<FormData> = pmBody.getFormdata() as ArrayList<FormData>
            var data:List<PostData>? = response.getRequest().getData()
            if(null!=data){
                for(pd in data){
                    formdatas.add(FormDataImpl(key = pd.getName(),
                            value = pd.getValue().toString(),
                            src = null,
                            type = FormDataType.text,
                            description = "",
                            disabled = null
                            ))
                }
            }
            if(data!!.size>0){
                pmRequest.setBody(pmBody)
            }
        }



        listItem.setRequest( pmRequest)



        item.setItem(Arrays.asList(listItem))

        postMan.setItem(Arrays.asList(item))

        println(JSON.toJSONString(postMan,true))

        return this
    }

    override fun subsectionPath(sp: SubsectionPath): RestDoc {
        var sub:SubsectionPathImpl = sp as SubsectionPathImpl
        if(StringUtils.isNotBlank(sub.getPathName())){
            subsections.add(sp)
        }
        return this
    }

    override fun links(links: LinkCallback): RestDoc {
        return this
    }

    override fun name(name: String): RestDoc {
        this.name = name
        return this
    }
}