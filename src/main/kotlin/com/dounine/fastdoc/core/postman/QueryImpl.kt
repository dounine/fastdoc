package com.dounine.fastdoc.core.postman

class QueryImpl : Query {
    private var key:String
    private var value:String = ""
    private val equals:Boolean = true
    private var description:String = ""
    private var disabled:Boolean? = null

    constructor(key:String,value:String,description:String,disabled:Boolean?){
        this.key = key
        this.value =value
        this.description =description
        this.disabled = disabled
    }


    override fun getKey(): String {
        return this.key
    }

    override fun getValue(): String {
        return this.value
    }

    override fun getEquals(): Boolean {
        return this.equals
    }

    override fun getDescription(): String {
        return this.description
    }

    override fun getDisabled(): Boolean? {
        return this.disabled
    }
}