import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.final_finalapp.extensions.toDp
import com.example.final_finalapp.game.chessBoard.BoardLayout
import com.example.final_finalapp.game.composition.LocalGameSession
import com.example.final_finalapp.game.pieces.Piece
import kotlin.math.roundToInt

context(BoardLayout)
@Composable
fun Pieces(
    modifier: Modifier = Modifier
) {
    val gameManager = LocalGameSession.current
    val game by gameManager.sessionUpdates().collectAsState(null)
    val pieces = remember(game?.id) { game?.piecesAtVariationStart() ?: emptyArray() }

    pieces.forEachIndexed { row, rowPieces ->
        rowPieces.forEachIndexed { col, piece ->
            if (piece.getPieceType() != null) {
                val initialX = col * squareSize
                val initialY = row * squareSize

                val painter = getPainter(piece)
                painter?.let {
                    DraggablePiece(piece, it, initialX, initialY, squareSize)
                }
            }
        }
    }
}

@Composable
private fun DraggablePiece(
    piece: Piece,
    painter: Painter,
    initialX: Float,
    initialY: Float,
    squareSize: Float
) {
    var offsetX by remember { mutableStateOf(initialX) }
    var offsetY by remember { mutableStateOf(initialY) }
    var dragging by remember { mutableStateOf(false) }

    val imageSizeDp = squareSize.toDp()
    val imageModifier = Modifier
        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
        .size(imageSizeDp)
        .pointerInput(Unit) {
            detectDragGestures(
                onDragStart = {
                    dragging = true
                },
                onDragEnd = {
                    dragging = false
                    // Update piece position on the board
                    // Use your game logic to update the piece's position
                },
                onDragCancel = {
                    dragging = false
                }
            ) { change, dragAmount ->
                change.consume()
                offsetX += dragAmount.x
                offsetY += dragAmount.y
            }
        }

    Image(
        painter = painter,
        contentDescription = null,
        modifier = imageModifier,
        contentScale = ContentScale.Fit
    )
}
