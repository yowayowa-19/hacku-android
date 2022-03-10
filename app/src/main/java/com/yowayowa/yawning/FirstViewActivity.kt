package com.yowayowa.yawning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yowayowa.yawning.databinding.ActivityFirstviewBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirstViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.registerButton.setOnClickListener{
            GlobalScope.launch {
                println(HttpClient().register("atria","awaawa"))
            }
        }
    }
}
