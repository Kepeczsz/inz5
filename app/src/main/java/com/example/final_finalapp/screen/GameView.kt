package com.example.final_finalapp.screen

import android.util.Log
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReusableContent
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.example.final_finalapp.game.GameRound
import com.example.final_finalapp.game.chessBoard.ChessBoard
import com.example.final_finalapp.game.composition.LocalBoardInteraction
import com.example.final_finalapp.game.composition.LocalGameSession
import com.example.final_finalapp.game.composition.StableHolder
import com.example.final_finalapp.game.moves.Move
import com.example.final_finalapp.interaction.BoardInteraction
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GameView(
    gameHolder: StableHolder<GameRound>,
    onMove: (Move) -> Unit,
    boardWidth: @Composable () -> Float = { LocalView.current.width.toFloat() },
){
    val (game) = gameHolder
    val sessionManager = LocalGameSession.current

    Log.d("Game id:", "game id ${game.id}")
    val boardInteraction = remember(game.id) { BoardInteraction(game) }

    LaunchedEffect(game) {
        sessionManager.updateSession(game)
        Log.d("Session updated", sessionManager.updateSession(game).toString())
        boardInteraction.moves().collectLatest {
            onMove(it)
        }

    }

    ReusableContent(key = game.id) {
        CompositionLocalProvider(LocalBoardInteraction provides boardInteraction) {
            ChessBoard(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 1f),
                boardWidth = boardWidth(),
            )
        }
    }
}