package com.yowayowa.yawning

data class AkubiResponse(
    val userID : Int,
    val comboCount : Int,
    val distance : Double,
    val akubis : List<Akubi>,
    val lastYawnedAt : String?
)
