package com.yowayowa.yawning

import android.content.Context
import android.os.Vibrator
import androidx.fragment.app.FragmentActivity

class VibrationManager(activity: FragmentActivity?) {
    private val vibrator: Vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    fun singleVibrate(){
        vibrator.vibrate(100)
    }
}
