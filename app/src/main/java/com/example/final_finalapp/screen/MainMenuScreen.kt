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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.final_finalapp.viewModel.MainMenuViewModel

@Composable
fun MainMenuScreen(
    mainMenuViewModel: MainMenuViewModel = MainMenuViewModel(),
    onPlay: () -> Unit,
    onSolvePuzzle: () -> Unit,
    onRankings: () -> Unit,
    onMatches: () -> Unit,
    onSettings: () -> Unit,
) {
    Final_finalappTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(24.dp),
                ) {
                    PlayButton(onClick = onPlay)
                    Spacer(modifier = Modifier.height(24.dp))
                    SolvePuzzleButton(onClick = onSolvePuzzle)
                    Spacer(modifier = Modifier.height(24.dp))
                    RankingsButton(onClick = onRankings)
                    Spacer(modifier = Modifier.height(24.dp))
                    ShowMatchesButton(onClick = onMatches)
                    Spacer(modifier = Modifier.height(24.dp))
                    ShowSettingsButton(onClick = onSettings)
                }
            }
        }
    }
}

@Composable
fun PlayButton(onClick: () -> Unit) {
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
                text = stringResource(id = R.string.play),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun SolvePuzzleButton(onClick: () -> Unit) {
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
                text = stringResource(id = R.string.solve_puzzle),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun RankingsButton(onClick: () -> Unit) {
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
                text = stringResource(id = R.string.rankings),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
@Composable
fun ShowMatchesButton(onClick: () -> Unit) {
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
                text = stringResource(id = R.string.matches),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun ShowSettingsButton(onClick: () -> Unit) {
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
                text = stringResource(id = R.string.settings),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}