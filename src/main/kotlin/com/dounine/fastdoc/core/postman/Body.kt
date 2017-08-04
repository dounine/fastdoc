package com.dounine.fastdoc.core.postman

interface Body {

    fun getMode():BodyMode

    fun getFormdata():List<FormData>

}