package com.dounine.fastdoc.core.postman

interface PostMan {

    fun getVariables():List<String>

    fun getInfo():Info

    fun getItem():List<ItemGroup>
}