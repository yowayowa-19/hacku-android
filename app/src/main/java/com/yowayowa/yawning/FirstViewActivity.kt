package com.yowayowa.yawning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yowayowa.yawning.databinding.ActivityFirstviewBinding

class FirstViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
