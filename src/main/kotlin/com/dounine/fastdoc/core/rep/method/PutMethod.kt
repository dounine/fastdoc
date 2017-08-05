package com.dounine.fastdoc.core.rep.method

import com.dounine.fastdoc.core.FastRequest
import com.dounine.fastdoc.core.FastRequestMethod

class PutMethod(request:FastRequest) : EntityEnclosingMethod(request,FastRequestMethod.PUT),IPutMethod {
}