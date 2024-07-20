package com.example.final_finalapp.game.chessBoard

import Pieces
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReusableContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.final_finalapp.game.Side
import com.example.final_finalapp.game.composition.LocalBoardInteraction

@Composable
fun ChessBoard(modifier: Modifier = Modifier,
               boardWidth: Float
){
    val boardInteraction = LocalBoardInteraction.current
    val perspective by boardInteraction.perspectiveChanges().collectAsState(boardInteraction.perspective())

    val boardLayout = rememberBoardLayout(
        perspective = perspective,
        boardWidth = boardWidth,
    )
    
    LaunchedEffect(boardLayout){
     boardInteraction.updateSquarePositions(boardLayout.squareSize)
    }

    Log.d("Perspective", perspective.toString())
    ReusableContent(key = perspective) {
        Box(modifier = modifier) {
            boardLayout.run {
                Squares()
                Pieces()
            }
        }
    }
}