package com.dounine.fastdoc.core

import com.dounine.fastdoc.core.doc.LinkCallback
import com.dounine.fastdoc.core.rep.SubsectionPath

interface RestDoc {

    fun name(name:String):RestDoc

    fun links(links:LinkCallback):RestDoc

    fun subsectionPath(sp:SubsectionPath):RestDoc

    fun create():RestDoc

}