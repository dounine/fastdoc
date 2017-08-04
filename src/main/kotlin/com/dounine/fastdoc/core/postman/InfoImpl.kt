package com.dounine.fastdoc.core.postman

import java.util.*

class InfoImpl : Info {
    private lateinit var name:String
    private var _postman_id:String = UUID.randomUUID().toString()
    private var description:String = ""
    private var schema:String = "https://schema.getpostman.com/json/collection/v2.0.0/collection.json"


    override fun getName(): String = this.name

    fun setName(name:String){
        this.name = name
    }

    override fun get__postman_id(): String = this._postman_id

    override fun getDescription(): String = this.description

    fun setDescription(des:String){
        this.description = des
    }

    override fun getSchema(): String = this.schema
}