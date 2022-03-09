package com.yowayowa.yawning

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yowayowa.yawning.databinding.ActivityMapBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint


class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    private val jpZeroPoint = GeoPoint(36.0047,137.5936)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //osmdroid
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
    }
}
