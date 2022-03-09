package com.yowayowa.yawning

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.yowayowa.yawning.databinding.ActivityMapBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.MapView.OnFirstLayoutListener
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import kotlin.math.abs


class MapActivity : AppCompatActivity() {
    private lateinit var pastPoints : MutableList<GeoPoint>
    private lateinit var myPoints : MutableList<GeoPoint>
    private val jpZeroPoint = GeoPoint(36.0047,137.5936)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pastPoints = mutableListOf()
        myPoints = mutableListOf()
        initMap(binding.map)
        pastPoints.add(GeoPoint(42.0047,140.5936))
        pastPoints.add(GeoPoint(43.0047,143.0936))
        binding.map.addOnFirstLayoutListener{ view: View, i: Int, i1: Int, i2: Int, i3: Int ->
            update(binding.map,binding.comboTextView)
        }
        binding.textView.setOnClickListener{
            myPoints.add(GeoPoint(35.0047,137.0936))
            update(binding.map,binding.comboTextView)
        }
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
        mapController.setCenter(jpZeroPoint)
        myPoints.add(jpZeroPoint)
    }
    private fun update(map: MapView, comboTextView: TextView){
        drawAllPointsAndLines(map)
        val area = getArea()
        map.zoomToBoundingBox(area,true)
        comboTextView.text = "${myPoints.size} Combo!"
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
        val line = Polyline()
        line.setPoints(listOf(pastPoints[pastPoints.size-1],myPoints[0]))
        line.outlinePaint.color = Color.BLUE
        map.overlays.add(line)
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
}

