package com.example.final_finalapp.game.pieces

import com.example.final_finalapp.game.Side

enum class Piece(
    val side: Side?,
    val type: PieceType?,
    val fanSymbol: String,
    val fenSymbol: String
) {
    /**
     * White pawn piece.
     */
    WHITE_PAWN(Side.WHITE, PieceType.PAWN, "♙", "P"),
    /**
     * White knight piece.
     */
    WHITE_KNIGHT(Side.WHITE, PieceType.KNIGHT, "♘", "N"),
    /**
     * White bishop piece.
     */
    WHITE_BISHOP(Side.WHITE, PieceType.BISHOP, "♗", "B"),
    /**
     * White rook piece.
     */
    WHITE_ROOK(Side.WHITE, PieceType.ROOK, "♖", "R"),
    /**
     * White queen piece.
     */
    WHITE_QUEEN(Side.WHITE, PieceType.QUEEN, "♕", "Q"),
    /**
     * White king piece.
     */
    WHITE_KING(Side.WHITE, PieceType.KING, "♔", "K"),
    /**
     * Black pawn piece.
     */
    BLACK_PAWN(Side.BLACK, PieceType.PAWN, "♟", "p"),
    /**
     * Black knight piece.
     */
    BLACK_KNIGHT(Side.BLACK, PieceType.KNIGHT, "♞", "n"),
    /**
     * Black bishop piece.
     */
    BLACK_BISHOP(Side.BLACK, PieceType.BISHOP, "♝", "b"),
    /**
     * Black rook piece.
     */
    BLACK_ROOK(Side.BLACK, PieceType.ROOK, "♜", "r"),
    /**
     * Black queen piece.
     */
    BLACK_QUEEN(Side.BLACK, PieceType.QUEEN, "♛", "q"),
    /**
     * Black king piece.
     */
    BLACK_KING(Side.BLACK, PieceType.KING, "♚", "k"),

    /**
     *  Duck Piece
     */

    DUCK(Side.NEUTRAL, PieceType.DUCK, "🐤", "d"),
    /**
     * None piece.
     */
    NONE(null, null, "NONE", ".");

    companion object {
        private val fenToPiece: MutableMap<String, Piece> = HashMap(13)
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


    fun getSanSymbol(): String {
        return type?.sanSymbol ?: ""
    }

    fun getFanSymbol1(): String {
        return fanSymbol
    }

    fun getFenSymbol1(): String {
        return fenSymbol
    }
}
