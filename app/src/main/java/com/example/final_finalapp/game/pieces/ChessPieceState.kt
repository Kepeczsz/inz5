package com.example.final_finalapp.game.pieces

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import com.example.final_finalapp.game.chessBoard.BoardLayout
import com.example.final_finalapp.game.moves.SquareCoordinates

@Stable
data class ChessPieceState(
    var squareCoordinates: SquareCoordinates,
    var squareOffset: Offset,
    var piece: Piece,
    var captured: Boolean = false
){
    context(BoardLayout)
    val position: Offset
        get() = squareCoordinates.offset + Offset(squareSize / 2f, squareSize / 2f)
}