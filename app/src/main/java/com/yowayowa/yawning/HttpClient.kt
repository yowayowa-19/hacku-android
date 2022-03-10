package com.yowayowa.yawning

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

class HttpClient {

    /**
     * あくびをする
     *
     * @args userID あくびをした人のuserID
     * @args yawned_at あくびをした時間
     * @args latitude あくびをした人の緯度
     * @args logitude あくびをした人の経度
     * @return AkubiResponse? あくびれすぽんす～
     */
    fun akubi(userID:Int, yawned_at: Date, latitude:Double, longitude:Double) : AkubiResponse?{
        val json = JSONObject()
        json.put("user_id", userID)
        json.put("yawned_at", yawned_at)
        json.put("latitude", latitude)
        json.put("longitude", longitude)
        val request = OkHttp.buildRequestBody(json.toString(),"application/json; charset=utf-8".toMediaType())
        val rawResponse = HttpClient().post("http://133.242.232.245:8000/akubi",request)
        return try{
            val response = JSONObject(rawResponse)
            val akubiList = mutableListOf<Akubi>()
            val akubis = response.getJSONArray("akubis")
            repeat(akubis.length()){
                val target = akubis.getJSONObject(it)
                val userId = target.getInt("user_id")
                val yawnedAt = target.getString("yawned_at")
                val lat = target.getDouble("latitude")
                val long = target.getDouble("longitude")
                akubiList.add(Akubi(userId,yawnedAt,lat,long))
            }
            AkubiResponse(
                response.getInt("user_id"),
                response.getInt("combo_count"),
                akubiList,
                response.getString("last_yawned_at")
            )
        }catch (e:Exception){
            println("akubi_error : ${e.message}")
            null
        }
    }
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
            println("register_error : ${e.message}")
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
