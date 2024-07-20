package com.example.final_finalapp.game.chessBoard

import com.example.final_finalapp.game.pieces.Piece

data class CapturedPiece(val piece: Piece, val coordinates: Pair<Int, Int>)