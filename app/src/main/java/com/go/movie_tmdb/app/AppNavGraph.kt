package com.go.movie_tmdb.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.go.movie_tmdb.presentation.Viewmodels.PostViewModel
import com.go.movie_tmdb.presentation.ui.DetailsScreen
import com.go.movie_tmdb.presentation.ui.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        // Screen Home
        composable("home") {
            val homeViewModel: PostViewModel = koinViewModel()
            HomeScreen(
                viewModel = homeViewModel,
                onPostClick = { post ->
                    // Sava Post at SavedStateHandle to send to details Screen
                    navController.currentBackStackEntry?.savedStateHandle?.set("post", post)
                    navController.navigate("details")
                }
            )
        }

        //Screen details
        composable("details") {
            val post = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<com.go.movie_tmdb.domain.model.Post>("post")

            DetailsScreen(
                post = post
            )
        }
    }
}