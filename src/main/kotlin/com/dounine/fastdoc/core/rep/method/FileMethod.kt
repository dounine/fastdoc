package com.dounine.fastdoc.core.rep.method

import com.dounine.fastdoc.core.*
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.util.EntityUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.nio.charset.Charset

class FileMethod(request: FastRequest) : EntityEnclosingMethod(request,FastRequestMethod.POST), IFileMethod {


    protected var fileDatas:List<FileData> = ArrayList()
    protected var downloadPath:String = FileUtils.getTempDirectoryPath()
    protected var fileName:String = ""

    override fun download(downloadPath: String): IFileMethod {
        this.downloadPath = downloadPath

        return this
    }

    override fun fileName(fileName:String):IFileMethod{
        this.fileName = fileName

        return this
    }

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

    override fun getOverMethod(): FastRequestMethod? {
        if(""!=downloadPath){
            return FastRequestMethod.GET
        }
        return FastRequestMethod.POST
    }

    override fun doResponse(): FastResponse {
        var urlPath = this.request.getPrefixUrl() + this.request.getReplaceUrl()
        url = fillParameter(urlPath)
        var response: HttpResponse
        var status: Int
        var length: Long
        var body: String = ""
        if(""==downloadPath) {

            var httpRequest: HttpPost = HttpPost(url)

            var multipartEntity: MultipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntity.setCharset(Charset.forName("utf-8"));
            multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (data.size > 0) {
                for (d in data) {//普通data
                    multipartEntity.addPart(d.name, StringBody(d.value.toString(), ContentType.APPLICATION_JSON))
                }
            }
            var _tfiles: ArrayList<File> = ArrayList()
            if (fileDatas.size > 0) {
                for (d in fileDatas) {//普通data
                    if (null != d.getUrl()) {//download network src file
                        var filePath: String = FileUtils.getTempDirectoryPath() + File.separator + FilenameUtils.getBaseName(d.getUrl()) + "." + FilenameUtils.getExtension(d.getUrl())
                        var _tfile = File(filePath)
                        _tfiles.add(_tfile)
                        FileUtils.copyURLToFile(URL(d.getUrl()), _tfile, FastDocImpl.getTimeout(), FastDocImpl.getTimeout())
                        multipartEntity.addBinaryBody(d.getName(), _tfile);//文件
                    } else {
                        multipartEntity.addBinaryBody(d.getName(), d.getFile());//文件
                    }
                }
            }
            httpRequest.setEntity(multipartEntity.build());

            fillHeader(httpRequest)
            fillCookie(this.httpContext)

            response = FastRequestImpl.HTTP_CLIENT.execute(httpRequest,httpContext)
            status = response.statusLine.statusCode
            length = response.entity.contentLength
            for(_file in _tfiles){
                _file.delete()
            }
            var entity: HttpEntity = response.entity

            body = EntityUtils.toString(entity)
        }else{
            var httpGet:HttpGet = HttpGet(url)
            fillHeader(httpGet)
            fillCookie(this.httpContext)
            response = FastRequestImpl.HTTP_CLIENT.execute(httpGet,httpContext)

            fileName = url.substring(url.lastIndexOf('/')+1)
            if(!File(downloadPath).exists()){
                File(downloadPath).mkdirs()
            }
            var fos:FileOutputStream = FileOutputStream(File(downloadPath+File.separator+fileName))

            response.entity.writeTo(fos)
            status = response.statusLine.statusCode
            length = response.entity.contentLength
        }

        return FastResponseImpl(body, status, length, response, this.request)

    }
}