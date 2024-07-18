package com.example.final_finalapp.screen

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReusableContent
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.example.final_finalapp.game.GameRound
import com.example.final_finalapp.game.chessBoard.ChessBoard
import com.example.final_finalapp.game.composition.LocalGameSession
import com.example.final_finalapp.game.composition.StableHolder
import com.example.final_finalapp.game.moves.Move
import com.example.final_finalapp.interaction.BoardInteraction

@Composable
fun GameView(
    gameHolder: StableHolder<GameRound>,
    onMove: (Move) -> Unit,
    boardWidth: @Composable () -> Float = { LocalView.current.width.toFloat() },
){
    val (game) = gameHolder
    val sessionManager = LocalGameSession.current
    val boardInteraction = remember(game.id) { BoardInteraction(game) }

    LaunchedEffect(game) {
        sessionManager.updateSession(game)

    }

    ReusableContent(key = game.id) {
        ChessBoard(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth()
                .aspectRatio(ratio = 1f),
            boardWidth = boardWidth(),
        )
    }

}