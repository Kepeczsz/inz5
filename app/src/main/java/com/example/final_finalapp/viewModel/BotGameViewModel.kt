package com.example.final_finalapp.viewModel

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duchess.java.com.example.duchess.game.logic.GameRoundRepository
import com.example.final_finalapp.game.GameRound

import com.example.final_finalapp.game.Side
import com.example.final_finalapp.game.StartGame
import com.example.final_finalapp.game.composition.StableHolder
import com.example.final_finalapp.game.moves.Move
import com.example.final_finalapp.game.pieces.Piece

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class BotGameViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val startGame1: StartGame,
    private val gameSessionRepository: GameRoundRepository,

) : ViewModel(), ContainerHost<BotGameViewModel.State, BotGameViewModel.SideEffect> {
    private lateinit var side: Side


    override val container =container<State,SideEffect>(State.Loading){
        viewModelScope.launch {
            gameSessionRepository.activeGame()
                .filterNotNull()
                .collectLatest { game ->
                    intent {
                        reduce {
                            State.Game(StableHolder(game))
                        }
                    }
                    intent {
                    }
                }
        }
        side = Side.valueOf(state.get<String>("side")!!)
        startGame()
    }

    fun onPlayerMove(move: Move) {
        intent {
                gameSessionRepository.activeGame().firstOrNull()?.run {
                    Log.d("Move that came in", move.toString())
                    move(Move(move.fromRow, move.fromCol, move.toRow, move.toCol))
                }
        }
    }

    private fun startGame() {
        intent {
            startGame1(side,"test")
        }
    }

    sealed interface State {
        object Loading : State

        data class Game(
            val gameHolder: StableHolder<GameRound>,
        ) : State
    }

    sealed interface SideEffect {
        data class ConfirmResignation(
            val onConfirm: () -> Unit,
            val onDismiss: () -> Unit,
        ) : SideEffect

        @Stable
        data class AnnounceTermination(
            val sideMated: Side? = null,
            val draw: Boolean = false,
            val resignation: Side? = null,
        ) : SideEffect


        object NavigateBack : SideEffect
    }
}
