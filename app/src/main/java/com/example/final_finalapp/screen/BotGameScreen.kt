package com.example.final_finalapp.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.final_finalapp.viewModel.GameViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun BotGameScreen(viewModel: GameViewModel = hiltViewModel(),
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
            var showTermination by remember { mutableStateOf<GameViewModel.SideEffect.AnnounceTermination?>(null) }
            var showResignation by remember { mutableStateOf<GameViewModel.SideEffect.ConfirmResignation?>(null) }


            BackHandler {
                viewModel.onResign(andNavigateBack = true)
            }

            viewModel.collectSideEffect { effect ->
                when (effect) {
                    is GameViewModel.SideEffect.AnnounceTermination -> showTermination = effect
                    is GameViewModel.SideEffect.ConfirmResignation -> showResignation = effect
                    is GameViewModel.SideEffect.NavigateBack -> navigateBack()
                }
            }
            showResignation?.let { effect ->
                ResignationDialog(
                    onConfirm = {
                        effect.onConfirm()
                        showResignation = null
                    },
                    onDismiss = {
                        effect.onDismiss
                        showResignation = null
                    },
                )
            }


            when (val s = state) {
                is GameViewModel.State.Game -> {
                    GameView(
                        gameHolder = s.gameHolder,
                        onMove = viewModel::onPlayerMove,
                        onResign = viewModel::onResign,
                    )
                }
                is GameViewModel.State.Loading -> Unit
            }
            showTermination?.let { (sideMated, draw, resignation) ->
                GameTermination(
                    sideMated = sideMated,
                    draw = draw,
                    resignation = resignation,
                    onRestart = { viewModel.onRestart() },
                    onDismiss = { showTermination = null },
                )
            }
        }
    }

}