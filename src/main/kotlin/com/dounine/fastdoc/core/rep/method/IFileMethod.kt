package com.dounine.fastdoc.core.rep.method

interface IFileMethod : IEntityEnclosingMethod {

    fun addFileData(vararg datas: FileData): IFileMethod

    fun download(downloadPath: String): IFileMethod

    fun fileName(fileName: String): IFileMethod
}