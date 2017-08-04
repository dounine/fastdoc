package com.dounine.fastdoc.core

object FastDocImpl : FastDoc {

    private lateinit var applicationName:String
    private lateinit var groupName:String

    override fun doRequest(): FastRequest {
        return FastRequestImpl()
    }

    fun setAppName(name:String){
        this.applicationName = name
    }

    fun setGroupName(name:String){
        this.groupName = name
    }

    fun getAppName():String{
        return applicationName
    }

    fun getGroupName():String{
        return groupName
    }

}