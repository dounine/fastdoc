package com.dounine.fastdoc.core.postman

class BodyImpl : Body{

    private lateinit var mode:BodyMode
    private var formdata:List<FormData> = ArrayList()

    fun setMode(mode:BodyMode){
        this.mode = mode
    }

    override fun getMode(): BodyMode = this.mode

    fun setFormdata(formdata:List<FormData>){
        this.formdata = formdata
    }

    override fun getFormdata(): List<FormData> = this.formdata
}