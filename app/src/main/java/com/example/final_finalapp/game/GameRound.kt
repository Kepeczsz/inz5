package com.example.final_finalapp.game

import com.example.final_finalapp.game.chessBoard.Board
import com.example.final_finalapp.game.pieces.Piece

open class GameRound(
    val id: String = "",
    val self: String = "Me",
    val opponent: String ="opponent",
    val selfSide: Side = Side.WHITE
) {
    private lateinit var startPieces: Array<Array<Piece>>
    private lateinit var board: Board

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
}