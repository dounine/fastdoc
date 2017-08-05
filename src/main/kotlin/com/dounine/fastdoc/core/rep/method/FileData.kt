package com.dounine.fastdoc.core.rep.method

import java.io.File

class FileData {
    private var name:String
    private var file:File? = null
    private var src:String? = null

    constructor(name:String,file:File){
        this.name = name
        this.file =file
    }

    constructor(name:String,src:String){
        this.name = name
        this.src = src
    }

    fun getName():String{
        return this.name
    }

    fun getFile():File?{
        return this.file
    }

    fun getSrc():String?{
        return this.src
    }
}