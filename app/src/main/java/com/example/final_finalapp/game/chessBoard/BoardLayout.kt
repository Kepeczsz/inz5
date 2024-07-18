package com.example.final_finalapp.game.chessBoard

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.final_finalapp.game.pieces.Piece
import com.example.final_finalapp.game.Side

@Stable
data class BoardLayout(val squareSizeDp: Dp = 0.dp,
                       val squareSize: Float = 0F,
                       val perspective: Side = Side.WHITE,
                       val getPainter: (Piece) -> Painter,
) {
    val isWhite get() = perspective == Side.WHITE
}