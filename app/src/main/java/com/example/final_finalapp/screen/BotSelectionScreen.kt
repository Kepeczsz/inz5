package com.example.final_finalapp.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.final_finalapp.R
import com.example.final_finalapp.extensions.drawableResource
import com.example.final_finalapp.game.pieces.Piece
import com.example.final_finalapp.game.Side

@Composable
fun BotSelectionScreen(
    onBotSelected: (name: String, side: String) -> Unit,
    onDismiss: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.play)) },
                navigationIcon = {
                    IconButton(onClick = { onDismiss() }) {
                        Icon(painter = rememberVectorPainter(image = Icons.Rounded.ArrowBack), contentDescription = stringResource(R.string.back))
                    }
                },
            )
        },
    ) { padding ->
        var selectedSide by remember { mutableStateOf(Side.WHITE) }

        Column(Modifier.padding(padding), horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Side.values()
                    .filter { it != Side.NEUTRAL }
                    .forEach { side ->
                    val piece = if (side == Side.WHITE) Piece.WHITE_KING else Piece.BLACK_KING
                    Image(
                        modifier = Modifier
                            .padding(vertical = 24.dp, horizontal = 16.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { selectedSide = side }
                            .let {
                                if (selectedSide == side) {
                                    it.border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        shape = CircleShape,
                                    )
                                } else {
                                    it
                                }
                            }
                            .size(64.dp)
                            .padding(8.dp),
                        painter = painterResource(piece.drawableResource()),
                        contentDescription = side.name,
                    )
                }
            }
            playButton {
                onBotSelected("test", selectedSide.name)
            }
        }
    }
}
@Composable
fun playButton(onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier.padding()
    ) {
        Text(text = "PLAY", style = MaterialTheme.typography.bodyMedium)
    }
}

