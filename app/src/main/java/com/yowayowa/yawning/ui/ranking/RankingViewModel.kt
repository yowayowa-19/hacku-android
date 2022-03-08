package com.yowayowa.yawning.ui.ranking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yowayowa.yawning.ComboRanking
import com.yowayowa.yawning.DistanceRanking

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
        val ria = ComboRanking(1, 3, "riaa")
        val yuhi = ComboRanking(2, 2, "yuhi")
        val awa = ComboRanking(3, 1, "awa")
        return listOf(ria, yuhi, awa)
    }
    private val distanceRankings:MutableLiveData<List<DistanceRanking>> by lazy{
        MutableLiveData<List<DistanceRanking>>().also {
            it.value = loadDistanceRanking()
        }
    }
    fun getDistanceRankings():LiveData<List<DistanceRanking>>{
        return distanceRankings
    }
    private fun loadDistanceRanking(): List<DistanceRanking> {
        val ria = DistanceRanking(1, 2.425, "riaa")
        val yuhi = DistanceRanking(2, 2.1, "yuhi")
        val awa = DistanceRanking(3, 1.75, "awa")
        return listOf(ria, yuhi, awa)
    }
}
