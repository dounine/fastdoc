package com.dounine.fastdoc.core.rep.method


interface IEntityEnclosingMethod : BaseMethod {

    fun addData(vararg datas:Data):IEntityEnclosingMethod

}