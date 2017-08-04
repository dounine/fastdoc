package com.dounine.fastdoc.core.postman

class ItemGroupImpl :ItemGroup {
    private lateinit var name:String
    private var description:String = ""
    private var item:List<Item> = ArrayList()

    override fun getName(): String = this.name

    fun setName(name:String){
        this.name = name
    }

    override fun getDescription(): String = this.description

    fun setDescription(description:String){
        this.description = description
    }

    override fun getItem(): List<Item> = this.item

    fun  setItem(item:List<Item>){
        this.item = item
    }
}