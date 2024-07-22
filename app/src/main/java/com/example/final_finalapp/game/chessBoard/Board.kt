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
                    Piece.WHITE_ROOK -> moves.addAll(rookMoves(startPieces, row, col, Side.WHITE))
                    Piece.BLACK_ROOK -> moves.addAll(rookMoves(startPieces, row, col, Side.BLACK))
                    Piece.WHITE_KNIGHT -> moves.addAll(knightMoves(startPieces, row, col, Side.WHITE))
                    Piece.BLACK_KNIGHT -> moves.addAll(knightMoves(startPieces, row, col, Side.BLACK))
                    Piece.WHITE_BISHOP -> moves.addAll(bishopMoves(startPieces, row, col, Side.WHITE))
                    Piece.BLACK_BISHOP -> moves.addAll(bishopMoves(startPieces, row, col, Side.BLACK))
                    Piece.WHITE_QUEEN -> moves.addAll(queenMoves(startPieces, row, col, Side.WHITE))
                    Piece.BLACK_QUEEN -> moves.addAll(queenMoves(startPieces, row, col, Side.BLACK))
                    Piece.WHITE_KING -> moves.addAll(kingMoves(startPieces, row, col, Side.WHITE))
                    Piece.BLACK_KING -> moves.addAll(kingMoves(startPieces, row, col, Side.BLACK))
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
                if (targetPiece != Piece.NONE && targetPiece != Piece.DUCK && targetPiece.getPieceSide() != side) {
                    moves.add(Move(row, col, captureRow, captureCol))
                }
            }
        }

        return moves
    }


    fun knightMoves(board: Array<Array<Piece>>, row: Int, col: Int, side: Side): List<Move> {
        val moves = mutableListOf<Move>()
        val directions = listOf(
            Pair(-2, -1), Pair(-2, 1), Pair(-1, -2), Pair(-1, 2),
            Pair(1, -2), Pair(1, 2), Pair(2, -1), Pair(2, 1)
        )

        for (direction in directions) {
            val newRow = row + direction.first
            val newCol = col + direction.second

            if (isInBounds(newRow, newCol)) {
                val targetPiece = board[newRow][newCol]
                if (targetPiece == Piece.NONE || targetPiece.getPieceSide() != side) {
                    moves.add(Move(row, col, newRow, newCol))
                }
            }
        }

        return moves
    }

    fun bishopMoves(board: Array<Array<Piece>>, row: Int, col: Int, side: Side): List<Move> {
        val moves = mutableListOf<Move>()

        val directions = listOf(Pair(-1, -1), Pair(-1, 1), Pair(1, -1), Pair(1, 1))

        for (direction in directions) {
            var newRow = row + direction.first
            var newCol = col + direction.second

            while (isInBounds(newRow, newCol)) {
                val targetPiece = board[newRow][newCol]
                if (targetPiece == Piece.NONE) {
                    moves.add(Move(row, col, newRow, newCol))
                } else {
                    if (targetPiece.getPieceSide() != side) {
                        moves.add(Move(row, col, newRow, newCol))
                    }
                    break
                }
                newRow += direction.first
                newCol += direction.second
            }
        }

        return moves
    }

    fun rookMoves(board: Array<Array<Piece>>, row: Int, col: Int, side: Side): List<Move> {
        val moves = mutableListOf<Move>()

        // Directions: up, down, left, right
        val directions = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))

        for (direction in directions) {
            var newRow = row + direction.first
            var newCol = col + direction.second

            while (isInBounds(newRow, newCol)) {
                val targetPiece = board[newRow][newCol]
                if (targetPiece == Piece.NONE) {
                    moves.add(Move(row, col, newRow, newCol))
                } else {
                    if (targetPiece.getPieceSide() != side) {
                        moves.add(Move(row, col, newRow, newCol))
                    }
                    break
                }
                newRow += direction.first
                newCol += direction.second
            }
        }

        return moves
    }

    fun queenMoves(board: Array<Array<Piece>>, row: Int, col: Int, side: Side): List<Move> {
        val moves = mutableListOf<Move>()
        moves.addAll(rookMoves(board, row, col, side))
        moves.addAll(bishopMoves(board, row, col, side))
        return moves
    }

    fun kingMoves(board: Array<Array<Piece>>, row: Int, col: Int, side: Side): List<Move> {
        val moves = mutableListOf<Move>()
        val directions = listOf(
            Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
            Pair(0, -1), /* King */ Pair(0, 1),
            Pair(1, -1), Pair(1, 0), Pair(1, 1)
        )

        for (direction in directions) {
            val newRow = row + direction.first
            val newCol = col + direction.second

            if (isInBounds(newRow, newCol)) {
                val targetPiece = board[newRow][newCol]
                if (targetPiece == Piece.NONE || targetPiece.getPieceSide() != side) {
                    moves.add(Move(row, col, newRow, newCol))
                }
            }
        }

        return moves
    }


    fun getCapturedPieces() {}
}
