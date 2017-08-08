package com.dounine.fastdoc.core.rep.json.express

interface BaseExpress {

    fun name():String

    fun matcher(str:String):Boolean

    fun expressStr(responseStr:String, parentJsonFields:StringBuilder):String

}