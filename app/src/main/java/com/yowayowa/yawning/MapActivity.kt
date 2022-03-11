package com.yowayowa.yawning

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.preference.PreferenceManager
import com.yowayowa.yawning.databinding.ActivityMapBinding
import kotlinx.coroutines.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.lang.Runnable
import java.text.SimpleDateFormat
import java.lang.Integer.min
import java.util.*
import kotlin.math.abs


class MapActivity : AppCompatActivity(), LocationListener {
    private lateinit var map : MapView
    private lateinit var comboTextView: TextView
    private lateinit var progressBar:ProgressBar
    private lateinit var timer:Timer
    private lateinit var pastPoints : MutableList<GeoPoint>
    private lateinit var myPoints : MutableList<GeoPoint>
    lateinit var mLocationManager : LocationManager
    private var myLocate : Location? = null
    private var lastYawnedAt: String? = null
    private var distance: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationStart()
        pastPoints = mutableListOf()
        myPoints = mutableListOf()
        map = binding.map // TODO:クラスメンバー変数にした影響より、各関数の引数周り変更する
        comboTextView = binding.comboTextView // TODO:クラスメンバー変数にした影響より、各関数の引数周り変更する
        progressBar = binding.progressBar

        initMap(map)
        binding.map.addOnFirstLayoutListener{ view: View, i: Int, i1: Int, i2: Int, i3: Int ->
            update(binding.map,binding.comboTextView)
        }
        akubi(binding.map,binding.comboTextView)
    }

    private fun initMap(map:MapView){
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.minZoomLevel = 3.0
        map.maxZoomLevel = 15.0
        map.setMultiTouchControls(true)//マップの2本指でのピンチアウト
        map.setLayerType(View.LAYER_TYPE_SOFTWARE,null)
        val mapController = map.controller
        mapController.setZoom(9.0)
        mapController.setCenter(GeoPoint(myLocate!!.latitude,myLocate!!.longitude))
        myPoints.add(GeoPoint(myLocate!!.latitude,myLocate!!.longitude))
        startDegreeProgressBar()
    }
    private fun akubi(map: MapView ,comboTextView: TextView){
        val pref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        GlobalScope.launch {
            val deferred = async(Dispatchers.IO){
                HttpClient().akubi(
                    pref.getInt("userID",0),
                    Date(),
                    myLocate!!.latitude,
                    myLocate!!.longitude
                ) ?: throw Exception()
            }
            withContext(Dispatchers.Main){
                val response = deferred.await()

                println("[/AKUBI/]AkubiResponse--------------")
                println("comboCount : ${response.comboCount}")
                println("distance : ${response.distance}")
                println("akubis : ")
                response.akubis.forEach{
                    println("[userid : ${it.user_id},yawned_at : ${it.yawned_at},lat : ${it.latitude},long : ${it.longitude}")
                    pastPoints.add(GeoPoint(it.latitude,it.longitude))
                }
                lastYawnedAt = response.lastYawnedAt
                println("last_yawned_at : ${response.lastYawnedAt}")
                println("AkubiResponseEND-----------")
                update(map,comboTextView)
            }
        }
    }
    private fun combo(map: MapView ,comboTextView: TextView){
        val pref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        GlobalScope.launch {
            val deferred = async(Dispatchers.IO){
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
                HttpClient().combo(
                    pref.getInt("userID",0),
                    sdf.parse(lastYawnedAt)?:null
                ) ?: throw Exception()
            }
            withContext(Dispatchers.Main){
                val response = deferred.await()

                println("[/COMBO/]AkubiResponse--------------")
                println("comboCount : ${response.comboCount}")
                println("distance : ${response.distance}")
                distance += response.distance
                println("akubis : ")
                response.akubis.forEach{
                    println("[userid : ${it.user_id},yawned_at : ${it.yawned_at},lat : ${it.latitude},long : ${it.longitude}")
                    myPoints.add(GeoPoint(it.latitude,it.longitude))
                }
                lastYawnedAt = response.lastYawnedAt
                println("last_yawned_at : ${response.lastYawnedAt}")
                println("AkubiResponseEND-----------")
                update(map,comboTextView)
                if(response.akubis.count() == 0){
                    stopDegreeProgressBar()
                    ResultDialogFragment.create(myPoints.size,distance)
                        .show(supportFragmentManager,ResultDialogFragment::class.simpleName)
                }
            }
        }
    }
    private fun update(map: MapView ,comboTextView: TextView){
        drawAllPointsAndLines(map)
        val area = getArea()
        map.zoomToBoundingBox(area,true)
        comboTextView.text = "${myPoints.size} Combo!"
        VibrationManager(this).singleVibrates(min(myPoints.size,10))
        progressBar.progress = 100
    }
    private fun drawAllPointsAndLines(map:MapView){
        map.overlays.clear()
        drawPastPointsWithLine(map)
        drawConnectLineResentPastPointAndMyFirstPoint(map)
        drawMyPointsWithLine(map)
    }
    private fun drawPastPointsWithLine(map: MapView){
        if(pastPoints.isEmpty()) return

        val line = Polyline()
        pastPoints.forEach{
            addMarker(map,it,Color.BLUE)
        }
        line.setPoints(pastPoints)
        line.outlinePaint.color = Color.BLUE
        map.overlays.add(line)
    }
    private fun drawConnectLineResentPastPointAndMyFirstPoint(map: MapView){
        if(pastPoints.count() > 0){
            val line = Polyline()
            line.setPoints(listOf(pastPoints[pastPoints.size-1],myPoints[0]))
            line.outlinePaint.color = Color.BLUE
            map.overlays.add(line)
        }
    }
    private fun drawMyPointsWithLine(map: MapView){
        if(myPoints.isEmpty()) return

        val line = Polyline()
        myPoints.forEach{
            addMarker(map,it,Color.RED)
        }
        line.setPoints(myPoints)
        line.outlinePaint.color = Color.RED
        map.overlays.add(line)
    }
    private fun startDegreeProgressBar(){
        timer = Timer()
        val timerTask = DegreeProgressTimerTask()
        timer.schedule(timerTask,0,100)
    }
    private fun stopDegreeProgressBar(){
        timer.cancel()
    }
    private fun addMarker(map:MapView ,geoPoint: GeoPoint,color:Int){
        val marker = Marker(map)
        marker.position = geoPoint
        val icon = ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_adjust_24,null)
        icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(color,
            BlendModeCompat.SRC_ATOP)
        marker.icon = icon
        marker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_CENTER)
        map.overlays.add(marker)
    }
    private fun getArea() : BoundingBox {
        var north : Double = Double.MIN_VALUE
        var south : Double = Double.MAX_VALUE
        var east : Double = Double.MIN_VALUE
        var west : Double = Double.MAX_VALUE
        pastPoints.forEach{
            val latitude = it.latitude
            val longitude = it.longitude
            north = java.lang.Double.max(latitude, north)
            south = java.lang.Double.min(latitude, south)
            west = java.lang.Double.min(longitude, west)
            east = java.lang.Double.max(longitude, east)
        }
        myPoints.forEach{
            val latitude = it.latitude
            val longitude = it.longitude
            north = java.lang.Double.max(latitude, north)
            south = java.lang.Double.min(latitude, south)
            west = java.lang.Double.min(longitude, west)
            east = java.lang.Double.max(longitude, east)
        }
        println("n:$north e:$east s:$south w: $west")
        val abs1 = (abs(north) - abs(south))/5
        val abs2 = (abs(east) - abs(west))/5
        return BoundingBox(north+abs1, east+abs2, south-abs1, west-abs2)
    }

    inner class DegreeProgressTimerTask : TimerTask() {
        override fun run() {
            val handler = Handler(Looper.getMainLooper())
            handler.post(Runnable {
                progressBar.progress -= 2
                if(progressBar.progress == 0){
                    combo(map,comboTextView)
                    progressBar.progress = 100
                }
            })
        }
    }


    //位置情報を取得
    private fun locationStart(){
        mLocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == 0){
            when {
                mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> {
                    myLocate = mLocationManager!!.getLastKnownLocation("gps")
                    mLocationManager.requestLocationUpdates("gps",1000,10F,this)
                }
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> {
                    myLocate = mLocationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,10F,this)
                }
                else -> {
                    //GPSが取れなかった時の処理
                    return
                }
            }
        }
    }
    override fun onLocationChanged(location: Location) {
        myLocate = location
    }
}
