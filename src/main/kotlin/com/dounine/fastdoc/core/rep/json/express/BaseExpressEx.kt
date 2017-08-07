package com.dounine.fastdoc.core.rep.json.express

import com.dounine.fastdoc.core.rep.json.Expr

abstract class BaseExpressEx {

    protected val elStr:String

    constructor(elStr:String){
        this.elStr = elStr
    }


    fun expressStr(responseStr: String, parentJsonFields: StringBuilder): String {
        var list:List<BaseExpress> = ExpressFactory.all()
        var els: List<String> = elStr.split(".")
        var tmpResponseStr:String = ""
        for(el in els){
            for(be in list){
                if(be.matcher(el)){
                    tmpResponseStr = be.expressStr(responseStr,parentJsonFields = parentJsonFields)
                    break
                }
            }
        }

        return ""
    }


}