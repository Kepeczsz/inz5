package com.example.final_finalapp.game

import android.annotation.SuppressLint
import android.util.Log
import com.example.final_finalapp.game.chessBoard.Board
import com.example.final_finalapp.game.chessBoard.CapturedPiece
import com.example.final_finalapp.game.moves.Move
import com.example.final_finalapp.game.pieces.Piece
import com.example.final_finalapp.game.pieces.PieceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

open class  GameRound(
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
    private val terminationReason = MutableStateFlow<GameTerminationReason?>(null)

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
        return if (termination() != null) {
            MoveResult.GameEnded
        } else {
            val piece = startPieces[move.fromRow][move.fromCol]
            val targetPiece = startPieces[move.toRow][move.toCol]

            if (targetPiece != Piece.NONE) {
                startPieces[move.toRow][move.toCol] = Piece.NONE
                capturedPieces.value += CapturedPiece(targetPiece, Pair(move.toRow, move.toCol))
                updateTermination(capturedPieces)
            }

            startPieces[move.fromRow][move.fromCol] = Piece.NONE
            startPieces[move.toRow][move.toCol] = piece

            Log.d("board:", getPieces())

            history.add(move)
            moves.emit(move)
            capturedPieces.emit(capturedPieces.value)
            return MoveResult.Moved
        }
    }

    fun legalMoves() : List<Move> {
        Log.d("Legal moves", board.legalMoves(startPieces).toList().toString() )
       return  board.legalMoves(startPieces).toList()
    }

    fun moves(): Flow<Move> {
        Log.d("GameRound", "moves() flow called")
     return   moves.filterNotNull()
    }
    open suspend fun resign() {
        terminationReason.emit(GameTerminationReason(resignation = selfSide))
    }

    fun termination(): GameTerminationReason? {
        return terminationReason.value
    }
    suspend fun awaitTermination(): GameTerminationReason {
        return terminationReason.filterNotNull().first()
    }

    fun captureUpdates(): MutableStateFlow<List<CapturedPiece>> = capturedPieces

    @SuppressLint("SuspiciousIndentation")
    private fun updateTermination(capturedPiece: MutableStateFlow<List<CapturedPiece>>) {
        val isCaptured = capturedPiece.value

        val whiteKingCaptured = isCaptured.any { it.piece.getPieceType() == PieceType.KING && it.piece.side == Side.WHITE }
        val blackKingCaptured = isCaptured.any { it.piece.getPieceType() == PieceType.KING && it.piece.side == Side.BLACK }

        if (whiteKingCaptured) {
            terminationReason.tryEmit(
                GameTerminationReason(
                    sideMated = Side.WHITE,
                    draw = false,
                )
            )
        } else if (blackKingCaptured) {
            terminationReason.tryEmit(
                GameTerminationReason(
                    sideMated = Side.BLACK,
                    draw = false,
                )
            )
        }
    }
}