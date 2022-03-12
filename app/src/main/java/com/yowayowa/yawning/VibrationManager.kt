package com.yowayowa.yawning

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.FragmentActivity

class VibrationManager(activity: FragmentActivity?) {
    private val vibrator: Vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    fun singleVibrates(times : Int){
        val rhythm = mutableListOf<Long>()
        repeat(times){
            rhythm.add(0)
            rhythm.add(100)
        }
        val effect = VibrationEffect.createWaveform(rhythm.toLongArray(), -1)
        vibrator.vibrate(effect)
    }
}
