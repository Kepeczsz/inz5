package com.example.duchess.java.com.example.duchess.game.logic


import com.example.final_finalapp.game.GameRound
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GameRoundRepositoryImpl @Inject constructor() : GameRoundRepository {

    private val activeSession = MutableStateFlow<GameRound?>(null)

    override suspend fun activeGame(): StateFlow<GameRound?> {
        return activeSession
    }

    override suspend fun updateActiveGame(game: GameRound?) {
        activeSession.value = game
    }
}