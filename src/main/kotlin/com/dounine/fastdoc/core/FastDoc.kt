package com.dounine.fastdoc.core

interface FastDoc {

    fun doRequest(): FastRequest

    fun setPrefixUrl(prefixUrl: String)

}