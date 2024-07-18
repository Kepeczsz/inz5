package com.example.final_finalapp.game

import android.util.Log
import com.example.duchess.java.com.example.duchess.game.logic.GameRoundRepository
import java.util.UUID
import javax.inject.Inject

class StartGameImpl @Inject constructor(
    private val gameSessionRepository: GameRoundRepository,
) : StartGame {
    override suspend fun invoke(side: Side, player: String) {
        val game = GameRound(
            id = UUID.randomUUID().toString(),
            selfSide = side,
        )
        game.setBoard()

        gameSessionRepository.updateActiveGame(game)

    }
}