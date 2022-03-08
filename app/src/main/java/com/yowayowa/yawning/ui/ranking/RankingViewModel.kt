package com.yowayowa.yawning.ui.ranking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yowayowa.yawning.ComboRanking

class RankingViewModel : ViewModel() {
    private val comboRankings:MutableLiveData<List<ComboRanking>> by lazy{
        MutableLiveData<List<ComboRanking>>().also {
            it.value = loadComboRanking()
        }
    }
    fun getComboRankings():LiveData<List<ComboRanking>>{
        return comboRankings
    }
    private fun loadComboRanking(): List<ComboRanking> {
        val ria = ComboRanking(1, 3, "ria")
        val yuhi = ComboRanking(2, 2, "yuhi")
        val awa = ComboRanking(3, 1, "awa")
        return listOf(ria, yuhi, awa)
    }
}
