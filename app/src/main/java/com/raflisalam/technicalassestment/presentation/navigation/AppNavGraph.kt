package com.raflisalam.technicalassestment.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raflisalam.technicalassestment.presentation.ui.genre.GenreScreen
import com.raflisalam.technicalassestment.presentation.ui.movies.MoviesScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "genres"
    ) {
        composable("genres") {
            GenreScreen(
                onGenreClick = { genreId, genreName ->
                    navController.navigate("movies/$genreId/${Uri.encode(genreName)}")
                }
            )
        }
        composable(
            route = "movies/{genreId}/{genreName}",
            arguments = listOf(
                navArgument("genreId") { type = NavType.IntType },
                navArgument("genreName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val genreId = backStackEntry.arguments?.getInt("genreId") ?: 0
            val genreName = backStackEntry.arguments?.getString("genreName").orEmpty()
            MoviesScreen(
                genreId = genreId,
                genreName = genreName,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
