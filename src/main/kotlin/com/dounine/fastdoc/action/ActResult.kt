package com.dounine.fastdoc.action


class ActResult : Result {

    private var code:Int = 0
    private var msg:String
    private var data:Any

    constructor(code:Int,data:Any,msg:String){
        this.code =code
        this.msg = msg
        this.data = data
    }

    constructor(data:Any,msg:String){
        this.msg = msg
        this.data = data
    }

    constructor(data:Any){
        this.msg = ""
        this.data = data
    }

    override fun getCode(): Int {
        return code
    }

    override fun getData(): Any {
        return data
    }

    override fun getMsg(): String {
        return msg
    }


}