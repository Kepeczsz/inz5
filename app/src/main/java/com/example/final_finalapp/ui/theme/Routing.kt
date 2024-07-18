package com.example.final_finalapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.final_finalapp.screen.BotGameScreen
import com.example.final_finalapp.screen.BotSelectionScreen
import com.example.final_finalapp.screen.GameModeSelectionScreen
import com.example.final_finalapp.screen.MainMenuScreen

@Composable
fun Routing(){
    val navController = rememberNavController()
    NavHost(navController, "/mainMenu"){
        composable("/mainMenu"){
            MainMenuScreen(
                onPlay = { navController.navigate("/selectGameMode")},
                onMatches = {} ,
                onRankings = {},
                onSettings = {},
                onSolvePuzzle = {},
            )
        }
        composable("/selectGameMode"){
            GameModeSelectionScreen(
                onBotGameSelected = {navController.navigate("/selectBot")},
                onHumanGameSelected = {},
                onBotVsBotGameSelected = {},
                onDismiss = {navController.popBackStack()},
            )
        }
        composable("/selectBot"){
            BotSelectionScreen(
                onBotSelected = {bot, side -> navController.navigate("/game/$side")},
                onDismiss = {navController.popBackStack()}
            )
        }
        composable(
            "/game/{side}",
            listOf(
                navArgument("side") { type = NavType.StringType },
            ),
        ) {
            BotGameScreen {
                navController.popBackStack()
            }
        }
    }
}