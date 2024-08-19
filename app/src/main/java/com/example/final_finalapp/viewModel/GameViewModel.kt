package com.example.final_finalapp.viewModel

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duchess.java.com.example.duchess.game.logic.GameRoundRepository
import com.example.final_finalapp.game.GameRound
import com.example.final_finalapp.game.GameTerminationReason

import com.example.final_finalapp.game.Side
import com.example.final_finalapp.game.StartGame
import com.example.final_finalapp.game.composition.StableHolder
import com.example.final_finalapp.game.moves.Move

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class GameViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val startGame1: StartGame,
    private val gameSessionRepository: GameRoundRepository,

) : ViewModel(), ContainerHost<GameViewModel.State, GameViewModel.SideEffect> {
    private lateinit var side: Side


    override val container =container<State,SideEffect>(State.Loading){
        viewModelScope.launch {
            gameSessionRepository.activeGame()
                .filterNotNull()
                .filter { it.termination() == null }
                .collectLatest { game ->
                    intent {
                        reduce {
                            State.Game(StableHolder(game))
                        }
                    }
                    intent {
                        handleTermination(game.awaitTermination())
                    }
                }
        }
        side = Side.valueOf(state.get<String>("side")!!)
        startGame()
    }

    private fun handleTermination(reason: GameTerminationReason) {
        intent {
            postSideEffect(
                SideEffect.AnnounceTermination(
                    sideMated = reason.sideMated,
                    draw = false,
                    resignation = reason.resignation,
                ),
            )
        }
    }

    fun onPlayerMove(move: Move) {
        intent {
                gameSessionRepository.activeGame().firstOrNull()?.run {
                    Log.d("Move that came in", move.toString())
                    move(Move(move.fromRow, move.fromCol, move.toRow, move.toCol))
                }
        }
    }

    private suspend fun confirmResignation(): Boolean {
        return suspendCoroutine { continuation ->
            intent {
                postSideEffect(
                    SideEffect.ConfirmResignation(
                        onConfirm = { continuation.resume(true) },
                        onDismiss = { continuation.resume(false) },
                    ),
                )
            }
        }
    }


    fun onResign(andNavigateBack: Boolean = false) {
        intent {
            gameSessionRepository.activeGame().firstOrNull()?.run {
                if (confirmResignation()) {
                    resign()
                }
            }
            if (andNavigateBack) {
                postSideEffect(SideEffect.NavigateBack)
            }
        }
    }

    fun onRestart(){
        startGame()
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
