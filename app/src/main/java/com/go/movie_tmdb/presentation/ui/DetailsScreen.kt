package com.go.movie_tmdb.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.go.movie_tmdb.domain.model.Post
import kotlinx.coroutines.delay


@Composable
fun DetailsScreen(
    post: Post?
) {
    post?.let {
        var showImage by remember { mutableStateOf(false) }
        var showTitle by remember { mutableStateOf(false) }
        var showDescription by remember { mutableStateOf(false) }

        // animation start
        LaunchedEffect(Unit) {
            showImage = true
            delay(200)
            showTitle = true
            delay(200)
            showDescription = true
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // animation image from the right
            AnimatedVisibility(
                visible = showImage,
                enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) + fadeIn()
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            // animation from the left
            AnimatedVisibility(
                visible = showTitle,
                enter = slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) + fadeIn()
            ) {
                Text(
                    text = it.title,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // animation image from the right
            AnimatedVisibility(
                visible = showDescription,
                enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) + fadeIn()
            ) {
                Text(
                    text = it.overview ?: "No description available.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}