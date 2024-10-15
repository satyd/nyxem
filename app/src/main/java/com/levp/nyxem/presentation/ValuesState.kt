package com.levp.nyxem.presentation

data class ValuesState(
    val attackDamage: String,
    val targetMaxMP: String,
    val targetMagResist: String,
    val targetPhysResist: String,
    val selfMagicAmplify: String,
) {
    companion object {
        fun initialState(): ValuesState = ValuesState(
            attackDamage = "120",
            targetMaxMP = "500",
            targetMagResist = "25",
            targetPhysResist = "25",
            selfMagicAmplify = "0",

        )
    }
}
