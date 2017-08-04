package com.dounine.fastdoc.core.postman

class PostManImpl : PostMan {

    private var variables:List<String> = ArrayList()
    private lateinit var info:Info
    private var item:List<ItemGroup> = ArrayList()

    override fun getVariables(): List<String> = this.variables

    fun setInfo(info:Info){
        this.info = info
    }

    override fun getInfo(): Info = this.info

    override fun getItem(): List<ItemGroup> = this.item

    fun setItem(itemGroup:List<ItemGroup>){
        this.item = itemGroup
    }
}