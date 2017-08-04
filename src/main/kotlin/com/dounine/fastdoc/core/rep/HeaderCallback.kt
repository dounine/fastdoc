package com.dounine.fastdoc.core.rep

import com.dounine.fastdoc.core.postman.Header

interface HeaderCallback {

    fun result():Array<Header>

}