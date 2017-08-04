package com.dounine.fastdoc.core.rep

import com.dounine.fastdoc.core.DataType

class SubsectionPathImpl : SubsectionPath {


    private var pathName:String = ""
    private var required:Boolean = false
    private var defaultValue:String = ""
    private var type:DataType = DataType.String
    private var constraint:String = ""
    private var description:String  = ""

    override fun pathName(pathName: String): SubsectionPath {
        this.pathName = pathName
        return this
    }

    override fun required(): SubsectionPath {
        this.required = true
        return this
    }

    override fun defaultValue(defaultValue: String): SubsectionPath {
        this.defaultValue = defaultValue
        return this
    }

    override fun type(type: DataType): SubsectionPath {
        this.type = type
        return this
    }

    override fun constraint(constraint: String): SubsectionPath {
        this.constraint = constraint
        return this
    }

    override fun description(des: String): SubsectionPath {
        this.description = des
        return this
    }

    fun getPathName():String = this.pathName
    fun getRequired():Boolean = this.required
    fun getDefaultValue():String = defaultValue
    fun getType():DataType = this.type
    fun getConstraint():String = this.constraint
    fun getDescription():String  = this.description
}