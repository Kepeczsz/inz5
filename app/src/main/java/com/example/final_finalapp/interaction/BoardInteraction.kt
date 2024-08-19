package com.example.final_finalapp.interaction

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.example.final_finalapp.PlayerTurn
import com.example.final_finalapp.game.GameRound
import com.example.final_finalapp.game.Side
import com.example.final_finalapp.game.moves.Move
import com.example.final_finalapp.game.moves.SquareCoordinates
import com.example.final_finalapp.game.pieces.Piece
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlin.math.sqrt

class BoardInteraction (
    private val session : GameRound
    )
{
    private val perspective = MutableStateFlow(session.selfSide)
    private var squareSizePx:Float =0f
    private val squareCoordinates = mutableListOf<SquareCoordinates>()
    private var enableInteraction = true
    private val moves = MutableSharedFlow<Move>(replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private val target = MutableStateFlow(SquareCoordinates(-1, -1, Offset.Zero) )
    var currentTurn = Side.WHITE
    var moveDuck  = mutableStateOf(false)
    fun updateSquarePositions(squareSizePx: Float) {
        this.squareSizePx = squareSizePx
        squareCoordinates.clear()

        for (row in 0 until 8) {
            for (col in 0 until 8) {
                val offset = if (perspective.value == Side.WHITE) {
                    Offset(col * squareSizePx, (7 - row) * squareSizePx)
                } else {
                    Offset((7 - col) * squareSizePx, row * squareSizePx)
                }
                squareCoordinates.add(SquareCoordinates(row, col, offset))
            }
        }
    }
    fun updateDragPosition(position: Offset) {
        if (enableInteraction) {
            var closest: Pair<SquareCoordinates, Float>? = null
            for (squareCoordinate in squareCoordinates) {

                val (x1, y1) = squareCoordinate.offset

                val (x2, y2) = position

                val distance = sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))

                if ((closest == null || distance < closest.second) && distance <= squareSizePx) {
                    closest = squareCoordinate to distance
                }
            }
            val newTarget = closest?.first ?: SquareCoordinates(-1, -1, Offset.Zero)
            target.value = newTarget
        }
    }

    fun perspective(): Side = perspective.value
    fun dropPiece(piece: Piece, square: SquareCoordinates): DropPieceResult {
        if (enableInteraction) {
            if (target.value.row == -1 && target.value.col == -1) return DropPieceResult.None

            var result: DropPieceResult = DropPieceResult.None
            if(moveDuck.value && piece == Piece.DUCK){
                val move = Move(square.row, square.col, target.value.row, target.value.col)
                if(move in session.legalMoves()) {
                    moves.tryEmit(move)
                    result = DropPieceResult.Moved(square, target.value)
                }
                moveDuck.value = false
                currentTurn = currentTurn.flip()
                return result
            }


            if ((currentTurn == Side.WHITE && piece.getPieceSide() == Side.WHITE) ||
                (currentTurn == Side.BLACK && piece.getPieceSide() == Side.BLACK)) {
                Log.d("current turn", currentTurn.toString())
                val move = Move(square.row, square.col, target.value.row, target.value.col)
                if(move in session.legalMoves()) {
                    moves.tryEmit(move)
                    result = DropPieceResult.Moved(square, target.value)
                    moveDuck.value = true
                }
                releaseTarget()
            }

            Log.d("Result:", result.toString())

            return result
        } else {
            releaseTarget()
            Log.d("Result:", "NONE")
            return DropPieceResult.None
        }
    }
    private fun releaseTarget() {
        target.value = SquareCoordinates(-1, -1, Offset.Zero)
    }


    fun moves(): Flow<Move> {
        Log.d("EMITED MOVES FROM BOARD INTERACTION" , "True" )
        return moves.filter {
            it.fromRow != -1 && it.fromCol != -1 && it.toRow != -1 && it.toCol != -1
        }
    }

    fun perspectiveChanges(): Flow<Side> = perspective
}