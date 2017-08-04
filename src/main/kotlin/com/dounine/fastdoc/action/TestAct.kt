package com.dounine.fastdoc.action

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestAct {

    @GetMapping("hello")
    fun hello(): String {
        return "world"
    }

    @GetMapping("hello/{username}")
    fun hello(@PathVariable username: String): String {
        return username
    }

    @GetMapping("result/{username}")
    fun username(@PathVariable username: String): Result<String> {
        return ActResult("你vb")
    }

    @PostMapping("result/post/{username}")
    fun usernamePost(@PathVariable username: String,role:String,age:Int): Result<String> {
        return ActResult(role+age)
    }

}