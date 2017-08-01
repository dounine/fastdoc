package com.dounine.fastdoc.core

import com.dounine.fastdoc.core.doc.LinkCallback

interface RestDoc {

    fun links(links:LinkCallback):RestDoc


}