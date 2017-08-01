package com.dounine.fastdoc.action

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestAct {

    @GetMapping("hello")
    fun hello():String{
        return "world"
    }

}