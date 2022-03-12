package com.yowayowa.yawning

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
