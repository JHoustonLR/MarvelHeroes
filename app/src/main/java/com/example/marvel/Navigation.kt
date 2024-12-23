package com.example.marvel

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.marvel.api.CharacterViewModel
import com.example.marvel.ui.screens.HeroScreen
import com.example.marvel.ui.screens.MainScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    characterViewModel: CharacterViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(navController, characterViewModel)
        }
        composable(Screen.HeroDetail.route) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: "Неизвестно"
            val description = backStackEntry.arguments?.getString("description") ?: "Описание отсутствует"

            HeroScreen(
                url = url,
                name = name,
                description = description,
                onClose = { navController.popBackStack() }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Main : Screen("home")
    object HeroDetail : Screen("heroDetail/{url}/{name}/{description}") {
        fun createRoute(url: String, name: String, description: String): String =
            "heroDetail/$url/$name/$description"
    }
}
