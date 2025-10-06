package com.kriscg.laboratorio8.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kriscg.laboratorio8.screens.*
import com.kriscg.laboratorio8.screens.locations.ScreenLocationDetails

@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            ScreenLogin(
                onStartClicked = {
                    navController.navigate("menu") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("menu") {
            ScreenMenu(rootNavController = navController)
        }

        composable(
            "character_details/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("characterId") ?: 0
            ScreenCharacterDetails(characterId = id, onBack = { navController.popBackStack() })
        }

        composable(
            "location_details/{locationId}",
            arguments = listOf(navArgument("locationId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("locationId") ?: 0
            ScreenLocationDetails(locationId = id, onBack = { navController.popBackStack() })
        }
    }
}