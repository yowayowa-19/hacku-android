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
        val user1 = ComboRanking(1, 27, "user1")
        val user2 = ComboRanking(2, 22, "user2")
        val user3 = ComboRanking(3, 21, "user3")
        val user4 = ComboRanking(4, 21, "user4")
        val user5 = ComboRanking(5, 14, "user5")
        val user6 = ComboRanking(6, 12, "user6")
        val user7 = ComboRanking(7, 10, "user7")
        val user8 = ComboRanking(8, 5, "user8")
        val user9 = ComboRanking(9, 3, "user9")
        val user10 = ComboRanking(10, 3, "user10")
        return listOf(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10)
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
        val user1 = DistanceRanking(1, 27.42, "user1")
        val user2 = DistanceRanking(2, 25.1, "user2")
        val user3 = DistanceRanking(3, 24.75, "user3")
        val user4 = DistanceRanking(4, 21.15, "user4")
        val user5 = DistanceRanking(5, 17.12, "user5")
        val user6 = DistanceRanking(6, 16.8, "user6")
        val user7 = DistanceRanking(7, 15.2, "user7")
        val user8 = DistanceRanking(8, 5.1, "user8")
        val user9 = DistanceRanking(9, 3.5, "user9")
        val user10 = DistanceRanking(10, 3.2, "user10")
        return listOf(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10)
    }
}
