package com.example.final_finalapp.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiFlags
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReusableContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.final_finalapp.R
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
    onResign: () -> Unit = {},
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
            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart,
            ) {
                GameOptions(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onResignClicked = onResign,
                )
            }

        }
    }
}

@Composable
fun GameOptions(modifier: Modifier = Modifier,
                onResignClicked: () -> Unit,
                allowResign: Boolean = true
) {


    val boardInteraction = LocalBoardInteraction.current
    val game by LocalGameSession.current.sessionUpdates().collectAsState(GameRound())

    Row(modifier) {
        if (game.termination() == null && allowResign) {
            IconButton(
                onClick = { onResignClicked() },
            ) {
                Icon(
                    imageVector = Icons.Rounded.EmojiFlags,
                    contentDescription = stringResource(R.string.resign),
                )
            }
        }
    }
}
