package com.example.final_finalapp.interaction

import android.util.Log
import androidx.compose.ui.geometry.Offset
import com.example.final_finalapp.game.GameRound
import com.example.final_finalapp.game.Side
import com.example.final_finalapp.game.moves.Move
import com.example.final_finalapp.game.moves.SquareCoordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class BoardInteraction
    (private val session : GameRound )
{
    private val perspective = MutableStateFlow(session.selfSide)
    private var squareSizePx:Float =0f
    private val squareCoordinates = mutableListOf<SquareCoordinates>()

    fun updateSquarePositions(squareSizePx: Float) {
        this.squareSizePx = squareSizePx
        squareCoordinates.clear()

        for (row in 0 until 8) {
            for (col in 0 until 8) {
                val offset = if (perspective.value == Side.WHITE) {
                    Offset(col * squareSizePx, row * squareSizePx)
                } else {
                    Offset((7 - col) * squareSizePx, (7 - row) * squareSizePx)
                }
                squareCoordinates.add(SquareCoordinates(row, col, offset))
            }
        }
    }

}