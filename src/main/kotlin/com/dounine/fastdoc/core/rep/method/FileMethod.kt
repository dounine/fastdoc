package com.dounine.fastdoc.core.rep.method

import com.dounine.fastdoc.core.*
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.util.EntityUtils
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.URL
import java.nio.charset.Charset

class FileMethod(request: FastRequest) : EntityEnclosingMethod(request,FastRequestMethod.POST), IFileMethod {
    protected var fileDatas:List<FileData> = ArrayList()

    override fun addFileData(vararg datas: FileData): IFileMethod {
        if(datas.size>0){
            var ds:ArrayList<FileData> = ArrayList(fileDatas)
            for(d in datas){
                ds.add(d)
            }
            fileDatas = ds
        }
        return this
    }

    override fun doResponse(): FastResponse {
        var urlPath = this.request.getPrefixUrl() + this.request.getReplaceUrl()
        url = fillParameter(urlPath)

        var httpRequest: HttpPost = HttpPost(url)

        var multipartEntity: MultipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntity.setCharset(Charset.forName("utf-8"));
        multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (data.size > 0) {
            for (d in data) {//普通data
                multipartEntity.addPart(d.name, StringBody(d.value.toString(), ContentType.APPLICATION_JSON))
            }
        }
        var _tfiles:ArrayList<File> = ArrayList()
        if(fileDatas.size>0){
            for (d in fileDatas) {//普通data
                if(null!=d.getSrc()){//download network src file
                    var filePath:String = FileUtils.getTempDirectoryPath()+File.separator+FilenameUtils.getBaseName(d.getSrc())+"."+FilenameUtils.getExtension(d.getSrc())
                    var _tfile = File(filePath)
                    _tfiles.add(_tfile)
                    FileUtils.copyURLToFile(URL(d.getSrc()),_tfile,FastDocImpl.getTimeout(),FastDocImpl.getTimeout())
                    multipartEntity.addBinaryBody(d.getName(), _tfile);//文件
                }else{
                    multipartEntity.addBinaryBody(d.getName(), d.getFile());//文件
                }
            }
        }
        httpRequest.setEntity(multipartEntity.build());
        fillHeader(httpRequest)
        fillCookie(this.httpContext)

        var response: HttpResponse = FastRequestImpl.HTTP_CLIENT.execute(httpRequest,httpContext)
        for(_file in _tfiles){
            _file.delete()
        }
        var entity: HttpEntity = response.entity

        var body: String = EntityUtils.toString(entity)
        var status: Int = response.statusLine.statusCode
        var length: Long = response.entity.contentLength

        return FastResponseImpl(body, status, length, response, this.request)

    }
}