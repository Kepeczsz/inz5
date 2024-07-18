package com.example.final_finalapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.final_finalapp.viewModel.BotGameViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun BotGameScreen(viewModel: BotGameViewModel = hiltViewModel(),
                  navigateBack: () -> Unit)
{

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {},
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            val state by viewModel.collectAsState()


            when (val s = state) {
                is BotGameViewModel.State.Game -> {
                    GameView(
                        gameHolder = s.gameHolder,
                        onMove = viewModel::onPlayerMove,
                    )
                }
                is BotGameViewModel.State.Loading -> Unit
            }

        }
    }

}