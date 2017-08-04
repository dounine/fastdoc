package com.dounine.fastdoc.action

import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class ActResult<T> : Result<T> {

    private var code:Int = 0
    private var msg:String
    private var data:T

    constructor(code:Int,data:T,msg:String){
        this.code =code
        this.msg = msg
        this.data = data
    }

    constructor(data:T,msg:String){
        this.msg = msg
        this.data = data
    }

    constructor(data:T){
        this.msg = ""
        this.data = data
    }

    override fun getCode(): Int {
        return code
    }

    override fun getData(): T {
        return data
    }

    override fun getMsg(): String {
        return msg
    }


}