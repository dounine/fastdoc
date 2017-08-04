package com.dounine.fastdoc.core.postman

interface Item {

    fun getName():String

    fun getRequest():Request

    fun getResponse():List<Response>
}