package com.dounine.fastdoc.action

import org.springframework.web.bind.annotation.*

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

    @PutMapping("result/put/{username}")
    fun usernamePut(@PathVariable username: String,role:String,age:Int): Result<String> {
        return ActResult(role+age)
    }

    @PatchMapping("result/patch/{username}")
    fun usernamePatch(@PathVariable username: String,role:String,age:Int): Result<String> {
        return ActResult(role+age)
    }

    @DeleteMapping("result/delete/{username}")
    fun usernameDelete(@PathVariable username: String): Result<String> {
        return ActResult("success")
    }

    @RequestMapping(name = "result/trace/{username}",method = arrayOf(RequestMethod.TRACE))
    fun usernameTrace(@PathVariable username: String): Result<String> {
        return ActResult("success")
    }

    @RequestMapping(name = "result/head/{username}",method = arrayOf(RequestMethod.HEAD))
    fun usernameHead(@PathVariable username: String): Result<String> {
        return ActResult("success")
    }

    @RequestMapping(value = "result/options/{username}",method = arrayOf(RequestMethod.OPTIONS))
    fun usernameOption(@PathVariable username: String): Result<String> {
        return ActResult("success")
    }

}