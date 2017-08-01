package com.dounine.fastdoc

import com.alibaba.fastjson.JSON
import com.dounine.fastdoc.action.TestAct
import com.dounine.fastdoc.core.*
import com.dounine.fastdoc.core.doc.LinkCallback
import com.dounine.fastdoc.core.doc.LinkData
import com.dounine.fastdoc.core.rep.BodyCallback
import com.dounine.fastdoc.core.rep.StatusCallback
import com.dounine.fastdoc.core.rep.StatusCallbackImpl
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.beans.factory.annotation.Autowired
import java.util.HashMap
import org.springframework.boot.context.embedded.LocalServerPort
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.javaMethod


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
class FastdocApplicationTests {

	@LocalServerPort
	var localServerPort: Int = 0
	@Autowired
	lateinit var testRestTemplate: TestRestTemplate
	lateinit var fastDoc:FastDoc

	@Before
	fun init(){
		fastDoc = FastDocImpl()
	}

	@Test
	fun contextLoads() {
		fastDoc.doRequest()
				.prefixUrl("http://localhost")
				.url("/test/{username}","lake")
				.method(FastRequestMethod.GET)
				.doResponse()
				.status(object : StatusCallback{
					override fun result(): Int {
						return 200
					}
				})
				.body(object : BodyCallback{
					override fun result(): String {
						return "success"
					}
				})
				.restDoc()
				.links(object :LinkCallback{
					override fun links(): Array<LinkData?> {
						var fields: Array<LinkData?> = arrayOfNulls(100)
						fields.set(0,LinkData("name","这是des"))

						return fields
					}

				})

		println(localServerPort)
		val multiValueMap = HashMap<String, String>()
		multiValueMap.put("username", "lake")//传值，但要在url上配置相应的参数
		var client:HttpClient = HttpClients.createDefault()
		var getRequest:HttpGet = HttpGet("/hello")
		var response:HttpResponse = client.execute(getRequest)
		println(EntityUtils.toString(response.entity))
//		testRestTemplate.restTemplate.get
		val result = testRestTemplate.getForObject("/hello", String::class.java, multiValueMap)
		println(result)
	}

}
