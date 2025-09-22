package com.kriscg.laboratorio8.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kriscg.laboratorio8.screens.ScreenLogin
import com.kriscg.laboratorio8.screens.ScreenMenu

@Composable
fun SetupNavGraph() {
    val rootNavController = rememberNavController()

    NavHost(navController = rootNavController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            ScreenLogin(
                onStartClicked = {
                    rootNavController.navigate(Screen.Menu.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }


        composable(Screen.Menu.route) {
            ScreenMenu(rootNavController = rootNavController)
        }
    }
}
