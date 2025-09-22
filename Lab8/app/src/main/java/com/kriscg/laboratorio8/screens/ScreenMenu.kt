package com.kriscg.laboratorio8.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.kriscg.laboratorio8.navigation.Screen
import com.kriscg.laboratorio8.screens.ScreenLocations
import com.kriscg.laboratorio8.screens.locations.ScreenLocationDetails
import com.kriscg.laboratorio8.screens.profile.ScreenProfile
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMenu(rootNavController: NavHostController) {
    val navController = rememberNavController()

    val items = listOf(
        Screen.CharactersRoot to Pair("Characters", Icons.Default.List),
        Screen.LocationsRoot to Pair("Locations", Icons.Default.LocationOn),
        Screen.Profile to Pair("Profile", Icons.Default.Person)
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
                           if (screen == Screen.Profile) {
                                navController.navigate(Screen.Profile.route) {
                                    launchSingleTop = true
                                }
                            } else {
                                navController.navigate(screen.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
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

                composable(Screen.CharactersRoot.route) {
                    ScreenCharacters(
                        onCharacterClick = { id ->
                            navController.navigate(Screen.CharacterDetails.createRoute(id))
                        }
                    )
                }

                composable(
                    route = Screen.CharacterDetails.route,
                    arguments = listOf(navArgument("characterId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("characterId") ?: return@composable
                    ScreenCharacterDetails(
                        characterId = id,
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(Screen.LocationsRoot.route) {
                    ScreenLocations(onLocationClick = { id ->
                        navController.navigate(Screen.LocationDetails.createRoute(id))
                    })
                }

                composable(
                    route = Screen.LocationDetails.route,
                    arguments = listOf(navArgument("locationId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("locationId") ?: return@composable
                    ScreenLocationDetails(
                        locationId = id,
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(Screen.Profile.route) {
                    ScreenProfile(
                        onLogout = {
                            rootNavController.navigate(Screen.Login.route) {
                                popUpTo(rootNavController.graph.startDestinationId) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }
}
