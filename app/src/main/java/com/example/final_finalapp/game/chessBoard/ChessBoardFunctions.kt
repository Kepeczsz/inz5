package com.example.final_finalapp.game.chessBoard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.final_finalapp.extensions.drawableResource
import com.example.final_finalapp.extensions.toDp
import com.example.final_finalapp.game.pieces.Piece
import com.example.final_finalapp.game.Side


@Composable
fun rememberBoardLayout(
    boardWidth: Float,
    perspective: Side,
): BoardLayout {
    val squareSize = boardWidth / 8f
    val squareSizeDp = squareSize.toDp()
    val painters = remember { mutableMapOf<Piece, Painter>() }

    Piece.values().forEach { piece ->
        if (piece != Piece.NONE && !painters.containsKey(piece)) {
            painters[piece] = painterResource(piece.drawableResource())
        }
    }

    return remember(perspective) {
        BoardLayout(
            squareSizeDp = squareSizeDp,
            squareSize = squareSize,
            perspective = perspective,
            getPainter = { piece ->
                painters[piece] ?: error("Painter not found for piece: $piece")
            },
        )
    }
}