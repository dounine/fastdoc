package com.dounine.fastdoc.core.rep.json.express

import com.alibaba.fastjson.JSONObject
import com.dounine.fastdoc.core.FastDocException
import java.util.regex.Matcher
import java.util.regex.Pattern

class ArrayExpressImpl : BaseExpress {

    companion object {
        private val ARRAY_PATTERN:Pattern = Pattern.compile("^[a-zA-Z0-9\$_]+([{]size[}]|[\\[]\\d+[\\]])$")
        private var NUMBER_PATTERN:Pattern = Pattern.compile("(?=[\\d+])\\d+")
    }

    protected var name: String = ""
    protected var index:Int = -1
    protected var querySize:Boolean = false

    override fun name(): String {
        return name
    }

    override fun matcher(str: String): Boolean {
        if(ARRAY_PATTERN.matcher(str).find()){
            if(str.endsWith("{size}")){
                name = str.substring(0,str.indexOf("{"))
                this.querySize=  true
            }else{
                var m: Matcher = NUMBER_PATTERN.matcher(str)
                m.find()
                index = Integer.parseInt(m.group())
                name = str.substring(0,str.indexOf("["))
            }
            return true
        }
        return false
    }

    override fun expressStr(responseStr: String, parentJsonFields: StringBuilder): String {
        var jo: JSONObject = JSONObject.parseObject(responseStr)
        if (!jo.containsKey(this.name)) {
            throw FastDocException("JsonField [ ${parentJsonFields.toString()} ]键不存在")
        }
        var jos = JSONObject.parseArray(jo.getString(this.name))
        var exprStr: String = jos.toString()
        if (parentJsonFields.length > 0) {
            parentJsonFields.append(".")
        }
        parentJsonFields.append(this.name)
        if(querySize){
            var jos = JSONObject.parseArray(exprStr)
            return jos.size.toString()
        }else if(index!=-1){
            var jos = JSONObject.parseArray(exprStr).get(index)
            return jos.toString()
        }
        return exprStr
    }

}