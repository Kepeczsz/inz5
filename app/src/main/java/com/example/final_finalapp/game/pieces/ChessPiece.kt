package com.example.final_finalapp.game.pieces

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.takeOrElse
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import com.example.final_finalapp.extensions.relevantToMove
import com.example.final_finalapp.extensions.toDp
import com.example.final_finalapp.game.chessBoard.BoardLayout
import com.example.final_finalapp.game.composition.LocalBoardInteraction
import com.example.final_finalapp.game.composition.LocalGameSession
import com.example.final_finalapp.interaction.DropPieceResult
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest

context(BoardLayout)
@Composable
fun ChessPiece(initialState: ChessPieceState){
    val gameManager = LocalGameSession.current
    val boardInteraction = LocalBoardInteraction.current


    var currentSize by remember { mutableStateOf(squareSize) }
    var state by remember { mutableStateOf(initialState) }
    var dragging by remember { mutableStateOf(false) }
    var pan by remember { mutableStateOf(Offset.Zero) }
    var isCaptured by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        gameManager.moveUpdates()
            .collectLatest {
                state = UpdateChessPieceState(state,it)
                pan = Offset.Zero
                currentSize = squareSize
            }
    }
    LaunchedEffect(Unit) {

        gameManager.captures().collectLatest { capturedPieces ->
            isCaptured = capturedPieces.any { it.piece == state.piece && it.coordinates.first == state.squareCoordinates.row  && it.coordinates.second == state.squareCoordinates.col}
            if (isCaptured) {

            }
            Log.d("lol", capturedPieces.toString())
        }
    }

    val animatedSize by animateDpAsState(currentSize.toDp(), label = "Piece Size")

    val position = remember(state, pan, dragging, animatedSize) {
        val sizeOffsetX = if (dragging) (animatedSize.value / 2f) else 0f
        val sizeOffsetY = if (dragging) (animatedSize.value * 2f) else 0f

        Offset(
            x = pan.x + state.squareOffset.x - sizeOffsetX,
            y = pan.y + state.squareOffset.y - sizeOffsetY,
        )
    }

    val animatedPan by animateOffsetAsState(
        targetValue = position,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "Piece Move",
    )

    val moving = animatedSize != currentSize.toDp() || animatedPan != position || dragging

    if (isCaptured) {
        return
    }

    Image(
        modifier = Modifier
            .semantics {
               // pieceSquare = SquarePieceTag(state.square, state.piece)
            }
            .size(animatedSize)
            .zIndex(if (moving) 1f else 0f)
            .offset(x = animatedPan.x.toDp(), y = animatedPan.y.toDp())
            .pointerInput(Unit) {
                coroutineScope {
                    while (true) {
                        awaitPointerEventScope {
                            val pointer = awaitFirstDown()
                            val newSize = squareSize * 1.7f
                            val squareCenter = Offset(squareSize / 2f, squareSize / 2f)

                            pan += Offset(
                                x = -(squareCenter.x - pointer.position.x),
                                y = -(squareCenter.y - pointer.position.y),
                            )


                            boardInteraction.updateDragPosition(pan + state.position)

                            currentSize = newSize
                            dragging = true

                            do {
                                val event = awaitPointerEvent()

                                pan += event
                                    .calculatePan()
                                    .takeOrElse { Offset.Zero }

                                val dragPosition = pan + state.squareOffset + squareCenter
                                boardInteraction.updateDragPosition(dragPosition)
                            } while (event.changes.none { it.changedToUp() })

                            val dropPan = currentEvent
                                .calculatePan()
                                .takeOrElse { Offset.Zero }

                            val dropPosition =
                                dropPan + pan + state.squareOffset + squareCenter

                            boardInteraction.updateDragPosition(dropPosition)

                            when (val result = boardInteraction.dropPiece(state.piece, state.squareCoordinates)) {
                                DropPieceResult.None -> {
                                    pan = Offset.Zero
                                    currentSize = squareSize
                                    dragging = false
                                }
//                                is DropPieceResult.SelectPromotion -> {
//                                    launch {
//                                        val promoted = boardInteraction.selectPromotion(result.promotions)
//                                        if (!promoted) {
//                                            pan = Offset.Zero
//                                            currentSize = squareSize
//                                        }
//                                        dragging = false
//                                    }
//                                }
                                else -> {
                                    dragging = false
                                }
                            }
                        }
                    }
                }
            },
        painter = getPainter(state.piece),
        contentDescription = state.piece.getPieceType().toString(),
    )
}