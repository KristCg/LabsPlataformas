package com.kriscg.laboratorio8.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Characters : Screen("characters")
    object CharacterDetails : Screen("character_details/{characterId}") {
        fun createRoute(characterId: Int) = "character_details/$characterId"
    }
}