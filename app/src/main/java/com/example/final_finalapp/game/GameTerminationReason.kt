package com.example.final_finalapp.game


data class GameTerminationReason (
    val sideMated: Side? = null,
    val draw: Boolean = false,
    val resignation: Side? = null,
)