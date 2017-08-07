package com.dounine.fastdoc.core.rep

import com.dounine.fastdoc.core.rep.json.JsonField

interface BaseCallback {

    fun condition():Condition {
        return Condition.EQ
    }

    fun jsonField():JsonField {
        return JsonField()
    }

}