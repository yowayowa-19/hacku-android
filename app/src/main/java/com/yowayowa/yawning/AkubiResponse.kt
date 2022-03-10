package com.yowayowa.yawning

data class AkubiResponse(
    val userID : Int,
    val comboCount : Int,
    val akubis : List<Akubi>,
    val lastYawnedAt : String?
)
