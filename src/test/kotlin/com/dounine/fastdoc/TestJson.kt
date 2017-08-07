package com.dounine.fastdoc

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import org.junit.Test

class TestJson {

    @Test
    fun testFastJsonField(){
        var jsonStr:String = "{\"name\":\"lake\",\"headers\":[{\"name\":\"header1\"},{\"name\":\"header2\"}],\"obj\":{\"name\":\"objName\"}}"
        var jb:JSONObject = JSON.parseObject(jsonStr)
        println((jb.get("obj") as JSONObject).get("name"))
        println((jb.get("headers") as JSONArray).get(0))
    }
}