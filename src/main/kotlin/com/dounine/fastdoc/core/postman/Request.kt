package com.dounine.fastdoc.core.postman

import com.dounine.fastdoc.core.FastRequestMethod

interface Request {

    fun getUrl():Any

    fun getMethod():FastRequestMethod

    fun getHeader():List<Header>

    fun getBody():Body?

    fun getDescription():String


}