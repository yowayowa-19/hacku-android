package com.yowayowa.yawning

import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

object OkHttp {
    private val Json: MediaType = "application/json; charset=utf-8".toMediaType()
    private val client = OkHttpClient()

    fun buildRequestBody(body: String, mediaType: MediaType = Json) : RequestBody {
        return body.toRequestBody(mediaType)
    }
    fun buildRequest(url: String) : Request {
        return Request.Builder()
            .url(url)
            .build()
    }
    fun buildParamRequest(url: String,params:Map<String,String>):Request{
        val urlBuilder = url.toHttpUrlOrNull()?.newBuilder()
        params.forEach(urlBuilder!!::addQueryParameter)
        return Request.Builder()
            .url(urlBuilder.build())
            .build()
    }
    fun buildPostRequest(url: String, requestBody: RequestBody): Request {
        return Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
    }
    fun execute(request: Request): String?{
        val response = client.newCall(request).execute()
        return response.body?.string()
    }
}
