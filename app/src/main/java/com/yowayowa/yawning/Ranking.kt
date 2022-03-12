package com.yowayowa.yawning

data class Ranking(
    val contain_user_id:Boolean,
    val first_id:Int,
    val first_id_name: String,
    val end_id:Int,
    val total_combo_count:Int,
    val total_distance:Double,
    val rank:Int,
)

data class RankingResponse(
    val combo_ranking:List<Ranking>,
    val distance_ranking:List<Ranking>,
)


data class ComboRanking (
    val rank:Int,
    val combo_count:Int,
    val user_name:String,
)

data class DistanceRanking (
    val rank:Int,
    val distance:Double,
    val user_name:String,
)
