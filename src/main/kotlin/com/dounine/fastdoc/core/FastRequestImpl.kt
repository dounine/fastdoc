package com.dounine.fastdoc.core

import com.dounine.fastdoc.core.postman.Header
import com.dounine.fastdoc.core.rep.method.*
import com.dounine.fastdoc.core.req.PostData
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.config.CookieSpecs
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.*
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class FastRequestImpl : FastRequest {


    private var prefixUrl: String? = null
    private lateinit var url: String
    private lateinit var replaceUrl: String
    private lateinit var headers: Array<Header>
    private var data: List<PostData> = ArrayList()
    private lateinit var baseMethod:BaseMethod

    companion object {
        val COOKIE_REQUEST_CONFIG = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .build()
        val VAL_PATTERN: Pattern = Pattern.compile("[{][a-zA-Z0-9_$]+[}]")
        val HTTP_CLIENT: HttpClient = HttpClients.custom()
                .setDefaultRequestConfig(COOKIE_REQUEST_CONFIG)
                .build()
    }

    override fun prefixUrl(url: String): FastRequest {
        prefixUrl = url
        return this
    }

    override fun getData(): List<PostData>? {
        return this.data
    }

    override fun dataPush(dataObj: PostData): FastRequest {
        var cc:ArrayList<PostData> =  ArrayList(data)
        cc.add(dataObj)
        this.data = cc
        return this
    }

    override fun data(data: List<PostData>): FastRequest {
        this.data = data
        return this
    }

    override fun url(url: String, args: List<UrlParameter>): FastRequest {
        this.url = url
        this.replaceUrl = url
        var match: Matcher = VAL_PATTERN.matcher(url)
        var listStr = ArrayList<String>()

        while (match.find()) {
            listStr.add(match.group())
        }

        var argsSize: Int = args.size
        var listSize: Int = listStr.size

        if (argsSize > 0) {
            if (listSize != argsSize) {
                throw RuntimeException("参数列表不匹配,${listSize}预参,${argsSize}实参")
            }
            var matchCount: Int = 0
            for (i in 0..listSize - 1) {
                var ov: Optional<UrlParameter> = args.stream().filter({ a -> "{${a.name}}".equals(listStr.get(i)) }).findFirst()
                if (ov.isPresent) {
                    this.replaceUrl = this.replaceUrl.replace(listStr.get(i), ov.get().value.toString())
                    matchCount++
                }
            }
            if (listSize != matchCount) {
                throw RuntimeException("参数列表不匹配,${listSize}预参,${matchCount}匹配实参")
            }
        }
        return this
    }

    override fun url(url: String, vararg args: String): FastRequest {
        this.url = url
        this.replaceUrl = url
        var match: Matcher = VAL_PATTERN.matcher(url)
        var listStr = ArrayList<String>()

        while (match.find()) {
            listStr.add(match.group())
        }

        var argsSize: Int = args.size
        var listSize: Int = listStr.size

        if (argsSize > 0) {
            if (listSize != argsSize) {
                throw RuntimeException("参数列表不匹配,${listSize}预参,${argsSize}实参")
            }
            for (i in 0..listSize - 1) {
                this.replaceUrl = this.replaceUrl.replace(listStr.get(i), args[i])
            }
        }
        return this
    }

    override fun headers(argv: Array<Header>): FastRequest {
        this.headers = argv
        return this
    }

    override fun getPrefixUrl(): String? {
        if(this.prefixUrl==null){
            return FastDocImpl.getPrefixUrl()
        }
        return this.prefixUrl
    }

    override fun getUrl(): String {
        return this.url
    }

    override fun getReplaceUrl(): String {
        return this.replaceUrl
    }

    override fun getHeaders(): Array<Header> {
        return this.headers
    }

    override fun GET(): IGetMethod {
        var method = GetMethod(this)
        this.baseMethod = method
        return method
    }

    override fun POST(): IPostMethod {
        var method = PostMethod(this)
        this.baseMethod = method
        return method
    }

    override fun PATCH(): IPatchMethod {
        var method = PatchMethod(this)
        this.baseMethod = method
        return method
    }

    override fun PUT(): IPutMethod {
        var method = PutMethod(this)
        this.baseMethod = method
        return method
    }

    override fun DELETE(): IDeleteMethod {
        var method = DeleteMethod(this)
        this.baseMethod = method
        return method
    }

    override fun OPTIONS(): IOptionsMethod {
        var method = OptionsMethod(this)
        this.baseMethod = method
        return method
    }

    override fun getMethodVars(): MethodVars = this.baseMethod.methodVars()
}