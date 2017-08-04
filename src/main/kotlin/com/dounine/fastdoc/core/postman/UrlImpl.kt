package com.dounine.fastdoc.core.postman

class UrlImpl : Url {
    private var raw:String
    private var host:List<String>
    private var port:Int? = null
    private var protocol:String? = null
    private var path:List<String>? = null
    private var query:List<Query>? = null
    private var variable:List<String> = ArrayList()

    constructor(raw:String, host:List<String>, port:Int, protocol:String, path: List<String>,query:List<Query>){
        this.raw = raw
        this.host = host
        this.port = port
        this.protocol = protocol
        this.query =query
        if(path.get(0)==""){
            if(path.get(path.size-1)==""){
                this.path = path.subList(1,path.size-1)
            }else{
                this.path = path.subList(1,path.size)
            }
        }
    }

    override fun getRaw(): String {
        return this.raw
    }

    override fun getHost(): List<String> {
        return this.host
    }

    override fun getPort(): Int? {
        return this.port
    }

    override fun getProtocol(): String? {
        return this.protocol
    }

    override fun getPath(): List<String>? {
        return this.path
    }

    override fun getQuery(): List<Query>? {
        return this.query
    }

    override fun getVariable(): List<String> {
        return this.variable
    }
}