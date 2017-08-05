package com.dounine.fastdoc.core

object FastDocImpl : FastDoc {


    private lateinit var applicationName:String
    private lateinit var groupName:String
    private lateinit var prefixUrl:String

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

    override fun setPrefixUrl(prefixUrl: String) {
        this.prefixUrl = prefixUrl
    }

    fun getPrefixUrl() :String{
        return this.prefixUrl
    }

}