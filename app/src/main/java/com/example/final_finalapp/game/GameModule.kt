package com.example.final_finalapp.game

import com.example.duchess.java.com.example.duchess.game.logic.GameRoundRepository
import com.example.duchess.java.com.example.duchess.game.logic.GameRoundRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GameModule {
    @Binds
    @Singleton
    abstract fun gameSessionRepo(impl: GameRoundRepositoryImpl): GameRoundRepository

    @Binds
    abstract fun startGame(impl: StartGameImpl): StartGame

}
