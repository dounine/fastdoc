package com.dounine.fastdoc.core.postman

interface Query {

    fun getKey():String

    fun getValue():String

    fun getEquals():Boolean

    fun getDescription():String

    fun getDisabled():Boolean?
}