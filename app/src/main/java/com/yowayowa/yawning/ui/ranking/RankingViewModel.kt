package com.yowayowa.yawning.ui.ranking

import android.content.SharedPreferences
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.yowayowa.yawning.ComboRanking
import com.yowayowa.yawning.DistanceRanking
import com.yowayowa.yawning.HttpClient
import com.yowayowa.yawning.RankingResponse
import kotlinx.coroutines.*
import org.osmdroid.util.Distance

class RankingViewModel : ViewModel() {
    val userID: MutableLiveData<Int?> by lazy {
        MutableLiveData<Int?>()
    }
    // 初期値を0に設定
    init{
        userID.value = null
    }
    private val _rankingResponse = MutableLiveData<RankingResponse>()
    private val rankingResponse: LiveData<RankingResponse> get() = _rankingResponse
    fun getRankingResponse() = viewModelScope.launch {
        val deferred = withContext(Dispatchers.Default){
            HttpClient().ranking(userID.value?:throw Exception())?:throw Exception()
        }
        _rankingResponse.value = deferred
    }
    private val comboRankings:MutableLiveData<List<ComboRanking>> by lazy{
        MutableLiveData<List<ComboRanking>>().also {
            it.value = loadComboRanking()
        }
    }
    fun getComboRankings():LiveData<List<ComboRanking>>{
        return comboRankings
    }
    private fun loadComboRanking(): List<ComboRanking> {
        val result = mutableListOf<ComboRanking>()
        rankingResponse.value?.combo_ranking?.forEach {
            result.add(ComboRanking(it.rank,it.total_combo_count,it.first_id_name))
        }
        return result
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
        val result = mutableListOf<DistanceRanking>()
        rankingResponse.value?.distance_ranking?.forEach {
            result.add(DistanceRanking(it.rank,it.total_distance,it.first_id_name))
        }
        return result
    }

}
