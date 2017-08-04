package com.dounine.fastdoc.core.rep

import com.dounine.fastdoc.core.DataType

interface SubsectionPath {

    fun pathName(pathName: String): SubsectionPath

    fun required(): SubsectionPath

    fun defaultValue(defaultValue: String): SubsectionPath

    fun type(type: DataType): SubsectionPath

    fun constraint(constraint: String): SubsectionPath

    fun description(des: String): SubsectionPath
}