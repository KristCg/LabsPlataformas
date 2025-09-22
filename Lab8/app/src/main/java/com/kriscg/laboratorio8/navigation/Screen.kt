package com.kriscg.laboratorio8.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Menu : Screen("menu")

    object CharactersRoot : Screen("characters_root")
    object CharacterDetails : Screen("character_details/{characterId}") {
        fun createRoute(id: Int) = "character_details/$id"
    }

    object LocationsRoot : Screen("locations_root")
    object LocationDetails : Screen("location_details/{locationId}") {
        fun createRoute(id: Int) = "location_details/$id"
    }

    object Profile : Screen("profile")
}
