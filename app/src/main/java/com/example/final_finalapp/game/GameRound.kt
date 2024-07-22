package com.example.final_finalapp.game

import android.util.Log
import com.example.final_finalapp.game.chessBoard.Board
import com.example.final_finalapp.game.chessBoard.CapturedPiece
import com.example.final_finalapp.game.moves.Move
import com.example.final_finalapp.game.pieces.Piece
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

open class GameRound(
    val id: String = "",
    val self: String = "Me",
    val opponent: String ="opponent",
    val selfSide: Side = Side.WHITE
) {
    private lateinit var startPieces: Array<Array<Piece>>
    private lateinit var board: Board
    private val moves = MutableStateFlow<Move?>(null)
    val history = mutableListOf<Move>()
    private val capturedPieces = MutableStateFlow<List<CapturedPiece>>(emptyList())
    var moveDuck : Boolean = false
    fun pieces(): Array<Array<Piece>> {
        return startPieces
    }

    fun getPieces() : String {
        val builder = StringBuilder()

        for (row in startPieces) {
            for (piece in row) {
                builder.append(piece.fenSymbol).append(" ")
            }
            builder.append("\n")
        }

        return builder.toString()
    }

    fun piecesAtVariationStart(): Array<Array<Piece>> {
        return startPieces
    }

    fun setBoard() {
        this.board =  Board()

        startPieces =  board.setBoard()
    }

    open suspend fun move(move: Move): MoveResult {
        val piece = startPieces[move.fromRow][move.fromCol]
        val targetPiece = startPieces[move.toRow][move.toCol]

        if (targetPiece != Piece.NONE) {
            startPieces[move.toRow][move.toCol] = Piece.NONE
            capturedPieces.value += CapturedPiece(targetPiece, Pair(move.toRow,move.toCol))
        }

        startPieces[move.fromRow][move.fromCol] = Piece.NONE
        startPieces[move.toRow][move.toCol] = piece

        Log.d("board:", getPieces())

        history.add(move)
        moves.emit(move)
        capturedPieces.emit(capturedPieces.value)
        return MoveResult.Moved
    }

    fun legalMoves() : List<Move> {
        Log.d("Legal moves", board.legalMoves(startPieces).toList().toString() )
       return  board.legalMoves(startPieces).toList()
    }

    fun moves(): Flow<Move> {
        Log.d("GameRound", "moves() flow called")
     return   moves.filterNotNull()
    }

    fun captureUpdates(): MutableStateFlow<List<CapturedPiece>> = capturedPieces
}