package com.dounine.fastdoc.core.rep

import com.dounine.fastdoc.core.postman.Header

interface HeaderCallback  : BaseCallback{

    fun result():Array<Header>

}