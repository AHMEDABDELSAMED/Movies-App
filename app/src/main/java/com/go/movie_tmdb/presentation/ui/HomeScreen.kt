package com.go.movie_tmdb.presentation.ui

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.go.movie_tmdb.domain.model.Post
import com.go.movie_tmdb.presentation.Viewmodels.PostViewModel

@Composable
fun HomeScreen(
    viewModel: PostViewModel,
    onPostClick: (Post) -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val lastSearch by viewModel.lastSearch.collectAsState()
    val postsFlow = lastSearch ?: viewModel.lastFeed
    val posts = postsFlow.collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    // first loading movies
    LaunchedEffect(Unit) {
       // viewModel.getPosts()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF26293A))
            .padding(16.dp)
    ) {
        //  Search Box
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                if (it.isEmpty()) {
                    viewModel.clearSearch()
                } else {
                    viewModel.search(it)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Search", color = Color.LightGray) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },

        )
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(posts.itemCount) { index ->
                val post = posts[index]
                post?.let {
                    PostItem(
                        post = it,
                        onClick = { onPostClick(it) }
                    )
                }
            }

            // if paging is loading data for the first time (refresh) or loading additional pages (append)
            posts.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}
