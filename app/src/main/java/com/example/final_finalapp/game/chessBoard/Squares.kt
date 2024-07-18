package com.example.final_finalapp.game.chessBoard

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.final_finalapp.game.Side
import com.example.final_finalapp.ui.theme.provideChessBoardColors


context(BoardLayout)

@OptIn(ExperimentalTextApi::class)
@Composable
fun Squares (
    modifier: Modifier = Modifier,
    drawLabels: Boolean = true,
){
    val textMeasurer = rememberTextMeasurer()
    val boardColors = provideChessBoardColors()

    val labelText = remember {
        TextStyle(
            color = Color.Unspecified,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 11.sp,
        )
    }

    val squareLabelStyleLight = remember { labelText.copy(color = boardColors.sqDark) }
    val squareLabelStyleDark = remember { labelText.copy(color = boardColors.sqLight) }
    val fileLabels = listOf("1", "2", "3", "4", "5", "6", "7", "8")
    val rankLabels = listOf("a","b","c","d","e","f","g","h")
    Canvas(
        modifier
            .fillMaxSize()
    ) {
        for (row in 0 until 8) {
            for (col in 0 until 8) {
                val color = if ((row + col) % 2 == 1) boardColors.sqLight else boardColors.sqDark
                val topLeftX = col * squareSize
                val topLeftY = row * squareSize

                drawRect(
                    color = color,
                    topLeft = Offset(topLeftX, topLeftY),
                    size = Size(squareSize, squareSize),
                )
            }
        }

        if (drawLabels) {
            drawIntoCanvas { canvas ->
                val paint = androidx.compose.ui.graphics.Paint().asFrameworkPaint()
                paint.textSize = 20f
                paint.color = Color.Black.toArgb()
                for (i in 0 until 8) {
                    val file = fileLabels[i]
                    val x = 20f
                    val y = (i * squareSize + squareSize / 2).toFloat() + paint.textSize / 2
                    val color = if ((i) % 2 == 1) squareLabelStyleLight else squareLabelStyleDark
                    drawText(
                        textMeasurer = textMeasurer,
                        text = file,
                        style = color,
                        topLeft = Offset(x, y)
                    )
                    if (i == 0 || i == 7) {
                        for (j in 0 until 8) {
                            val rank = rankLabels[j]
                            val x = j * squareSize + squareSize - 2 * paint.textSize
                            val color = if ((i == 0 && j % 2 == 0) || (i == 7 && j % 2 != 0)) {
                                squareLabelStyleDark
                            } else {
                                squareLabelStyleLight
                            }
                            drawText(
                                textMeasurer = textMeasurer,
                                text = rank,
                                style = color,
                                topLeft = Offset(x, y),
                            )
                        }
                    }
                }
            }
        }
    }
}



