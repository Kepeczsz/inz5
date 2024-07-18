package com.example.duchess.java.com.example.duchess.game.logic

import com.example.final_finalapp.game.GameRound
import kotlinx.coroutines.flow.StateFlow

interface GameRoundRepository {
    suspend fun activeGame(): StateFlow<GameRound?>
    suspend fun updateActiveGame(game: GameRound?)
}