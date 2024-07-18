package com.example.final_finalapp.game.chessBoard

import com.example.final_finalapp.game.pieces.Piece

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

}
