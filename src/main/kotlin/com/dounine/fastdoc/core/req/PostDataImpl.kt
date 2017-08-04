package com.dounine.fastdoc.core.req

class PostDataImpl : PostData {

    private lateinit var name:String
    private lateinit var value:Any

    constructor(name:String,value:Any){
        this.name = name
        this.value = value
    }


    fun setName(name:String){
        this.name = name
    }

    override fun getName(): String {
        return this.name
    }

    override fun getValue(): Any {
        return this.value
    }

    fun setValue(value:Any){
        this.value = value
    }
}