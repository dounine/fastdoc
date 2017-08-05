package com.dounine.fastdoc.core.rep.method

import com.dounine.fastdoc.core.FastRequest
import com.dounine.fastdoc.core.FastRequestMethod

class PatchMethod(request: FastRequest) : EntityEnclosingMethod(request,FastRequestMethod.PATCH),IPatchMethod {
}