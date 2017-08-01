package com.dounine.fastdoc.core

import com.dounine.fastdoc.core.rep.StatusCallback

class FastResponseImpl : FastResponse {
    override fun length(): FastResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun body(): FastResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun headers(): FastResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun status(result: StatusCallback): FastResponse {

        return this
    }
}