package com.yowayowa.yawning.ui.pro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Pro Fragment"
    }
    val text: LiveData<String> = _text
}
