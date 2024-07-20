package com.example.final_finalapp.interaction

import com.example.final_finalapp.game.moves.Move
import com.example.final_finalapp.game.moves.SquareCoordinates

sealed interface DropPieceResult {
    object None : DropPieceResult
    data class SelectPromotion(val promotions: List<Move>) : DropPieceResult
    data class Moved(val from: SquareCoordinates, val to: SquareCoordinates) : DropPieceResult
}
