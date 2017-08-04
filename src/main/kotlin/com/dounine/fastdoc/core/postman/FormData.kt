package com.dounine.fastdoc.core.postman

interface FormData {

    fun getKey():String

    fun getValue():String

    /**
     * 在FormData=file情况下使用
     */
    fun getSrc():String?

    fun getType():FormDataType

    fun getDescription():String

    fun getDisabled():Boolean?

}