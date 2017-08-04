package com.dounine.fastdoc.core.postman

class FormDataImpl : FormData{


    private lateinit var key:String
    private lateinit var value:String
    private var src:String? = null
    private lateinit var type:FormDataType
    private lateinit var description:String
    private var disabled:Boolean? = null

    constructor(key:String,value:String,src:String?,type:FormDataType,description:String,disabled:Boolean?){
        this.key = key
        this.value = value
        this.src = src
        this.type = type
        this.description = description
        this.disabled = disabled
    }


    override fun getKey(): String = this.key

    override fun getValue(): String = this.value

    /**
     * 在FormData=file情况下使用
     */
    override fun getSrc(): String? = this.src

    override fun getType(): FormDataType = this.type

    override fun getDescription(): String = this.description

    override fun getDisabled(): Boolean? = this.disabled
}