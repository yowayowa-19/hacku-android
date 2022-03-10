package com.yowayowa.yawning

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import org.json.JSONObject

class HttpClient {
    /**
     * ユーザー登録をする
     *
     * @args name 登録したい任意のユーザーネーム
     * @args password 登録したい任意のパスワード
     * @return Int? サーバーから割り振られたユーザーID
     */
    fun register(name:String,password:String):Int?{
        val json = JSONObject()
        json.put("name", name)
        json.put("password", password)
        val request = OkHttp.buildRequestBody(json.toString(),"application/json; charset=utf-8".toMediaType())
        val response = HttpClient().post("http://133.242.232.245:8000/register",request)
        return try {
            JSONObject(response).getInt("id")
        }catch (e:Exception) {
            println("error : ${e.message}")
            null
        }
    }

    //basement
    private fun get(url:String):String?{
        val request = OkHttp.buildRequest(url)
        return OkHttp.execute(request)
    }
    private fun post(url: String,requestBody: RequestBody): String?{
        val request = OkHttp.buildPostRequest(url,requestBody)
        return OkHttp.execute(request)
    }
}
