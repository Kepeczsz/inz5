package com.example.final_finalapp.extensions

import androidx.compose.ui.geometry.Offset
import com.example.final_finalapp.game.chessBoard.BoardLayout
import com.example.final_finalapp.game.moves.Move
import com.example.final_finalapp.game.moves.SquareCoordinates

fun SquareCoordinates.relevantToMove(move: Move): Boolean {
    return this.row == move.fromRow ||
            this.col == move.fromCol ||
            this.row == move.toRow ||
            this.col == move.toCol
}

context(BoardLayout)
fun SquareCoordinates.topLeft(): Offset {
    return topLeft(isWhite, squareSize)
}

fun SquareCoordinates.topLeft(isWhite: Boolean, squareSize: Float): Offset {
    return if (isWhite) {
        val x = col * squareSize
        val y = (7 - row) * squareSize
        Offset(x, y)
    } else {
        val x = (7 - col) * squareSize
        val y = row * squareSize
        Offset(x, y)
    }
}