package com.yowayowa.yawning

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.yowayowa.yawning.databinding.ActivityMapBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    private val jpZeroPoint = GeoPoint(36.0047,137.5936)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMap()
    }

    private fun initMap(){
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        val map = binding.map
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.minZoomLevel = 3.0
        map.maxZoomLevel = 15.0
        map.setMultiTouchControls(true)//マップの2本指でのピンチアウト
        map.setLayerType(View.LAYER_TYPE_SOFTWARE,null)
        val mapController = binding.map.controller
        mapController.setZoom(9.0)
        mapController.setCenter(jpZeroPoint)
        addMarker(map,jpZeroPoint, Color.BLUE)
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
}

