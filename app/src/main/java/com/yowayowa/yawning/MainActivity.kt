package com.yowayowa.yawning

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.yowayowa.yawning.databinding.ActivityMainBinding
import com.yowayowa.yawning.ui.pro.ProViewModel
import org.altbeacon.beacon.*
import org.altbeacon.beacon.service.IntentScanStrategyCoordinator

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var binding: ActivityMainBinding

    private val uuidString: String = "01020304-0506-0708-0910-111213141516"
    private val uuid = Identifier.parse(uuidString)
    private lateinit var beaconManager: BeaconManager
    private val IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
    private val region = Region("beacon", uuid, null, null)
    private var beaconMajorId: Int = 0
    private var beaconMinorId: Int = 0
    private lateinit var mainViewModel: MainViewModel
    lateinit var mLocationManager : LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        binding.toolbar.setupWithNavController(navController,appBarConfiguration)
        binding.toolbar.inflateMenu(R.menu.top_nav_menu)
        binding.toolbar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.navigation_settings -> startActivity(Intent(this,SettingsActivity::class.java))
            }
            true
        }
        navView.setupWithNavController(navController)
        checkLocationPermission()
        firstView()
        initBeaconManager()
        locationStart()
    }

    override fun onResume() {
        super.onResume()
        initBeaconManager()
    }

    private val permissionsRequestCode:Int = 1000;
    //????????????
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) { // ?????????????????????????????????????????????

            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
                permissionsRequestCode
            )
        }
    }

    //????????????????????????????????????????????????????????????????????????????????????????????????????????????
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionsRequestCode -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                } else {
                    Toast.makeText(applicationContext,"gps?????????????????????????????????????????????\n???????????????????????????????????????", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else ->{

            }
        }
    }

    fun firstView(){
        val pref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if(pref.getInt("userID",-1) == -1){
            val intent = Intent(this,FirstViewActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }


    private fun initBeaconManager() {
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))
        beaconManager.foregroundScanPeriod = 1000;
        beaconManager.addRangeNotifier(rangeNotifier)
        beaconManager.addMonitorNotifier(monitorNotifier)
        beaconManager.startMonitoring(region)
    }
    private val monitorNotifier = object : MonitorNotifier {
        override fun didEnterRegion(region: Region) {
            beaconManager.startRangingBeacons(region)
        }

        override fun didExitRegion(region: Region) {
            beaconManager.stopRangingBeacons(region)
        }

        override fun didDetermineStateForRegion(state: Int, region: Region) {
            if (state == 1) {
                beaconManager.startRangingBeacons(region)
            } else {
                beaconManager.stopRangingBeacons(region)
            }
        }
    }

    private val rangeNotifier = RangeNotifier { beacons, region ->
        if ((beacons == null) ||(!beacons.any())) return@RangeNotifier

        Log.d(IntentScanStrategyCoordinator.TAG, "Ranged: ${beacons.count()} beacons")
        beacons.sortedBy { beacon: Beacon -> beacon.distance }
        val nearestBeacon = beacons.first()
        if (nearestBeacon.distance < 100) {
            Log.d(IntentScanStrategyCoordinator.TAG, "InDistance: ${beacons.count()} beacons")
            putBeaconId(nearestBeacon.id2, nearestBeacon.id3)
            mainViewModel.majorID.value = beaconMajorId
            if(beaconMinorId == 0) //??????????????????
            else if(beaconMinorId == 1) {
                //??????????????????
                val intent = Intent(this, MapActivity::class.java)
                intent.putExtra("lat",mainViewModel.myLocate.value?.latitude)
                intent.putExtra("long",mainViewModel.myLocate.value?.longitude)
                startActivity(intent)
                //2??????????????????
                beaconManager.removeAllMonitorNotifiers()
                beaconManager.removeAllRangeNotifiers()
            }
            println(mainViewModel.majorID.value)
        }
    }
    private fun putBeaconId(identifier1: Identifier, identifier2: Identifier) {
        beaconMajorId = identifier1.toInt()
        beaconMinorId = identifier2.toInt()
    }

    //?????????????????????
    private fun locationStart(){
        mLocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == 0){
            when {
                mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> {
                    mainViewModel.myLocate.value = mLocationManager!!.getLastKnownLocation("gps")
                    mLocationManager.requestLocationUpdates("gps",1000,10F,this)
                }
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> {
                    mainViewModel.myLocate.value = mLocationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,10F,this)
                }
                else -> {
                    //GPS?????????????????????????????????
                    return
                }
            }
        }
    }
    override fun onLocationChanged(location: Location) {
        mainViewModel.myLocate.value = location
    }
}
