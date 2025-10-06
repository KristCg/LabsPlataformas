package com.kriscg.laboratorio8.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    val route: String

    @Serializable
    data object Login : Screen { override val route = "login" }
    @Serializable
    data object Menu : Screen { override val route = "menu" }

    @Serializable
    data object CharactersRoot : Screen { override val route = "characters_root" }
    @Serializable
    data object LocationsRoot : Screen { override val route = "locations_root" }
    @Serializable
    data object Profile : Screen { override val route = "profile" }

    companion object {
        const val CharacterDetailsRoute = "character_details/{characterId}"
        fun CharacterDetailsRoute(id: Int) = "character_details/$id"

        const val LocationDetailsRoute = "location_details/{locationId}"
        fun LocationDetailsRoute(id: Int) = "location_details/$id"
    }
}


