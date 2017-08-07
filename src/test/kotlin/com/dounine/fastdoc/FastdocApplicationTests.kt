package com.dounine.fastdoc

import com.dounine.fastdoc.core.FastDocImpl
import com.dounine.fastdoc.core.UrlParameter
import com.dounine.fastdoc.core.rep.*
import com.dounine.fastdoc.core.rep.json.JsonField
import com.dounine.fastdoc.core.rep.json.JsonFieldUtils
import com.dounine.fastdoc.core.rep.method.*
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.web.client.TestRestTemplate
import java.io.File
import java.util.*


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
    var fastDoc: FastDocImpl = FastDocImpl

    @Before
    fun init() {
        fastDoc.setAppName("用户模块测试")
        fastDoc.setGroupName("用户功能测试")
        fastDoc.setTimeout(3*1000)
        fastDoc.setPrefixUrl("http://localhost:8080")
    }

    @Test
    fun testGetMethod() {
        fastDoc.doRequest()
                .prefixUrl("http://localhost:8080")
                .url("/result/{username}?u=1&cc=1234", Arrays.asList(UrlParameter("username", 123)))
                .GET()
                .doResponse()
                .status(object : StatusCallback {
                    override fun result(): Int {
                        return 200
                    }
                })
                .body(object : BodyCallback {
                    override fun result(): Any {
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
                .url("/result/post/{username}?u=1&cc=1234", "lake")
                .POST()
                .addData(Data("age", 22))
                .addData(Data("role", "admin"))
                .doResponse()
                .status(object : StatusCallback {
                    override fun result(): Int {
                        return 200
                    }
                })
                .body(object : BodyCallback {
                    override fun result(): Any {
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
    fun testFileMethod() {
        fastDoc.doRequest()
                .prefixUrl("http://localhost:8080")
                .url("/result/file")
                .FILE()
                .addFileData(FileData("files", File("/home/lake/github/fastdoc/build.gradle")))
                .addFileData(FileData("files", "https://raw.githubusercontent.com/dounine/clouddisk/master/src/main/java/com/dounine/clouddisk360/parser/BaseParser.java"))
                .addData(Data("username", 22))
                .doResponse()
                .status(object : StatusCallback {
                    override fun result(): Int {
                        return 200
                    }
                })
                .body(object : BodyCallback {
                    override fun result(): Any {
                        return "{\"code\":0,\"msg\":\"\",\"data\":\"22\"}"
                    }
                })
                .restDoc()
                .name("查询列表")
                .subsectionPath(SubsectionPathImpl().pathName("username").description("用户"))
                .subsectionPath(SubsectionPathImpl())
                .create()
    }

    @Test
    fun testGetFileMethod() {
        fastDoc.doRequest()
                .prefixUrl("http://localhost:8080")
                .url("/result/build.gradle")
                .FILE()
                .download("/home/lake/github/fastdoc/build/files/")
                .doResponse()
                .status(object : StatusCallback {
                    override fun result(): Int {
                        return 200
                    }
                })
                .length(object : LengthCallback{
                    override fun result(): Long {
                        return 1649
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
                .url("/result/put/{username}?u=1&cc=1234", "lake")
                .PUT()
                .addData(Data("age", 22), Data("role", "admin"))
                .doResponse()
                .status(object : StatusCallback {
                    override fun result(): Int {
                        return 200
                    }
                })
                .body(object : BodyCallback {
                    override fun result(): Any {
                        return 10
                    }

                    override fun jsonField(): JsonField {
//                        return JsonField().jfObject("data").jfObject("cu").jfArray("ages").jfArrayGet(0)
                        return JsonFieldUtils.converJsonField("msg.cc{length}")
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
                .url("/result/patch/{username}?u=1&cc=1234", "lake")
                .PATCH()
                .addData(Data("age", 22))
                .addData(Data("role", "admin"))
                .doResponse()
                .status(object : StatusCallback {
                    override fun result(): Int {
                        return 200
                    }
                })
                .body(object : BodyCallback {
                    override fun result(): Any {
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
                .url("/result/delete/{username}?u=1&cc=1234", "lake")
                .DELETE()
                .doResponse()
                .status(object : StatusCallback {
                    override fun result(): Int {
                        return 200
                    }
                })
                .body(object : BodyCallback {
                    override fun result(): Any {
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
                .url("/result/options/{username}?u=1&cc=1234", "lake")
                .OPTIONS()
                .doResponse()
                .status(object : StatusCallback {
                    override fun result(): Int {
                        return 200
                    }
                })
                .body(object : BodyCallback {
                    override fun result(): Any {
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
    fun testCookieMethod() {
        fastDoc.doRequest()
                .prefixUrl("http://localhost:8080")
                .url("/result/get/cookie")
                .GET()
                .addCookie(Cookie("name", "lake"))
                .doResponse()
                .status(object : StatusCallback {
                    override fun result(): Int {
                        return 200
                    }
                })
                .body(object : BodyCallback {
                    override fun result(): Any {
                        return "{\"code\":0,\"msg\":\"\",\"data\":\"lake\"}"
                    }
                })
                .restDoc()
                .name("查询列表")
                .subsectionPath(SubsectionPathImpl().pathName("username").description("用户"))
                .subsectionPath(SubsectionPathImpl())
                .create()
    }

    @Test
    fun testHeaderMethod() {
        fastDoc.doRequest()
                .url("/result/get/header")
                .GET()
                .addParameter(Parameter("name", "cc"))
                .addParameter(Parameter("name1", "cc"))
                .addHeader(Header("name", "lake"))
                .doResponse()
                .status(object : StatusCallback {
                    override fun result(): Int {
                        return 200
                    }
                })
                .body(object : BodyCallback {
                    override fun result(): Any {
                        return "{\"code\":0,\"msg\":\"\",\"data\":\"lake\"}"
                    }
                })
                .restDoc()
                .name("查询列表")
                .subsectionPath(SubsectionPathImpl().pathName("username").description("用户"))
                .subsectionPath(SubsectionPathImpl())
                .create()
    }

}
