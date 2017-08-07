package com.dounine.fastdoc.core.rep.method

import java.io.File

class FileData {
    private var name:String
    private var file:File? = null
    private var url:String? = null

    constructor(name:String,file:File){
        this.name = name
        this.file =file
    }

    constructor(name:String,url:String){
        this.name = name
        this.url = url
    }

    fun getName():String{
        return this.name
    }

    fun getFile():File?{
        return this.file
    }

    fun getUrl():String?{
        return this.url
    }
}