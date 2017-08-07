package com.dounine.fastdoc.core.rep.json.express

import com.alibaba.fastjson.JSONObject
import com.dounine.fastdoc.core.FastDocException
import java.util.regex.Pattern

class ObjectLengthExpressImpl : BaseExpress {

    companion object {
        private val STRING_SINGLE_PATTERN: Pattern = Pattern.compile("^[a-zA-Z0-9\$_]+$")
    }

    protected var name: String = ""

    override fun name(): String {
        return name
    }

    override fun matcher(str: String): Boolean {
        if (str.indexOf("{") != -1) {
            name = str.substring(0, str.indexOf("{"))
        } else {
            name = str
        }
        return STRING_SINGLE_PATTERN.matcher(str).find()
    }

    override fun expressStr(responseStr: String, parentJsonFields: StringBuilder): String {
        if ("" == responseStr) {
            throw FastDocException("JsonField [ ${parentJsonFields.toString()}.${name} ]键不存在")
        }
        var jo: JSONObject = JSONObject.parseObject(responseStr)
        if (!jo.containsKey(name)) {
            throw FastDocException("JsonField [ ${parentJsonFields.toString()} ]键不存在")
        }
        var exprStr: String = jo.getString(name)
        if (parentJsonFields.length > 0) {
            parentJsonFields.append(".")
        }
        parentJsonFields.append(name)
        return exprStr
    }

}