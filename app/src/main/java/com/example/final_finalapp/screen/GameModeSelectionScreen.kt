package com.example.final_finalapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.final_finalapp.R
import com.example.final_finalapp.ui.theme.Final_finalappTheme

@Composable
fun GameModeSelectionScreen(
    onBotGameSelected: () -> Unit,
    onHumanGameSelected: () -> Unit,
    onBotVsBotGameSelected: () -> Unit,
    onDismiss: () -> Unit,
) {

    Final_finalappTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(id = R.string.chooseGameMode)) },
                )
            },
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(24.dp),
                ) {
                    BotGameButton(onClick = onBotGameSelected)
                    Spacer(modifier = Modifier.height(24.dp))
                    HumanGameButton(onClick = onHumanGameSelected)
                    Spacer(modifier = Modifier.height(24.dp))
                    BotVsBotGameButton(onClick = onBotVsBotGameSelected)
                }
            }
        }

    }
}

@Composable
fun BotGameButton(onClick: () -> Unit){
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = stringResource(id = R.string.playVsBot),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun HumanGameButton(onClick: () -> Unit){
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = stringResource(id = R.string.playVsHuman),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun BotVsBotGameButton(onClick: () -> Unit){
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = stringResource(id = R.string.BotVsBot),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
