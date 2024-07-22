package com.example.final_finalapp.game.pieces

import android.util.Log
import com.example.final_finalapp.extensions.topLeft
import com.example.final_finalapp.game.chessBoard.BoardLayout
import com.example.final_finalapp.game.moves.Move

object UpdateChessPieceState {
    context(BoardLayout)
    operator fun invoke(
        state: ChessPieceState,
        move: Move,
    ): ChessPieceState {
        val newState = state.copy()

        move(move, state, newState, onNoChanges = { return state })
        newState.squareOffset = newState.squareCoordinates.topLeft()

        return newState
    }
}

context(BoardLayout)
private inline fun move(
    move: Move,
    state: ChessPieceState,
    newState: ChessPieceState,
    onNoChanges: () -> Unit,
) {

    if (newState.piece != Piece.NONE && newState.piece.getPieceSide() != state.piece.getPieceSide()) {
        newState.captured = true
        newState.squareCoordinates.col = move.toCol
        newState.squareCoordinates.row = move.toRow
    }
    if (!state.captured) {
        if (move.fromCol == state.squareCoordinates.col && move.fromRow == state.squareCoordinates.row) {
            newState.squareCoordinates.col = move.toCol
            newState.squareCoordinates.row = move.toRow
        }
    }
}
