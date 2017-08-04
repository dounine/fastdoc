package com.dounine.fastdoc.core.postman

interface ItemGroup {

    fun getName():String

    fun getDescription():String

    fun getItem():List<Item>
}