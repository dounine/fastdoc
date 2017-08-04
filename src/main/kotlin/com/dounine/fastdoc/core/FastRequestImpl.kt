package com.dounine.fastdoc.core

import com.dounine.fastdoc.core.postman.Header
import com.dounine.fastdoc.core.req.PostData
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
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


    private lateinit var prefixUrl: String
    private lateinit var url: String
    private lateinit var replaceUrl: String
    private lateinit var method: FastRequestMethod
    private lateinit var headers: Array<Header>
    private var data: List<PostData> = ArrayList()

    companion object {
        val VAL_PATTERN: Pattern = Pattern.compile("[{][a-zA-Z0-9_]+[}]")
        val HTTP_CLIENT: HttpClient = HttpClients.createDefault()
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

    override fun doResponse(): FastResponse {
        var request: HttpUriRequest? = null;
        var urlPath = this.prefixUrl + this.replaceUrl;
        if (method.equals(FastRequestMethod.GET)) {
            request = HttpGet(urlPath)
        } else if (method.equals(FastRequestMethod.PUT)) {
            request = HttpPut(urlPath)
        } else if (method.equals(FastRequestMethod.DELETE)) {
            request = HttpDelete(urlPath)
        } else if (method.equals(FastRequestMethod.PATCH)) {
            request = HttpPatch(urlPath)
        } else if (method.equals(FastRequestMethod.OPTIONS)) {
            request = HttpOptions(urlPath)
        }

        var response: HttpResponse? = null;

        if (method.equals(FastRequestMethod.POST) && data.size > 0) {
            val params: ArrayList<BasicNameValuePair> = ArrayList()
            for (pd in data) {
                params.add(BasicNameValuePair(pd.getName(), pd.getValue().toString()))
            }
            var postRequest: HttpPost = HttpPost(urlPath)
            postRequest.entity = UrlEncodedFormEntity(params)
            response = HTTP_CLIENT.execute(postRequest)
        } else {
            response = HTTP_CLIENT.execute(request)
        }

        var entity: HttpEntity = response.entity

        var body: String = EntityUtils.toString(entity)
        var status: Int = response.statusLine.statusCode
        var length: Long = response.entity.contentLength
        var fastResponse: FastResponseImpl = FastResponseImpl(body, status, length, response, this)
        return fastResponse
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
                    this.replaceUrl = this.replaceUrl.replace(listStr.get(i), ov.get().value)
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

    override fun method(method: FastRequestMethod): FastRequest {
        this.method = method
        return this
    }

    override fun headers(argv: Array<Header>): FastRequest {
        this.headers = argv
        return this
    }

    override fun getPrefixUrl(): String {
        return this.prefixUrl
    }

    override fun getUrl(): String {
        return this.url
    }

    fun getReplaceUrl(): String {
        return this.replaceUrl
    }

    override fun getMethod(): FastRequestMethod {
        return this.method
    }

    override fun getHeaders(): Array<Header> {
        return this.headers
    }
}