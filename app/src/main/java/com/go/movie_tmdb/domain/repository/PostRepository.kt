package com.go.movie_tmdb.domain.repository

import androidx.paging.PagingData
import com.go.movie_tmdb.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPagedPosts(): Flow<PagingData<Post>>
    fun searchPagedPosts(query: String): Flow<PagingData<Post>>
}