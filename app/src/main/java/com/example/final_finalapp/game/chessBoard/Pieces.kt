
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import com.example.final_finalapp.extensions.relevantToMove
import com.example.final_finalapp.extensions.topLeft

import com.example.final_finalapp.game.chessBoard.BoardLayout
import com.example.final_finalapp.game.composition.LocalGameSession
import com.example.final_finalapp.game.moves.Move
import com.example.final_finalapp.game.moves.SquareCoordinates
import com.example.final_finalapp.game.pieces.ChessPiece
import com.example.final_finalapp.game.pieces.ChessPieceState
import com.example.final_finalapp.game.pieces.Piece
import com.example.final_finalapp.game.pieces.UpdateChessPieceState


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
                val boardHeight = 8 * squareSize
                val initialY = boardHeight - (row + 1) * squareSize
                ChessPiece(
                    initialState = calculatePieceState(
                    piece = piece,
                    initialSquare =SquareCoordinates(row, col, Offset( initialX,  initialY)),
                    history = game?.history ?: emptyList(),
                ),
                )

            }
        }
    }
}



context(BoardLayout)
private fun calculatePieceState(
    piece: Piece,
    initialSquare: SquareCoordinates,
    history: List<Move>
) = history.foldIndexed(
    initial = ChessPieceState(
        squareCoordinates = initialSquare,
        squareOffset = initialSquare.topLeft(),
        piece = piece
    ),
) { _, state, move ->
    if(state.squareCoordinates.relevantToMove(move)) {
        UpdateChessPieceState(state,move)
    } else {
        state
    }
}