package com.dounine.fastdoc.core.rep.json.express

object ExpressFactory {

    fun all():List<BaseExpress>{
        var list:ArrayList<BaseExpress> = ArrayList()
        list.add(ObjectExpressImpl())
        list.add(ArrayExpressImpl())
        return list
    }
}