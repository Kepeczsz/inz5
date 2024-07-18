package com.example.final_finalapp.game.composition

import androidx.compose.runtime.compositionLocalOf
import com.example.final_finalapp.game.GameManager
import com.example.final_finalapp.game.GameRound
import com.example.final_finalapp.interaction.BoardInteraction

val LocalGameSession = compositionLocalOf { GameManager() }
val LocalBoardInteraction = compositionLocalOf { BoardInteraction(GameRound()) }