package com.fourteenrows.p2pcs.model

class User(
    val name: String,
    val surname: String,
    val mail: String,
    val exp: Long = 0,
    val gaia_coins: Long = 0,
    val week_points: Long = 0
)