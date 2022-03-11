package com.yowayowa.yawning

import android.annotation.SuppressLint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class HttpClient {
    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
    /**
     * あくびをする
     *
     * @args userID あくびをした人のuserID
     * @args yawned_at あくびをした時間
     * @args latitude あくびをした人の緯度
     * @args logitude あくびをした人の経度
     * @return AkubiResponse? あくびれすぽんす～
     */
    fun akubi(userID:Int, yawned_at: Date?, latitude:Double, longitude:Double) : AkubiResponse?{
        val json = JSONObject()
        json.put("user_id", userID)
        json.put("yawned_at", sdf.format(yawned_at?:""))
        json.put("latitude", latitude)
        json.put("longitude", longitude)
        val request = OkHttp.buildRequestBody(json.toString(),"application/json; charset=utf-8".toMediaType())
        val rawResponse = HttpClient().post("http://133.242.232.245:8000/akubi/",request)
        return try{
            val response = JSONObject(rawResponse?:throw java.lang.Exception())
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
                response.getDouble("distance"),
                akubiList,
                response.getString("last_yawned_at")
            )
        }catch (e:Exception){
            println("akubi_error : ${e.message}")
            null
        }
    }
    /**
     * コンボが成立したかをfetchする。AkubiResponseのList<Akubi>が空だった場合コンボ非成立。
     *
     * @args userID 自分のuserID
     * @args last_yawned_at
     * @return AkubiResponse? あくびれすぽんす～
     */
    fun combo(userID: Int,last_yawned_at: Date?): AkubiResponse?{
        val json = JSONObject()
        json.put("user_id", userID)
        println("sdf.format/////////////////////////" + sdf.format(last_yawned_at?:""))
        json.put("last_yawned_at", sdf.format(last_yawned_at?:""))
        val request = OkHttp.buildRequestBody(json.toString(),"application/json; charset=utf-8".toMediaType())
        val rawResponse = HttpClient().post("http://133.242.232.245:8000/combo/",request)
        return try{
            val response = JSONObject(rawResponse?:throw java.lang.Exception())
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
                response.getDouble("distance"),
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
        val response = HttpClient().post("http://133.242.232.245:8000/register/",request)
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
