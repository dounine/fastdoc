package com.dounine.fastdoc.core.rep.json

import com.dounine.fastdoc.FastdocApplication
import org.springframework.boot.SpringApplication
import java.util.regex.Matcher
import java.util.regex.Pattern

class JsonFieldUtils {

    companion object {
        private val ARRAY_PATTERN:Pattern = Pattern.compile("^[a-zA-Z0-9\$_]+([{]size[}]|[\\[]\\d+[\\]])$")
        private val STRING_LENGTH_PATTERN:Pattern = Pattern.compile("^[a-zA-Z0-9\$_]+[{]length[}]$")
        private val STRING_SINGLE_PATTERN:Pattern = Pattern.compile("^[a-zA-Z0-9\$_]+$")
        private var NUMBER_PATTERN:Pattern = Pattern.compile("(?=[\\d+])\\d+")

        fun converJsonField(exprStr:String):JsonField{
            var jf:JsonField = JsonField()
            var jfs: List<String> = exprStr.split(".")
            var types:ArrayList<String> = ArrayList()
            var sizeLength:Int = jfs.size
            for(s in jfs){
                if(ARRAY_PATTERN.matcher(s).find()){
                    if(s.endsWith("{size}")){
                        jf.jfArray(s.substring(0,s.indexOf("{"))).jfArraySize()
                    }else{
                        var m:Matcher = NUMBER_PATTERN.matcher(s)
                        m.find()
                        jf.jfArray(s.substring(0,s.indexOf("["))).jfArrayGet(Integer.parseInt(m.group()))
                    }
                }else if(STRING_LENGTH_PATTERN.matcher(s).find()){
                    jf.jfStrLength(s.substring(0,s.indexOf("{")))
                }else if(STRING_SINGLE_PATTERN.matcher(s).find()){
                    jf.jfObject(s)
                }
            }
            return jf
        }
    }




}
fun main(args: Array<String>) {
    JsonFieldUtils.converJsonField("data.cusers{size}")
    JsonFieldUtils.converJsonField("data.cusers[0].ages[1]")
    JsonFieldUtils.converJsonField("users{size}")
    JsonFieldUtils.converJsonField("address{length}")
    JsonFieldUtils.converJsonField("data[0].address{length}")
    JsonFieldUtils.converJsonField("data.cusers[0].address{length}")
    JsonFieldUtils.converJsonField("data.cusers[0].address{length}")
}