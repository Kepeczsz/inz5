package com.example.final_finalapp.game.pieces

import com.example.final_finalapp.game.Side

enum class Piece(
    val side: Side?,
    val type: PieceType?,
    val fanSymbol: String,
    val fenSymbol: String
) {
    WHITE_PAWN(Side.WHITE, PieceType.PAWN, "♙", "P"),
    WHITE_KNIGHT(Side.WHITE, PieceType.KNIGHT, "♘", "N"),
    WHITE_BISHOP(Side.WHITE, PieceType.BISHOP, "♗", "B"),
    WHITE_ROOK(Side.WHITE, PieceType.ROOK, "♖", "R"),
    WHITE_QUEEN(Side.WHITE, PieceType.QUEEN, "♕", "Q"),
    WHITE_KING(Side.WHITE, PieceType.KING, "♔", "K"),
    BLACK_PAWN(Side.BLACK, PieceType.PAWN, "♟", "p"),
    BLACK_KNIGHT(Side.BLACK, PieceType.KNIGHT, "♞", "n"),
    BLACK_BISHOP(Side.BLACK, PieceType.BISHOP, "♝", "b"),
    BLACK_ROOK(Side.BLACK, PieceType.ROOK, "♜", "r"),
    BLACK_QUEEN(Side.BLACK, PieceType.QUEEN, "♛", "q"),
    BLACK_KING(Side.BLACK, PieceType.KING, "♚", "k"),
    DUCK(Side.NEUTRAL, PieceType.DUCK, "🐤", "d"),
    NONE(null, null, "NONE", ".");

    companion object {

        private val fenToPiece: MutableMap<String, Piece> = HashMap()
        val allPieces: Array<Piece> = values()
        private val pieceMake: Array<Array<Piece>> = arrayOf(
            arrayOf(WHITE_PAWN, BLACK_PAWN),
            arrayOf(WHITE_KNIGHT, BLACK_KNIGHT),
            arrayOf(WHITE_BISHOP, BLACK_BISHOP),
            arrayOf(WHITE_ROOK, BLACK_ROOK),
            arrayOf(WHITE_QUEEN, BLACK_QUEEN),
            arrayOf(WHITE_KING, BLACK_KING),
            arrayOf(NONE, NONE, DUCK),
            arrayOf(NONE, NONE)
        )

        init {
            for (piece in values()) {
                fenToPiece[piece.fenSymbol] = piece
            }
        }

        fun fromValue(v: String): Piece {
            return valueOf(v)
        }

        fun make(side: Side, type: PieceType): Piece {
            return pieceMake[type.ordinal][side.ordinal]
        }

        fun fromFenSymbol(fenSymbol: String): Piece {
            return fenToPiece[fenSymbol]
                ?: throw IllegalArgumentException(String.format("Unknown piece '%s'", fenSymbol))
        }
    }

    fun value(): String {
        return name
    }

    fun getPieceType(): PieceType? {
        return type
    }

    fun getPieceSide(): Side? {
        return side
    }
}