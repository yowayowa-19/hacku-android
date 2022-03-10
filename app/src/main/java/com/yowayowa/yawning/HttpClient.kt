package com.yowayowa.yawning

import okhttp3.RequestBody

class HttpClient {
    fun get(url:String):String?{
        val request = OkHttp.buildRequest(url)
        return OkHttp.execute(request)
    }
    fun post(url: String,requestBody: RequestBody): String?{
        val request = OkHttp.buildPostRequest(url,requestBody)
        return OkHttp.execute(request)
    }
}
