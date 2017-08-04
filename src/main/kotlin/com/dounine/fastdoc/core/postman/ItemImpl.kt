package com.dounine.fastdoc.core.postman

class ItemImpl :Item {
    private lateinit var name:String
    private lateinit var request:Request
    private var response:List<Response> = ArrayList()

    override fun getName(): String = this.name

    fun setName(name:String){
        this.name = name
    }

    override fun getRequest(): Request = this.request

    fun setRequest(request:Request){
        this.request = request
    }

    override fun getResponse(): List<Response> = this.response

    fun setResponse(response: List<Response>){
        this.response =response
    }
}