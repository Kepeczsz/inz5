package com.example.final_finalapp.game

interface StartGame {
    suspend operator fun invoke(side: Side, player: String)
}