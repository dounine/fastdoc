package com.dounine.fastdoc

import com.alibaba.fastjson.JSON
import com.dounine.fastdoc.action.TestAct
import com.dounine.fastdoc.core.*
import com.dounine.fastdoc.core.doc.LinkCallback
import com.dounine.fastdoc.core.doc.LinkData
import com.dounine.fastdoc.core.rep.*
import com.dounine.fastdoc.core.rep.method.Data
import com.dounine.fastdoc.core.req.PostDataImpl
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.hibernate.validator.internal.util.privilegedactions.GetMethod
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import java.util.*
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.javaMethod


//@RunWith(SpringRunner::class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EnableAutoConfiguration
class FastdocApplicationTests {

	//		println(localServerPort)
//		val multiValueMap = HashMap<String, String>()
//		multiValueMap.put("username", "lake")//传值，但要在url上配置相应的参数
//		var client:HttpClient = HttpClients.createDefault()
//		var getRequest:HttpGet = HttpGet("/hello")
//		var response:HttpResponse = client.execute(getRequest)
//		println(EntityUtils.toString(response.entity))
////		testRestTemplate.restTemplate.get
//		val result = testRestTemplate.getForObject("/hello", String::class.java, multiValueMap)
//		println(result)

	@LocalServerPort
	var localServerPort: Int = 0
	@Autowired
	lateinit var testRestTemplate: TestRestTemplate
	var fastDoc:FastDocImpl = FastDocImpl

	@Before
	fun init(){
		fastDoc.setAppName("用户模块测试")
		fastDoc.setGroupName("用户功能测试")
	}

	@Test
	fun testGetMethod() {
		fastDoc.doRequest()
				.prefixUrl("http://localhost:8080")
				.url("/result/{username}?u=1&cc=1234","lake")
				.GET()
				.doResponse()
				.status(object : StatusCallback{
					override fun result(): Int {
						return 200
					}
				})
				.body(object : BodyCallback{
					override fun result(): String {
						return "{\"code\":0,\"msg\":\"\",\"data\":\"你vb\"}"
					}
				})
				.restDoc()
				.name("查询列表")
				.subsectionPath(SubsectionPathImpl().pathName("username").description("用户"))
				.subsectionPath(SubsectionPathImpl())
				.create()
	}

	@Test
	fun testPostMethod() {
		fastDoc.doRequest()
				.prefixUrl("http://localhost:8080")
				.url("/result/post/{username}?u=1&cc=1234","lake")
				.POST()
				.addData(Data("age",22))
				.addData(Data("role","admin"))
				.doResponse()
				.status(object : StatusCallback{
					override fun result(): Int {
						return 200
					}
				})
				.body(object : BodyCallback{
					override fun result(): String {
						return "{\"code\":0,\"msg\":\"\",\"data\":\"admin22\"}"
					}
				})
				.restDoc()
				.name("查询列表")
				.subsectionPath(SubsectionPathImpl().pathName("username").description("用户"))
				.subsectionPath(SubsectionPathImpl())
				.create()
	}

	@Test
	fun testPutMethod() {
		fastDoc.doRequest()
				.prefixUrl("http://localhost:8080")
				.url("/result/put/{username}?u=1&cc=1234","lake")
				.PUT()
				.addData(Data("age",22),Data("role","admin"))
				.doResponse()
				.status(object : StatusCallback{
					override fun result(): Int {
						return 200
					}
				})
				.body(object : BodyCallback{
					override fun result(): String {
						return "{\"code\":0,\"msg\":\"\",\"data\":\"admin22\"}"
					}
				})
				.restDoc()
				.name("查询列表")
				.subsectionPath(SubsectionPathImpl().pathName("username").description("用户"))
				.subsectionPath(SubsectionPathImpl())
				.create()
	}

	@Test
	fun testPatchMethod() {
		fastDoc.doRequest()
				.prefixUrl("http://localhost:8080")
				.url("/result/patch/{username}?u=1&cc=1234","lake")
				.PATCH()
				.addData(Data("age",22))
				.addData(Data("role","admin"))
				.doResponse()
				.status(object : StatusCallback{
					override fun result(): Int {
						return 200
					}
				})
				.body(object : BodyCallback{
					override fun result(): String {
						return "{\"code\":0,\"msg\":\"\",\"data\":\"admin22\"}"
					}
				})
				.restDoc()
				.name("查询列表")
				.subsectionPath(SubsectionPathImpl().pathName("username").description("用户"))
				.subsectionPath(SubsectionPathImpl())
				.create()
	}

	@Test
	fun testDeleteMethod() {
		fastDoc.doRequest()
				.prefixUrl("http://localhost:8080")
				.url("/result/delete/{username}?u=1&cc=1234","lake")
				.DELETE()
				.doResponse()
				.status(object : StatusCallback{
					override fun result(): Int {
						return 200
					}
				})
				.body(object : BodyCallback{
					override fun result(): String {
						return "{\"code\":0,\"msg\":\"\",\"data\":\"success\"}"
					}
				})
				.restDoc()
				.name("查询列表")
				.subsectionPath(SubsectionPathImpl().pathName("username").description("用户"))
				.subsectionPath(SubsectionPathImpl())
				.create()
	}

	@Test
	fun testOptionsMethod() {
		fastDoc.doRequest()
				.prefixUrl("http://localhost:8080")
				.url("/result/options/{username}?u=1&cc=1234","lake")
				.OPTIONS()
				.doResponse()
				.status(object : StatusCallback{
					override fun result(): Int {
						return 200
					}
				})
				.body(object : BodyCallback{
					override fun result(): String {
						return "{\"code\":0,\"msg\":\"\",\"data\":\"success\"}"
					}
				})
				.restDoc()
				.name("查询列表")
				.subsectionPath(SubsectionPathImpl().pathName("username").description("用户"))
				.subsectionPath(SubsectionPathImpl())
				.create()
	}

}
