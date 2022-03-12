package com.yowayowa.yawning

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val majorID: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    // 初期値を0に設定
    init{
        majorID.value = 1200
    }

    val myLocate: MutableLiveData<Location?> by lazy {
        MutableLiveData<Location?>()
    }
    // 初期値を0に設定
    init{
        myLocate.value = null
    }
}
