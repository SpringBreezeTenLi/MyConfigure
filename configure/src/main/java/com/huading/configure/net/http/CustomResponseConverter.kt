package com.huading.configure.net.http

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.*
import java.nio.charset.StandardCharsets

class CustomResponseConverter<T> internal constructor(
    private val gson: Gson,
    private var adapter: TypeAdapter<T>
) : Converter<ResponseBody, T?> {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val response = value.string()
        val httpStatus = gson.fromJson(response, HttpStatus::class.java)

        if (httpStatus.isCodeInvalid) {
            value.close()
            throw ApiException(httpStatus.code, httpStatus.message)
        }
        val contentType = value.contentType()
        val charset =
            if (contentType != null) contentType.charset(StandardCharsets.UTF_8) else StandardCharsets.UTF_8
        val inputStream: InputStream = ByteArrayInputStream(response.toByteArray())
        val reader: Reader = InputStreamReader(inputStream, charset)
        val jsonReader = gson.newJsonReader(reader)
        return value.use {
            adapter.read(jsonReader)
        }
    }
}