package com.kriscg.laboratorio8.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kriscg.laboratorio8.data.room.AppDatabase
import com.kriscg.laboratorio8.screens.*
import com.kriscg.laboratorio8.ViewModel.CharactersViewModelFactory
import com.kriscg.laboratorio8.ViewModel.LocationsViewModelFactory
import com.kriscg.laboratorio8.screens.locations.ScreenLocationDetails

@Composable
fun SetupNavGraph(database: AppDatabase) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // Login
        composable("login") {
            ScreenLogin(
                onStartClicked = {
                    navController.navigate("menu") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // Menu
        composable("menu") {
            ScreenMenu(
                rootNavController = navController,
                database = database // pasar la database al menú
            )
        }

        // Characters
        composable("characters_root") {
            val factory = CharactersViewModelFactory(database)
            ScreenCharacters(
                database = database,
                onCharacterClick = { id ->
                    navController.navigate("character_details/$id")
                }
            )
        }

        // Locations
        composable("locations_root") {
            val factory = LocationsViewModelFactory(database)
            ScreenLocations(
                database = database,
                onLocationClick = { id ->
                    navController.navigate("location_details/$id")
                }
            )
        }

        // Detalles de Character
        composable(
            "character_details/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("characterId") ?: 0
            ScreenCharacterDetails(
                characterId = id,
                onBack = { navController.popBackStack() }
            )
        }

        // Detalles de Location
        composable(
            "location_details/{locationId}",
            arguments = listOf(navArgument("locationId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("locationId") ?: 0
            ScreenLocationDetails(
                locationId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
