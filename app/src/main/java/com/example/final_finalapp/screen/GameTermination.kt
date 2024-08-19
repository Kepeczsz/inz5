package com.example.final_finalapp.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.final_finalapp.R
import com.example.final_finalapp.game.Side

@Composable
fun GameTermination(
    sideMated: Side? = null,
    draw: Boolean = false,
    resignation: Side? = null,
    onDismiss: () -> Unit,
    onRestart: (() -> Unit)?,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
    ) {
        Column(
            Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
        ) {
            Reason(
                sideMated = sideMated,
                draw = draw,
                resignation = resignation,
            )
            Row(modifier = Modifier.align(Alignment.End)) {
                onRestart?.let {
                    TextButton(
                        onClick = {
                            onRestart()
                            onDismiss()
                        },
                    ) {
                        Text(stringResource(id = R.string.restart))
                    }
                }
                TextButton(onClick = { onDismiss() }) {
                    Text(stringResource(R.string.dismiss))
                }
            }
        }
    }
}

@Composable
private fun Reason(
    sideMated: Side? = null,
    draw: Boolean = false,
    resignation: Side? = null,
) {
    val text = when {
        draw -> stringResource(R.string.game_ended_by_draw)
        resignation == Side.WHITE -> stringResource(R.string.white_resigned)
        resignation == Side.BLACK -> stringResource(R.string.black_resigned)
        else ->  "Przegrana"
    }
    Text(text = text)
}

@Composable
@Preview
private fun GameTerminationPreview() {
        Box(modifier = Modifier.padding(24.dp)) {
            GameTermination(
                onDismiss = {},
                onRestart = null,
            )
        }
}