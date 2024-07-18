package com.example.final_finalapp.extensions

import com.example.final_finalapp.R
import com.example.final_finalapp.game.pieces.Piece
import com.example.final_finalapp.game.pieces.PieceType
import com.example.final_finalapp.game.Side

fun Piece.drawableResource(): Int {
    val isWhite = getPieceSide() == Side.WHITE
    return when (getPieceType()!!) {
        PieceType.BISHOP -> if (isWhite) R.drawable.piece_wb else R.drawable.piece_bb
        PieceType.KING -> if (isWhite) R.drawable.piece_wk else R.drawable.piece_bk
        PieceType.KNIGHT -> if (isWhite) R.drawable.piece_wn else R.drawable.piece_bn
        PieceType.PAWN -> if (isWhite) R.drawable.piece_wp else R.drawable.piece_bp
        PieceType.QUEEN -> if (isWhite) R.drawable.piece_wq else R.drawable.piece_bq
        PieceType.ROOK -> if (isWhite) R.drawable.piece_wr else R.drawable.piece_br
        PieceType.DUCK -> R.drawable.duck
        PieceType.NONE -> throw Error("Unknown piece")
    }
}