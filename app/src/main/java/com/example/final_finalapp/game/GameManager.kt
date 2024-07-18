package com.example.final_finalapp.game

import com.example.final_finalapp.game.pieces.Piece
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull


class GameManager {
    private val sessionUpdates = MutableStateFlow<GameRound?>(null)

    fun pieces(): Array<Array<Piece>> = sessionUpdates.value?.pieces() ?: emptyArray()

    fun sessionUpdates(): Flow<GameRound> = sessionUpdates.filterNotNull()

    suspend fun updateSession(session: GameRound) {
        sessionUpdates.emit(session)
    }
}