package com.dounine.fastdoc.core.rep

import com.dounine.fastdoc.core.FastHeader

interface HeaderCallback {

    fun result():Array<FastHeader>

}