package com.dounine.fastdoc.core.rep.method

import com.dounine.fastdoc.core.FastRequest
import com.dounine.fastdoc.core.FastRequestMethod

class PostMethod(request: FastRequest) : EntityEnclosingMethod(request,FastRequestMethod.POST), IPostMethod {
}