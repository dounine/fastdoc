package com.dounine.fastdoc.core.postman

interface Url {

    fun getRaw():String

    fun getHost():List<String>

    fun getPort():Int?

    fun getProtocol():String?

    fun getPath():List<String>?

    fun getQuery():List<Query>?

    fun getVariable():List<String>

}