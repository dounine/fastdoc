package com.dounine.fastdoc.core.rep.json.express

class BaseExpressEx {

    protected val jsonField:String
    protected val list:List<BaseExpress> = ExpressFactory.all()

    constructor(jsonField:String){
        this.jsonField = jsonField
    }


    fun expressStr(responseStr: String): String {
        var els: List<String> = jsonField.split(".")
        var tmpResponseStr:String = responseStr
        var parentJsonFields:StringBuilder = StringBuilder()
        for(el in els){
            for(be in list){
                if(be.matcher(el)){
                    tmpResponseStr = be.expressStr(tmpResponseStr,parentJsonFields = parentJsonFields)
                    break
                }
            }
        }

        return tmpResponseStr
    }


}