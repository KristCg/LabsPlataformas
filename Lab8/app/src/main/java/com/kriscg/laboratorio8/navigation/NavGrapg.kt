package com.kriscg.laboratorio8.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kriscg.laboratorio8.screens.ScreenLogin
import com.kriscg.laboratorio8.screens.ScreenCharacters
import com.kriscg.laboratorio8.screens.ScreenCharacterDetails

@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            ScreenLogin(
                onStartClicked = {
                    navController.navigate(Screen.Characters.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Characters.route) {
            ScreenCharacters(
                onCharacterClick = { id ->
                    navController.navigate(Screen.CharacterDetails.createRoute(id))
                }
            )
        }

        composable(
            route = "character_details/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("characterId") ?: return@composable
            ScreenCharacterDetails(characterId = id, onBack = { navController.popBackStack() })
        }
    }
}

