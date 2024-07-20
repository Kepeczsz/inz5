package com.example.final_finalapp.game.chessBoard

import android.util.Log
import com.example.final_finalapp.game.Side
import com.example.final_finalapp.game.moves.Move
import com.example.final_finalapp.game.pieces.Piece
import com.example.final_finalapp.game.pieces.PieceType

class Board {
    fun setBoard(): Array<Array<Piece>> {
        val startPieces = arrayOf(
            arrayOf(
                Piece.WHITE_ROOK, Piece.WHITE_KNIGHT, Piece.WHITE_BISHOP, Piece.WHITE_QUEEN,
                Piece.WHITE_KING, Piece.WHITE_BISHOP, Piece.WHITE_KNIGHT, Piece.WHITE_ROOK
            ),
            arrayOf(
                Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN,
                Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN, Piece.WHITE_PAWN
            ),
            arrayOf(
                Piece.NONE, Piece.DUCK, Piece.NONE, Piece.NONE,
                Piece.NONE, Piece.NONE, Piece.NONE, Piece.NONE
            ),
            arrayOf(
                Piece.NONE, Piece.NONE, Piece.NONE, Piece.NONE,
                Piece.NONE, Piece.NONE, Piece.NONE, Piece.NONE
            ),
            arrayOf(
                Piece.NONE, Piece.NONE, Piece.NONE, Piece.NONE,
                Piece.NONE, Piece.NONE, Piece.NONE, Piece.NONE
            ),
            arrayOf(
                Piece.NONE, Piece.NONE, Piece.NONE, Piece.NONE,
                Piece.NONE, Piece.NONE, Piece.NONE, Piece.NONE
            ),
            arrayOf(
                Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN,
                Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN, Piece.BLACK_PAWN
            ),
            arrayOf(
                Piece.BLACK_ROOK, Piece.BLACK_KNIGHT, Piece.BLACK_BISHOP, Piece.BLACK_QUEEN,
                Piece.BLACK_KING, Piece.BLACK_BISHOP, Piece.BLACK_KNIGHT, Piece.BLACK_ROOK
            )
        )
        return startPieces
    }


    fun isInBounds(row: Int, col: Int): Boolean {
        return row in 0..7 && col in 0..7
    }

    fun legalMoves(startPieces: Array<Array<Piece>>): List<Move> {
        val moves = mutableListOf<Move>()

        for (row in startPieces.indices) {
            for (col in startPieces[row].indices) {
                val piece = startPieces[row][col]
                when (piece) {
                    Piece.WHITE_PAWN -> moves.addAll(pawnMoves(startPieces, row, col, Side.WHITE))
                    Piece.BLACK_PAWN -> moves.addAll(pawnMoves(startPieces, row, col, Side.BLACK))
                    else -> {}
                }
            }
        }

        return moves
    }


    fun pawnMoves(board: Array<Array<Piece>>, row: Int, col: Int, side: Side): List<Move> {
        val moves = mutableListOf<Move>()
        val direction = if (side == Side.WHITE) 1 else -1
        val startingRow = if (side == Side.WHITE) 1 else 6

        val newRow = row + direction
        if (isInBounds(newRow, col) && board[newRow][col] == Piece.NONE) {
            moves.add(Move(row, col, newRow, col))
            if (row == startingRow) {
                val twoRow = row + 2 * direction
                if (isInBounds(twoRow, col) && board[twoRow][col] == Piece.NONE) {
                    moves.add(Move(row, col, twoRow, col))
                }
            }
        }

        val captureCols = arrayOf(col - 1, col + 1)
        for (captureCol in captureCols) {
            val captureRow = row + direction
            if (isInBounds(captureRow, captureCol)) {
                val targetPiece = board[captureRow][captureCol]
                if (targetPiece != Piece.NONE && targetPiece.getPieceSide() != side) {
                    moves.add(Move(row, col, captureRow, captureCol))
                }
            }
        }

        return moves
    }

    fun getCapturedPieces() {}
}
