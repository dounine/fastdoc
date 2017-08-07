package com.dounine.fastdoc.core.rep.json

import com.alibaba.fastjson.annotation.JSONField
import com.dounine.fastdoc.core.FastDocException

class JsonField {

    protected var sb:ArrayList<Expr> = ArrayList()

    fun jfObject(name:String):JsonField{
        if(sb.size>0&&sb.last().type.equals(JFType.NAME)){
            throw FastDocException("jsonField 已经选取了jfName,不能再选择jfObject")
        }else if(sb.size>0&&sb.last().type.equals(JFType.ARRAY_SIZE)){
            throw FastDocException("jsonField 已经选取了jfArraySize,不能再选择jfObject")
        }else if(sb.size>0&&sb.last().type.equals(JFType.ARRAY_SIZE)){
            throw FastDocException("jsonField 已经选取了jfArraySize,不能再选择jfObject")
        }
        sb.add(Expr(JFType.OBJECT,name))
        return this
    }

    fun jfName(name:String):JsonField{
        if(sb.size>=1&&!sb.last().type.equals(JFType.OBJECT)&&!sb.last().type.equals(JFType.ARRAY_GET)){
            throw FastDocException("jsonField 必需先指定jfObject")
        }
        sb.add(Expr(JFType.NAME,name))
        return this
    }

    fun jfArray(name:String):JsonField{
        if(sb.size>0&&sb.last().type.equals(JFType.NAME)){
            throw FastDocException("jsonField 已经选取了jfName,不能再选择jfArray")
        }else if(sb.size>0&&sb.last().type.equals(JFType.ARRAY)){
            throw FastDocException("jsonField 已经选取了jfArray,不能再选择jfArray")
        }else if(sb.size>0&&sb.last().type.equals(JFType.ARRAY_SIZE)){
            throw FastDocException("jsonField 已经选取了jfArraySize,不能再选择jfArray")
        }
        sb.add(Expr(JFType.ARRAY,name))
        return this
    }

    fun jfArrayGet(index:Int):JsonField{
        if(sb.size>0&&!sb.last().type.equals(JFType.ARRAY)){
            throw FastDocException("jsonField 请先选择jfArray")
        }
        sb.add(Expr(JFType.ARRAY_GET,index))
        return this
    }

    fun jsArraySize(size:Int):JsonField{
        if(sb.size>0&&!sb.last().type.equals(JFType.ARRAY)){
            throw FastDocException("jsonField 请先选择jfArray")
        }
        sb.add(Expr(JFType.ARRAY_SIZE,size))
        return this
    }

    fun getExpression():List<Expr>{
        return sb
    }

}