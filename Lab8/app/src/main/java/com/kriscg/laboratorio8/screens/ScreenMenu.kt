package com.kriscg.laboratorio8.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.kriscg.laboratorio8.ViewModel.CharactersViewModel
import com.kriscg.laboratorio8.ViewModel.CharactersViewModelFactory
import com.kriscg.laboratorio8.ViewModel.LocationsViewModel
import com.kriscg.laboratorio8.ViewModel.LocationsViewModelFactory
import com.kriscg.laboratorio8.data.room.AppDatabase
import com.kriscg.laboratorio8.navigation.Screen
import com.kriscg.laboratorio8.screens.ScreenCharacterDetails
import com.kriscg.laboratorio8.screens.locations.ScreenLocationDetails
import com.kriscg.laboratorio8.screens.profile.ScreenProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMenu(rootNavController: NavHostController, database: AppDatabase) {
    val navController = rememberNavController()

    val items: List<Pair<Screen, Pair<String, ImageVector>>> = listOf(
        Screen.CharactersRoot to ("Characters" to Icons.Default.List),
        Screen.LocationsRoot to ("Locations" to Icons.Default.LocationOn),
        Screen.Profile to ("Profile" to Icons.Default.Person)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route

                items.forEach { (screen, labelIcon) ->
                    val (label, icon) = labelIcon
                    NavigationBarItem(
                        selected = currentRoute?.startsWith(screen.route) == true || currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(imageVector = icon, contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = Screen.CharactersRoot.route) {

                // Listado de personajes
                composable(Screen.CharactersRoot.route) {
                    val viewModel: CharactersViewModel = viewModel(
                        factory = CharactersViewModelFactory(database)
                    )
                    ScreenCharacters(
                        database = database, // <- usa la instancia creada arriba
                        onCharacterClick = { id ->
                            navController.navigate(Screen.CharacterDetailsRoute(id))
                        }
                    )
                }


                // Detalle de personaje
                composable(
                    route = Screen.CharacterDetailsRoute,
                    arguments = listOf(navArgument("characterId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("characterId") ?: return@composable
                    ScreenCharacterDetails(characterId = id, onBack = { navController.popBackStack() })
                }

                // Listado de locaciones
                composable(Screen.LocationsRoot.route) {
                    val viewModel: LocationsViewModel = viewModel(
                        factory = LocationsViewModelFactory(database)
                    )
                    ScreenLocations(
                        database = database,
                        onLocationClick = { id ->
                            navController.navigate(Screen.LocationDetailsRoute(id))
                        }
                    )
                }

                // Detalle de locación
                composable(
                    route = Screen.LocationDetailsRoute,
                    arguments = listOf(navArgument("locationId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("locationId") ?: return@composable
                    ScreenLocationDetails(locationId = id, onBack = { navController.popBackStack() })
                }

                // Perfil
                composable(Screen.Profile.route) {
                    ScreenProfile(onLogout = {
                        rootNavController.navigate(Screen.Login.route) {
                            popUpTo(rootNavController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    })
                }
            }
        }
    }
}



