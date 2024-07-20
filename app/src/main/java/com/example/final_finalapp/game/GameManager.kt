package com.example.final_finalapp.game

import android.util.Log
import com.example.final_finalapp.game.chessBoard.CapturedPiece
import com.example.final_finalapp.game.pieces.Piece
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map


class GameManager {
    private val sessionUpdates = MutableStateFlow<GameRound?>(null)


    suspend fun updateSession(session: GameRound) {
        sessionUpdates.emit(session)
        Log.d("GameManager", "Session updated: ${session.getPieces()}")
    }

    fun sessionUpdates(): Flow<GameRound> = sessionUpdates.filterNotNull()

    fun moveUpdates() = sessionUpdates
        .filterNotNull()
        .flatMapLatest { it.moves() }


    fun captures(): Flow<List<CapturedPiece>> = sessionUpdates
        .filterNotNull()
        .flatMapLatest { it.captureUpdates() }


    fun pieces(): Array<Array<Piece>> = sessionUpdates.value?.pieces() ?: emptyArray()

}