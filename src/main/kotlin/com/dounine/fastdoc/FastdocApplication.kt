package com.dounine.fastdoc

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class FastdocApplication

fun main(args: Array<String>) {
    SpringApplication.run(FastdocApplication::class.java, *args)
}
