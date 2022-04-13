package com.huading.configure.net.http

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class CustomGsonConverterFactory private constructor(gson: Gson?) : Converter.Factory() {
    private val gson: Gson
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return CustomResponseConverter<Any>(gson, adapter as TypeAdapter<Any>)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return CustomRequestConverter(gson, adapter as TypeAdapter<Any>)
    }

    companion object {
        @JvmOverloads
        fun create(gson: Gson? = Gson()): CustomGsonConverterFactory {
            return CustomGsonConverterFactory(gson)
        }
    }

    init {
        if (gson == null) throw NullPointerException("gson == null")
        this.gson = Gson()
    }
}