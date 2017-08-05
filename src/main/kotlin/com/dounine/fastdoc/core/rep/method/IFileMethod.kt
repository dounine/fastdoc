package com.dounine.fastdoc.core.rep.method

interface IFileMethod : IEntityEnclosingMethod {

    fun addFileData(vararg datas:FileData):IFileMethod

}